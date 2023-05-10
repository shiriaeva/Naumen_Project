package com.example.Naumen_Project.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String surname;
    private String secondName;
    @Column(name = "username",unique = true)
    private String username;
    private String password;
    @Column(name="email",unique = true)
    private String email;
    private Date registrationDate;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(mappedBy = "user")
    private List<LikedMovie> likedMovies;

    @OneToMany(mappedBy = "user")
    private List<ExpectedMovie> expectedMovies;

    @OneToMany(mappedBy = "user")
    private List<MovieRating> movieRatings;

    @Enumerated(EnumType.STRING)
    public UserRole userRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
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

    public List<MovieRating> getMovieRatings() {
        return movieRatings;
    }

    public void setMovieRatings(List<MovieRating> movieRatings) {
        this.movieRatings = movieRatings;
    }
}
