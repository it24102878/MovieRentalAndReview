package com.movierental.controller;

import com.movierental.model.Payment;
import com.movierental.model.Rental;
import com.movierental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin(origins = "http://localhost:8080")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping("/rent")
    public ResponseEntity<Rental> rentMovie(
            @RequestParam String customerId,
            @RequestParam String movieId,
            @RequestParam String rentalType,
            @RequestParam String dueDate) throws IOException {
        Rental rental = rentalService.createRental(customerId, movieId, rentalType, LocalDate.parse(dueDate));
        return ResponseEntity.ok(rental);
    }

    @PostMapping("/payment")
    public ResponseEntity<Payment> processPayment(
            @RequestParam String rentalId,
            @RequestParam String cardNumber,
            @RequestParam String cardHolder,
            @RequestParam String expiryDate,
            @RequestParam double amount) throws IOException {
        Payment payment = rentalService.processPayment(rentalId, cardNumber, cardHolder, expiryDate, amount);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Rental>> getRentalsByCustomer(@PathVariable String customerId) throws IOException {
        return ResponseEntity.ok(rentalService.getRentalsByCustomer(customerId));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Rental>> getRentalsByMovie(@PathVariable String movieId) throws IOException {
        return ResponseEntity.ok(rentalService.getRentalsByMovie(movieId));
    }

    @PutMapping("/{rentalId}")
    public ResponseEntity<Rental> updateRental(
            @PathVariable String rentalId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String dueDate) throws IOException {
        Rental rental = rentalService.updateRental(rentalId, status, dueDate != null ? LocalDate.parse(dueDate) : null);
        return ResponseEntity.ok(rental);
    }

    @DeleteMapping("/{rentalId}")
    public ResponseEntity<Void> deleteRental(@PathVariable String rentalId) throws IOException {
        rentalService.deleteRental(rentalId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/availability/{movieId}")
    public ResponseEntity<Boolean> isMovieAvailable(@PathVariable String movieId) throws IOException {
        return ResponseEntity.ok(rentalService.isMovieAvailable(movieId));
    }
}