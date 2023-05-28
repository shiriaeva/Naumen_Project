package com.example.Naumen_Project.services;


import com.example.Naumen_Project.dto.MovieCommon;
import com.example.Naumen_Project.dto.user.UserCommon;
import com.example.Naumen_Project.models.UserEntity;
import com.example.Naumen_Project.repositories.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final LikedMovieRepository likedMovieRepository;
    private final ExpectedMovieRepository expectedMovieRepository;
    private final ReviewRepository reviewRepository;

    public UserService(LikedMovieRepository likedMovieRepository, ExpectedMovieRepository expectedMovieRepository, ReviewRepository reviewRepository) {
        this.likedMovieRepository = likedMovieRepository;
        this.expectedMovieRepository = expectedMovieRepository;
        this.reviewRepository = reviewRepository;
    }

    public UserCommon getUserCommonInfo(UserEntity user) {
        var result = new UserCommon();
        result.setName(user.getName());
        result.setEmail(user.getEmail());
        result.setUsername(user.getUsername());
        result.setSurname(user.getSurname());
        result.setSecondName(user.getSecondName());
        return result;
    }

    public List<MovieCommon> getUserLikedMovies(UserEntity user, int offset, int limit) {
        var movies = likedMovieRepository.findAllByUserId(user.getId(), PageRequest.of(offset, limit));
        return movies.stream().map(
                likedMovie -> {
                    var movie = likedMovie.getMovie();
                    return MovieCommon.fromMovie(movie);
                }
        ).collect(Collectors.toList());
    }

    public List<MovieCommon> getExpectedMovies(UserEntity user, int offset, int limit) {
        var movies = expectedMovieRepository.findAllByUserId(user.getId(), PageRequest.of(offset, limit));
        return movies.stream().map(
                likedMovie -> {
                    var movie = likedMovie.getMovie();
                    return MovieCommon.fromMovie(movie);
                }
        ).collect(Collectors.toList());
    }

    public List<MovieCommon> getRatedMovies(UserEntity user, int offset, int limit) {
        var reviews = reviewRepository.findAllByUserId(user.getId(),PageRequest.of(offset, limit));
        return reviews.stream().map(
                review -> {
                    var movie = review.getMovie();
                    return MovieCommon.fromMovie(movie);
                }
        ).collect(Collectors.toList());
    }
}
