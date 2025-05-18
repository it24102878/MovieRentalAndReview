package com.movierental.service;

import com.movierental.model.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RentalService {
    private final String RENTALS_FILE = "rentals.txt";
    private final String PAYMENTS_FILE = "payments.txt";
    private final int MAX_RENTALS_PER_MOVIE = 5; // Example limit

    public Rental createRental(String customerId, String movieId, String rentalType, LocalDate dueDate) throws IOException {
        if (!isMovieAvailable(movieId)) {
            throw new IllegalStateException("Movie is not available for rental.");
        }
        String rentalId = UUID.randomUUID().toString();
        Rental rental;
        if ("physical".equalsIgnoreCase(rentalType)) {
            rental = new PhysicalRental(rentalId, customerId, movieId, LocalDate.now(), dueDate, "active");
        } else {
            rental = new DigitalRental(rentalId, customerId, movieId, LocalDate.now(), dueDate, "active");
        }
        saveRentalToFile(rental);
        return rental;
    }

    public Payment processPayment(String rentalId, String cardNumber, String cardHolder, String expiryDate, double amount) throws IOException {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, rentalId, cardNumber, cardHolder, LocalDate.parse(expiryDate), amount);
        savePaymentToFile(payment);
        return payment;
    }

    public List<Rental> getRentalsByCustomer(String customerId) throws IOException {
        List<Rental> rentals = readRentalsFromFile();
        List<Rental> customerRentals = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getCustomerId().equals(customerId)) {
                customerRentals.add(rental);
            }
        }
        return customerRentals;
    }

    public List<Rental> getRentalsByMovie(String movieId) throws IOException {
        List<Rental> rentals = readRentalsFromFile();
        List<Rental> movieRentals = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getMovieId().equals(movieId)) {
                movieRentals.add(rental);
            }
        }
        return movieRentals;
    }

    public Rental updateRental(String rentalId, String status, LocalDate dueDate) throws IOException {
        List<Rental> rentals = readRentalsFromFile();
        for (Rental rental : rentals) {
            if (rental.getRentalId().equals(rentalId)) {
                if (status != null) rental.setStatus(status);
                if (dueDate != null) rental.setDueDate(dueDate);
                updateRentalsFile(rentals);
                return rental;
            }
        }
        throw new IllegalArgumentException("Rental not found.");
    }

    public void deleteRental(String rentalId) throws IOException {
        List<Rental> rentals = readRentalsFromFile();
        rentals.removeIf(rental -> rental.getRentalId().equals(rentalId));
        updateRentalsFile(rentals);
    }

    public boolean isMovieAvailable(String movieId) throws IOException {
        List<Rental> rentals = getRentalsByMovie(movieId);
        long activeRentals = rentals.stream().filter(r -> "active".equals(r.getStatus())).count();
        return activeRentals < MAX_RENTALS_PER_MOVIE;
    }

    private void saveRentalToFile(Rental rental) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RENTALS_FILE, true))) {
            writer.write(rental.toString() + "," + rental.getClass().getSimpleName());
            writer.newLine();
        }
    }

    private void savePaymentToFile(Payment payment) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAYMENTS_FILE, true))) {
            writer.write(payment.toString());
            writer.newLine();
        }
    }

    private List<Rental> readRentalsFromFile() throws IOException {
        List<Rental> rentals = new ArrayList<>();
        File file = new File(RENTALS_FILE);
        if (!file.exists()) return rentals;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 7) continue;
                String rentalId = parts[0];
                String customerId = parts[1];
                String movieId = parts[2];
                LocalDate rentalDate = LocalDate.parse(parts[3]);
                LocalDate dueDate = LocalDate.parse(parts[4]);
                String status = parts[5];
                String rentalType = parts[6];
                Rental rental;
                if ("PhysicalRental".equals(rentalType)) {
                    rental = new PhysicalRental(rentalId, customerId, movieId, rentalDate, dueDate, status);
                } else {
                    rental = new DigitalRental(rentalId, customerId, movieId, rentalDate, dueDate, status);
                }
                rentals.add(rental);
            }
        }
        return rentals;
    }

    private void updateRentalsFile(List<Rental> rentals) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RENTALS_FILE))) {
            for (Rental rental : rentals) {
                writer.write(rental.toString() + "," + rental.getClass().getSimpleName());
                writer.newLine();
            }
        }
    }
}