package com.moviereview.service;

import com.moviereview.model.CustomerReview;
import com.moviereview.model.Review;
import com.moviereview.model.VerifiedReview;
import com.moviereview.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public void submitReview(String movieTitle, int rating, String reviewText, boolean isVerified) throws IOException {
        String reviewId = UUID.randomUUID().toString();
        Review review = isVerified ?
                new VerifiedReview(movieTitle, rating, reviewText, reviewId) :
                new CustomerReview(movieTitle, rating, reviewText, reviewId);
        reviewRepository.saveReview(review);
    }

    public List<Review> getAllReviews() throws IOException {
        return reviewRepository.getAllReviews();
    }

    public Review getReviewById(String reviewId) throws IOException {
        return reviewRepository.findReviewById(reviewId);
    }

    public void updateReview(String reviewId, String movieTitle, int rating, String reviewText, boolean isVerified) throws IOException {
        Review existingReview = reviewRepository.findReviewById(reviewId);
        if (existingReview != null) {
            Review updatedReview = isVerified ?
                    new VerifiedReview(movieTitle, rating, reviewText, reviewId) :
                    new CustomerReview(movieTitle, rating, reviewText, reviewId);
            reviewRepository.updateReview(updatedReview);
        }
    }

    public void deleteReview(String reviewId) throws IOException {
        reviewRepository.deleteReview(reviewId);
    }

    public String displayReview(Review review) {
        return review.displayReview(); // Polymorphic behavior
    }
}