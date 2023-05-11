package com.example.Naumen_Project.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class ExpectedMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expected_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="movie_id", nullable=false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;

    public ExpectedMovie() {
    }

    public ExpectedMovie(Movie movie, UserEntity user) {
        this.movie = movie;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
