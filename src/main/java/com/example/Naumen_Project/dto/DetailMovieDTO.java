package com.example.Naumen_Project.dto;

import com.example.Naumen_Project.models.Genre;
import com.example.Naumen_Project.models.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class DetailMovieDTO extends MovieDTO{
    private List<DetailReview> reviews;

    public DetailMovieDTO() {
    }

    public static DetailMovieDTO fromMovie(Movie movie, List<DetailReview> reviews){
        var result = new DetailMovieDTO();
        result.setDescription(movie.getDescription());
        result.setRating(movie.getKpRating());
        result.setId(movie.getId());
        result.setKpId(movie.getKpId());
        result.setGenres(movie.getMovieGenres().stream().map(Genre::getName).collect(Collectors.toList()));
        result.setType(movie.getMovieTypes().stream().findFirst().get().getName());
        result.setName(movie.getName());
        result.setPoster(movie.getPosterUrl());
        result.setYear(movie.getYear());
        result.setReviews(reviews);
        return result;
    }

    public List<DetailReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<DetailReview> reviews) {
        this.reviews = reviews;
    }
}
