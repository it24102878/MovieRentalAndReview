package com.moviereview.model;

public class VerifiedReview extends Review {
    public VerifiedReview(String movieTitle, int rating, String reviewText, String reviewId) {
        super(movieTitle, rating, reviewText, reviewId);
    }

    @Override
    public String displayReview() {
        return "[Verified] Review for " + getMovieTitle() + ": " + getRating() + " stars\n" + getReviewText();
    }
}