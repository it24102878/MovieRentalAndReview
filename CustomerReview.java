package com.moviereview.model;

public class CustomerReview extends Review {
    public CustomerReview(String movieTitle, int rating, String reviewText, String reviewId) {
        super(movieTitle, rating, reviewText, reviewId);
    }

    @Override
    public String displayReview() {
        return "Customer Review for " + getMovieTitle() + ": " + getRating() + " stars\n" + getReviewText();
    }
}