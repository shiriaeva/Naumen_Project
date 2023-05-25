package com.example.Naumen_Project.dto;

import com.example.Naumen_Project.models.Genre;
import com.example.Naumen_Project.models.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieDTO {
    private Long id;
    private Long kpId;
    private String name;
    private String type;
    private int year;
    private String description;

    private String poster;
    private double rating;
    private List<String> genres;



    public static MovieDTO fromMovie(Movie movie){
        var result = new MovieDTO();
        result.setDescription(movie.getDescription());
        result.setRating(movie.getKpRating());
        result.setId(movie.getId());
        result.setKpId(movie.getKpId());
        result.setGenres(movie.getMovieGenres().stream().map(Genre::getName).collect(Collectors.toList()));
        result.setType(movie.getMovieTypes().stream().findFirst().get().getName());
        result.setName(movie.getName());
        result.setPoster(movie.getPosterUrl());
        result.setYear(movie.getYear());
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKpId() {
        return kpId;
    }

    public void setKpId(Long kpId) {
        this.kpId = kpId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
