package com.example.Naumen_Project.dto;

import com.example.Naumen_Project.models.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class ReviewCommon {
    private String reviewText;
    @Min(0)
    @Max(10)
    private int rating;
    private Long movieId;

    public ReviewCommon(String reviewText, int rating, Long movieId) {
        this.reviewText = reviewText;
        this.rating = rating;
        this.movieId = movieId;
    }

    public ReviewCommon() {

    }

    public static ReviewCommon fromReview(Review review){
        var result = new ReviewCommon();
        result.setRating(review.getRating());
        result.setReviewText(review.getReviewText());
        result.setMovieId(review.getMovie().getId());

        return result;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
