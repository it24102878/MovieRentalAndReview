package com.movierental.model;

import java.time.LocalDate;

public abstract class Rental {
    private String rentalId;
    private String customerId;
    private String movieId;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private String status; // e.g., "active", "returned", "extended"

    public Rental(String rentalId, String customerId, String movieId, LocalDate rentalDate, LocalDate dueDate, String status) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.movieId = movieId;
        this.rentalDate = rentalDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Getters and Setters
    public String getRentalId() { return rentalId; }
    public void setRentalId(String rentalId) { this.rentalId = rentalId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }
    public LocalDate getRentalDate() { return rentalDate; }
    public void setRentalDate(LocalDate rentalDate) { this.rentalDate = rentalDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Abstract method for cost calculation (polymorphism)
    public abstract double calculateCost();

    @Override
    public String toString() {
        return rentalId + "," + customerId + "," + movieId + "," + rentalDate + "," + dueDate + "," + status;
    }
}