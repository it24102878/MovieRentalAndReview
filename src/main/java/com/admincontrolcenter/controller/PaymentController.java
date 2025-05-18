package com.admincontrolcenter.controller;

import com.admincontrolcenter.model.Payment;
import com.admincontrolcenter.service.MovieService;
import com.admincontrolcenter.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final MovieService movieService;

    @Autowired
    public PaymentController(PaymentService paymentService, MovieService movieService) {
        this.paymentService = paymentService;
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.ok(createdPayment);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return payment != null ? ResponseEntity.ok(payment) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<List<Payment>> getPaymentsByUserName(@PathVariable String userName) {
        return ResponseEntity.ok(paymentService.getPaymentsByUserName(userName));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Payment>> getPaymentsByMovieId(@PathVariable Long movieId) {
        return ResponseEntity.ok(paymentService.getPaymentsByMovieId(movieId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable Long id, @RequestParam String status) {
        Payment updatedPayment = paymentService.updatePaymentStatus(id, status);
        return updatedPayment != null ? ResponseEntity.ok(updatedPayment) : ResponseEntity.notFound().build();
    }

    @PostMapping("/process/{movieId}")
    public ResponseEntity<Payment> processPayment(
            @PathVariable Long movieId,
            @RequestParam String userName,
            @RequestParam String paymentMethod,
            @RequestParam double amount) {
        
        try {
            // Create a new payment
            Payment payment = new Payment();
            payment.setUserName(userName);
            payment.setMovieId(movieId);
            payment.setAmount(amount);
            payment.setPaymentMethod(paymentMethod);
            payment.setPaymentStatus("COMPLETED");
            
            Payment createdPayment = paymentService.createPayment(payment);
            
            // Process the rental after payment is successful
            movieService.rentMovie(movieId, userName);
            
            return ResponseEntity.ok(createdPayment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}