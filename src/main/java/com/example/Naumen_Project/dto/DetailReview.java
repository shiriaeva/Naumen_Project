package com.example.Naumen_Project.dto;

import com.example.Naumen_Project.models.Review;

public class DetailReview extends ReviewCommon {
    private String username;

    public DetailReview() {

    }

    public static DetailReview fromReview(Review review, String username){
        var result = new DetailReview();
        result.setRating(review.getRating());
        result.setReviewText(review.getReviewText());
        result.setUsername(username);
        result.setMovieId(review.getMovie().getId());

        return result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
