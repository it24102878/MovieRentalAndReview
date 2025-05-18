package com.movierental.model;

import java.time.LocalDate;

public abstract class Movie {
    private String id;
    private String title;
    private String genre;
    private LocalDate releaseDate;
    private String description;
    private boolean available;

    public Movie(String id, String title, String genre, LocalDate releaseDate, String description, boolean available) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.description = description;
        this.available = available;
    }

    // Getters and Setters (Encapsulation)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    // Polymorphic method
    public abstract String getDisplayDetails();

    @Override
    public String toString() {
        return id + "|" + title + "|" + genre + "|" + releaseDate + "|" + description + "|" + available;
    }
}