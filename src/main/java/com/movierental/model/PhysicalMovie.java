package com.movierental.model;

import java.time.LocalDate;

public class PhysicalMovie extends Movie {
    private String format; // e.g., DVD, Blu-ray

    public PhysicalMovie(String id, String title, String genre, LocalDate releaseDate, String description, boolean available, String format) {
        super(id, title, genre, releaseDate, description, available);
        this.format = format;
    }

    @Override
    public String getDisplayDetails() {
        return "Physical Movie: " + getTitle() + " (" + getReleaseDate().getYear() + "), Genre: " + getGenre() + ", Format: " + format + ", Available: " + isAvailable();
    }

    @Override
    public String toString() {
        return "Physical|" + super.toString() + "|" + format;
    }
}