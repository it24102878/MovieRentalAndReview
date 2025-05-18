package com.moviereview.model;

public abstract class Review {
    public String getReviewText;
    private String movieTitle;
    private int rating;
    private String reviewText;
    private String reviewId;

    public Review(String movieTitle, int rating, String reviewText, String reviewId) {
        this.movieTitle = movieTitle;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewId = reviewId;
    }

    // Getters and Setters
    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    // Abstract method for polymorphic display
    public abstract String displayReview();
}