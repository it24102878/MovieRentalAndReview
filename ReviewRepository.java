package com.moviereview.repository;

import com.moviereview.model.CustomerReview;
import com.moviereview.model.Review;
import com.moviereview.model.VerifiedReview;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ReviewRepository {
    private final String FILE_PATH = "src/main/resources/reviews.txt";

    public void saveReview(Review review) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String reviewType = review instanceof VerifiedReview ? "Verified" : "Customer";
            writer.write(reviewType + "|" + review.getReviewId() + "|" + review.getMovieTitle() + "|" +
                    review.getRating() + "|" + review.getReviewText  + "\n");
        }
    }

    public List<Review> getAllReviews() throws IOException {
        List<Review> reviews = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String type = parts[0];
                    String id = parts[1];
                    String title = parts[2];
                    int rating = Integer.parseInt(parts[3]);
                    String text = parts[4];
                    Review review = type.equals("Verified") ?
                            new VerifiedReview(title, rating, text, id) :
                            new CustomerReview(title, rating, text, id);
                    reviews.add(review);
                }
            }
        }
        return reviews;
    }

    public Review findReviewById(String reviewId) throws IOException {
        List<Review> reviews = getAllReviews();
        return reviews.stream()
                .filter(r -> r.getReviewId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }

    public void updateReview(Review updatedReview) throws IOException {
        List<Review> reviews = getAllReviews();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Review review : reviews) {
                if (review.getReviewId().equals(updatedReview.getReviewId())) {
                    String reviewType = review instanceof VerifiedReview ? "Verified" : "Customer";
                    writer.write(reviewType + "|" + updatedReview.getReviewId() + "|" + updatedReview.getMovieTitle() + "|" +
                            updatedReview.getRating() + "|" + updatedReview.getReviewText() + "\n");
                } else {
                    String reviewType = review instanceof VerifiedReview ? "Verified" : "Customer";
                    writer.write(reviewType + "|" + review.getReviewId() + "|" + review.getMovieTitle() + "|" +
                            review.getRating() + "|" + review.getReviewText() + "\n");
                }
            }
        }
    }

    public void deleteReview(String reviewId) throws IOException {
        List<Review> reviews = getAllReviews();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Review review : reviews) {
                if (!review.getReviewId().equals(reviewId)) {
                    String reviewType = review instanceof VerifiedReview ? "Verified" : "Customer";
                    writer.write(reviewType + "|" + review.getReviewId() + "|" + review.getMovieTitle() + "|" +
                            review.getRating() + "|" + review.getReviewText() + "\n");
                }
            }
        }
    }
}