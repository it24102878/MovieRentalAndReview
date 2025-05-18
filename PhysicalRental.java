package com.movierental.model;

import java.time.LocalDate;

public class PhysicalRental extends Rental {
    public PhysicalRental(String rentalId, String customerId, String movieId, LocalDate rentalDate, LocalDate dueDate, String status) {
        super(rentalId, customerId, movieId, rentalDate, dueDate, status);
    }

    @Override
    public double calculateCost() {
        // Physical rental: $5 base + $1 per day
        long days = java.time.temporal.ChronoUnit.DAYS.between(getRentalDate(), getDueDate());
        return 5.0 + days * 1.0;
    }
}