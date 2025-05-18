package com.admincontrolcenter.service;

import com.admincontrolcenter.model.Movie;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Service
public class MovieService {
    private static final Logger LOGGER = Logger.getLogger(MovieService.class.getName());

    // Use data directory in the current working directory
    private final String dataDirectory;
    private final String filePath;
    private final String rentalFilePath;
    private Long nextId = 1L;

    public MovieService() {
        try {
            // Create paths relative to the current working directory
            this.dataDirectory = "data";
            this.filePath = dataDirectory + "/movies.txt";
            this.rentalFilePath = dataDirectory + "/rentals.txt";

            // Ensure data directory exists
            createDirectoryIfNotExists(dataDirectory);

            initializeFile();
            initializeRentalFile();
            loadNextId();
        } catch (Exception e) {
            LOGGER.severe("Failed to initialize MovieService: " + e.getMessage());
            throw new RuntimeException("Initialization failed", e);
        }
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                LOGGER.info("Created directory: " + directoryPath);
            } else {
                LOGGER.warning("Failed to create directory: " + directoryPath);
            }
        }
    }

    private void initializeFile() {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (created) {
                    LOGGER.info("Created new movies.txt file at: " + file.getAbsolutePath());
                } else {
                    LOGGER.warning("Failed to create movies.txt file at: " + file.getAbsolutePath());
                }
            } else {
                LOGGER.info("Using existing movies.txt file at: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.severe("Error initializing movies file at " + file.getAbsolutePath() + ": " + e.getMessage());
            throw new RuntimeException("Error initializing movies file", e);
        }
    }

    private void initializeRentalFile() {
        File file = new File(rentalFilePath);
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (created) {
                    LOGGER.info("Created new rentals.txt file at: " + file.getAbsolutePath());
                } else {
                    LOGGER.warning("Failed to create rentals.txt file at: " + file.getAbsolutePath());
                }
            } else {
                LOGGER.info("Using existing rentals.txt file at: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.severe("Error initializing rentals file at " + file.getAbsolutePath() + ": " + e.getMessage());
            throw new RuntimeException("Error initializing rentals file", e);
        }
    }

    private void loadNextId() {
        List<Movie> movies = readAllMovies();
        if (!movies.isEmpty()) {
            nextId = movies.stream().map(Movie::getId).max(Long::compare).orElse(0L) + 1;
            LOGGER.info("Next ID set to: " + nextId);
        }
    }

    public Movie createMovie(Movie movie) {
        movie.setId(nextId++);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(movieToCsv(movie));
            writer.newLine();
            LOGGER.info("Created movie: " + movie.getTitle() + " in file: " + filePath);
        } catch (IOException e) {
            LOGGER.severe("Error writing to movies file " + filePath + ": " + e.getMessage());
            throw new RuntimeException("Error writing to movies file", e);
        }
        return movie;
    }

    public List<Movie> getAllMovies() {
        return readAllMovies();
    }

    public Movie getMovieById(Long id) {
        return readAllMovies().stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Movie> searchMoviesByTitle(String title) {
        return readAllMovies().stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Movie> searchMoviesByGenre(String genre) {
        return readAllMovies().stream()
                .filter(movie -> movie.getGenre().toLowerCase().contains(genre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Movie updateMovie(Long id, Movie updatedMovie) {
        List<Movie> movies = readAllMovies();
        boolean found = false;
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId().equals(id)) {
                updatedMovie.setId(id);
                movies.set(i, updatedMovie);
                found = true;
                break;
            }
        }
        if (!found) {
            return null;
        }
        writeAllMovies(movies);
        LOGGER.info("Updated movie ID: " + id);
        return updatedMovie;
    }

    public boolean deleteMovie(Long id) {
        List<Movie> movies = readAllMovies();
        boolean removed = movies.removeIf(movie -> movie.getId().equals(id));
        if (removed) {
            writeAllMovies(movies);
            LOGGER.info("Deleted movie ID: " + id);
        }
        return removed;
    }

    public void rentMovie(Long movieId, String userName) {
        Movie movie = getMovieById(movieId);
        if (movie == null) {
            LOGGER.warning("Rent attempt failed for movie ID: " + movieId);
            throw new RuntimeException("Movie not found");
        }
        // Always keep the movie available regardless of rental status
        movie.setAvailable(true);
        updateMovie(movieId, movie);

        File file = new File(rentalFilePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(String.format("%d,%s", movieId, escapeCsv(userName)));
            writer.newLine();
            LOGGER.info("Rented movie ID: " + movieId + " to " + userName + " in file: " + file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.severe("Error writing to rentals file " + file.getAbsolutePath() + ": " + e.getMessage());
            throw new RuntimeException("Error writing to rentals file", e);
        }
    }

    public List<String> getRentals() {
        List<String> rentals = new ArrayList<>();
        File file = new File(rentalFilePath);

        // Return empty list if file doesn't exist yet
        if (!file.exists()) {
            LOGGER.info("Rentals file does not exist yet at: " + file.getAbsolutePath());
            return rentals;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    rentals.add(line);
                }
            }
            LOGGER.info("Retrieved " + rentals.size() + " rentals from: " + file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.severe("Error reading rentals file " + file.getAbsolutePath() + ": " + e.getMessage());
            throw new RuntimeException("Error reading rentals file", e);
        }
        return rentals;
    }

    private List<Movie> readAllMovies() {
        List<Movie> movies = new ArrayList<>();
        File file = new File(filePath);

        // Return empty list if file doesn't exist yet
        if (!file.exists()) {
            LOGGER.info("Movies file does not exist yet at: " + file.getAbsolutePath());
            return movies;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (!line.trim().isEmpty()) {
                    try {
                        movies.add(csvToMovie(line));
                    } catch (IllegalArgumentException e) {
                        LOGGER.warning("Skipping invalid line " + lineNumber + " in " + filePath + ": " + line + " (" + e.getMessage() + ")");
                    }
                }
            }
            LOGGER.info("Read " + movies.size() + " movies from: " + file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.severe("Error reading movies file " + file.getAbsolutePath() + ": " + e.getMessage());
            throw new RuntimeException("Error reading movies file", e);
        }
        return movies;
    }

    private void writeAllMovies(List<Movie> movies) {
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Movie movie : movies) {
                writer.write(movieToCsv(movie));
                writer.newLine();
            }
            LOGGER.info("Wrote " + movies.size() + " movies to file: " + file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.severe("Error writing to movies file " + file.getAbsolutePath() + ": " + e.getMessage());
            throw new RuntimeException("Error writing to movies file", e);
        }
    }

    private String movieToCsv(Movie movie) {
        return String.format("%d,%s,%s,%s,%s,%b,%.1f,%s",
                movie.getId(),
                escapeCsv(movie.getTitle()),
                escapeCsv(movie.getGenre()),
                movie.getReleaseDate().toString(),
                escapeCsv(movie.getDescription()),
                movie.isAvailable(),
                movie.getRating(),
                escapeCsv(movie.getImageUrl() != null ? movie.getImageUrl() : ""));
    }

    private Movie csvToMovie(String csv) {
        String[] parts = csv.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (parts.length < 7 || parts.length > 8) {
            throw new IllegalArgumentException("Invalid CSV format, expected 7 or 8 fields, got " + parts.length + ": " + csv);
        }
        try {
            String imageUrl = parts.length == 8 ? unescapeCsv(parts[7]) : ""; // Default to empty string if imageUrl is missing
            return new Movie(
                    Long.parseLong(parts[0].trim()),
                    unescapeCsv(parts[1]),
                    unescapeCsv(parts[2]),
                    LocalDate.parse(parts[3].trim()),
                    unescapeCsv(parts[4]),
                    Boolean.parseBoolean(parts[5].trim()),
                    Double.parseDouble(parts[6].trim()),
                    imageUrl
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid data in CSV: " + csv, e);
        }
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private String unescapeCsv(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1).replace("\"\"", "\"");
        }
        return value;
    }
}
