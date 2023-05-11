package com.example.Naumen_Project.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    private long kpId;
    private double kpRating;
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private String posterUrl;
    private int year;

    @OneToMany(mappedBy = "movie")
    private List<LikedMovie> likedMovies;

    @OneToMany(mappedBy = "movie")
    private List<ExpectedMovie> expectedMovies;

    @OneToMany(mappedBy = "movie")
    private List<MovieRating> movieRatings;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "genre_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    Set<Genre> movieGenres;

    @ManyToMany
    @JoinTable(
            name = "type_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    Set<Type> movieTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getKpId() {
        return kpId;
    }

    public double getKpRating() {
        return kpRating;
    }

    public void setKpRating(double kpRating) {
        this.kpRating = kpRating;
    }

    public void setKpId(long kpId) {
        this.kpId = kpId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<LikedMovie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(List<LikedMovie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public List<ExpectedMovie> getExpectedMovies() {
        return expectedMovies;
    }

    public void setExpectedMovies(List<ExpectedMovie> expectedMovies) {
        this.expectedMovies = expectedMovies;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Genre> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(Set<Genre> movieGenres) {
        this.movieGenres = movieGenres;
    }

    public Set<Type> getMovieTypes() {
        return movieTypes;
    }

    public void setMovieTypes(Set<Type> movieTypes) {
        this.movieTypes = movieTypes;
    }

    public List<MovieRating> getMovieRatings() {
        return movieRatings;
    }

    public void setMovieRatings(List<MovieRating> movieRatings) {
        this.movieRatings = movieRatings;
    }
}
