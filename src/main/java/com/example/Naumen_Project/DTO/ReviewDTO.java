package com.example.Naumen_Project.DTO;

import com.example.Naumen_Project.models.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewDTO {
    private String reviewText;
    private int rating;
    private Long userId;
    private Long movieId;

    public ReviewDTO(String reviewText, int rating, Long userId, Long movieId) {
        this.reviewText = reviewText;
        this.rating = rating;
        this.userId = userId;
        this.movieId = movieId;
    }

    public ReviewDTO() {

    }

    public static ReviewDTO fromReview(Review review){
        var result = new ReviewDTO();
        result.setRating(review.getRating());
        result.setReviewText(review.getReviewText());
        result.setUserId(review.getUser().getId());
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
