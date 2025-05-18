package com.movierental.model;

import java.time.LocalDate;

public class DigitalRental extends Rental {
    public DigitalRental(String rentalId, String customerId, String movieId, LocalDate rentalDate, LocalDate dueDate, String status) {
        super(rentalId, customerId, movieId, rentalDate, dueDate, status);
    }

    @Override
    public double calculateCost() {
        // Digital rental: $3 flat rate
        return 3.0;
    }
}