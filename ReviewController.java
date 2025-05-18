package com.moviereview.controller;

import com.moviereview.model.Review;
import com.moviereview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitReview(@RequestParam String movieTitle,
                                               @RequestParam int rating,
                                               @RequestParam String reviewText,
                                               @RequestParam(defaultValue = "false") boolean isVerified) throws IOException {
        reviewService.submitReview(movieTitle, rating, reviewText, isVerified);
        return ResponseEntity.ok("Review submitted successfully");
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() throws IOException {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable String reviewId) throws IOException {
        Review review = reviewService.getReviewById(reviewId);
        if (review != null) {
            return ResponseEntity.ok(review);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable String reviewId,
                                               @RequestParam String movieTitle,
                                               @RequestParam int rating,
                                               @RequestParam String reviewText,
                                               @RequestParam(defaultValue = "false") boolean isVerified) throws IOException {
        reviewService.updateReview(reviewId, movieTitle, rating, reviewText, isVerified);
        return ResponseEntity.ok("Review updated successfully");
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable String reviewId) throws IOException {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }
}