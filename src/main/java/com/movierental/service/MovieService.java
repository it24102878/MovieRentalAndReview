package com.movierental.service;

import com.movierental.model.DigitalMovie;
import com.movierental.model.Movie;
import com.movierental.model.PhysicalMovie;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private static final String FILE_PATH = "src/main/resources/movies.txt";

    public Movie createMovie(Movie movie) throws IOException {
        movie.setId(UUID.randomUUID().toString());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(movie.toString());
            writer.newLine();
        }
        return movie;
    }

    public List<Movie> getAllMovies() throws IOException {
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                movies.add(parseMovie(line));
            }
        }
        return movies;
    }

    public Movie getMovieById(String id) throws IOException {
        return getAllMovies().stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Movie> searchMovies(String title, String genre) throws IOException {
        return getAllMovies().stream()
                .filter(movie -> (title == null || movie.getTitle().toLowerCase().contains(title.toLowerCase())) &&
                        (genre == null || movie.getGenre().equalsIgnoreCase(genre)))
                .collect(Collectors.toList());
    }

    public Movie updateMovie(String id, Movie updatedMovie) throws IOException {
        List<Movie> movies = getAllMovies();
        boolean found = false;
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId().equals(id)) {
                updatedMovie.setId(id);
                movies.set(i, updatedMovie);
                found = true;
                break;
            }
        }
        if (found) {
            writeMoviesToFile(movies);
            return updatedMovie;
        }
        return null;
    }

    public boolean deleteMovie(String id) throws IOException {
        List<Movie> movies = getAllMovies();
        int initialSize = movies.size();
        movies.removeIf(movie -> movie.getId().equals(id));
        if (movies.size() < initialSize) {
            writeMoviesToFile(movies);
            return true;
        }
        return false;
    }

    private Movie parseMovie(String line) {
        String[] parts = line.split("\\|");
        String type = parts[0];
        String id = parts[1];
        String title = parts[2];
        String genre = parts[3];
        LocalDate releaseDate = LocalDate.parse(parts[4]);
        String description = parts[5];
        boolean available = Boolean.parseBoolean(parts[6]);
        if (type.equals("Physical")) {
            String format = parts[7];
            return new PhysicalMovie(id, title, genre, releaseDate, description, available, format);
        } else {
            String platform = parts[7];
            return new DigitalMovie(id, title, genre, releaseDate, description, available, platform);
        }
    }

    private void writeMoviesToFile(List<Movie> movies) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Movie movie : movies) {
                writer.write(movie.toString());
                writer.newLine();
            }
        }
    }
}