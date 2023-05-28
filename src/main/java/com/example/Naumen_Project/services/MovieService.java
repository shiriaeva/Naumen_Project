package com.example.Naumen_Project.services;

import com.example.Naumen_Project.dto.DetailMovieDTO;
import com.example.Naumen_Project.dto.DetailReview;
import com.example.Naumen_Project.dto.MovieDTO;
import com.example.Naumen_Project.dto.ReviewDTO;
import com.example.Naumen_Project.models.ExpectedMovie;
import com.example.Naumen_Project.models.LikedMovie;
import com.example.Naumen_Project.models.Review;
import com.example.Naumen_Project.models.UserEntity;
import com.example.Naumen_Project.repositories.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final LikedMovieRepository likedRepository;
    private final ExpectedMovieRepository expectedRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public MovieService(MovieRepository movieRepository, LikedMovieRepository likedRepository, ExpectedMovieRepository expectedRepository, ReviewRepository reviewRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.likedRepository = likedRepository;
        this.expectedRepository = expectedRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public List<MovieDTO> getMovieList(int offset, int limit) {
        var movies = movieRepository.findAll(PageRequest.of(offset, limit));
        return movies.stream().map(
                MovieDTO::fromMovie
        ).collect(Collectors.toList());
    }

    public void addToLiked(UserEntity user, MovieDTO filmDTO) {
        var movie = movieRepository.findById(
                filmDTO.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Данного фильма не существует id - " + filmDTO.getId()));

        var likedOrNot = likedRepository.findByUserIdAndMovie(user.getId(), movie);
        if (likedOrNot == null){
            var newLiked = new LikedMovie(movie, user);
            likedRepository.save(newLiked);
            userRepository.save(user);
        }
        else {
            likedRepository.delete(likedOrNot);
            userRepository.save(user);
        }
    }

    public void addToExpected(UserEntity user, MovieDTO filmDTO) {
        var movie = movieRepository.findById(
                        filmDTO.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Данного фильма не существует id - " + filmDTO.getId()));

        var expectedOrNot = expectedRepository.findByUserIdAndMovie(user.getId(), movie);
        if (expectedOrNot == null){
            var newExpected = new ExpectedMovie(movie, user);
            expectedRepository.save(newExpected);
            userRepository.save(user);
        }
        else {
            expectedRepository.delete(expectedOrNot);
            userRepository.save(user);
        }
    }

    public DetailReview createReview(UserEntity user, ReviewDTO reviewDTO){
        var movie = movieRepository.findById(
                        reviewDTO.getMovieId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Данного фильма не существует id - " + reviewDTO.getMovieId()));

        var existedReview = reviewRepository.findByUserIdAndMovie(user.getId(), movie);
        if (existedReview == null){
            var newReview = new Review(reviewDTO.getReviewText(),
                    reviewDTO.getRating(), user, movie);
            reviewRepository.save(newReview);
            userRepository.save(user);
            movieRepository.save(movie);
            return DetailReview.fromReview(newReview, user.getUsername());
        }
        else {
            return DetailReview.fromReview(existedReview, user.getUsername());
        }
    }

    public DetailMovieDTO getMovieById(int movieId) {
        var movie = movieRepository.findById((long) movieId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Данного фильма не существует id - " + movieId));
        var reviews = new ArrayList<DetailReview>();
        movie.getReviews().forEach(review -> reviews.add(DetailReview.fromReview(review, review.getUser().getUsername())));
        return DetailMovieDTO.fromMovie(movie, reviews);
    }

    public DetailMovieDTO getMovieBySlug(String slug) {
        try {
            var movie = movieRepository.findMovieBySlug(slug);
            var reviews = new ArrayList<DetailReview>();
            movie.getReviews().forEach(review -> reviews.add(DetailReview.fromReview(review, review.getUser().getUsername())));
            return DetailMovieDTO.fromMovie(movie, reviews);
        } catch (Exception e) {
            throw new IllegalArgumentException("Данного фильма не существует - " + slug);
        }
    }
}
