package com.movierental.model;

import java.time.LocalDate;

public class DigitalMovie extends Movie {
    private String streamingPlatform;

    public DigitalMovie(String id, String title, String genre, LocalDate releaseDate, String description, boolean available, String streamingPlatform) {
        super(id, title, genre, releaseDate, description, available);
        this.streamingPlatform = streamingPlatform;
    }

    @Override
    public String getDisplayDetails() {
        return "Digital Movie: " + getTitle() + " (" + getReleaseDate().getYear() + "), Genre: " + getGenre() + ", Platform: " + streamingPlatform + ", Available: " + isAvailable();
    }

    @Override
    public String toString() {
        return "Digital|" + super.toString() + "|" + streamingPlatform;
    }
}