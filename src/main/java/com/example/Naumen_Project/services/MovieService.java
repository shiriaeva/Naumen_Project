package com.example.Naumen_Project.services;

import com.example.Naumen_Project.DTO.MovieDTO;
import com.example.Naumen_Project.models.ExpectedMovie;
import com.example.Naumen_Project.models.LikedMovie;
import com.example.Naumen_Project.models.UserEntity;
import com.example.Naumen_Project.repositories.ExpectedMovieRepository;
import com.example.Naumen_Project.repositories.LikedMovieRepository;
import com.example.Naumen_Project.repositories.MovieRepository;
import com.example.Naumen_Project.repositories.UserRepository;
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
    private final UserRepository userRepository;

    public MovieService(MovieRepository movieRepository, LikedMovieRepository likedRepository, ExpectedMovieRepository expectedRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.likedRepository = likedRepository;
        this.expectedRepository = expectedRepository;
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

        if (user.getLikedMovies() == null) {
            user.setLikedMovies(new ArrayList<LikedMovie>());
            userRepository.save(user);
        }

        var likedOrNot = likedRepository.findByUserIdAndMovie(user.getId(), movie);
        if (likedOrNot == null){
            var newLiked = new LikedMovie(movie, user);
            likedRepository.save(newLiked);
            user.getLikedMovies().add(newLiked);
            userRepository.save(user);
        }
        else {
            user.getLikedMovies().remove(likedOrNot);
            userRepository.save(user);
            likedRepository.delete(likedOrNot);
        }
    }

    public void addToExpected(UserEntity user, MovieDTO filmDTO) {
        var movie = movieRepository.findById(
                        filmDTO.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Данного фильма не существует id - " + filmDTO.getId()));

        if (user.getExpectedMovies() == null) {
            user.setExpectedMovies(new ArrayList<ExpectedMovie>());
            userRepository.save(user);
        }

        var expectedOrNot = expectedRepository.findByUserIdAndMovie(user.getId(), movie);
        if (expectedOrNot == null){
            var newExpected = new ExpectedMovie(movie, user);
            expectedRepository.save(newExpected);
            user.getExpectedMovies().add(newExpected);
            userRepository.save(user);
        }
        else {
            user.getExpectedMovies().remove(expectedOrNot);
            userRepository.save(user);
            expectedRepository.delete(expectedOrNot);
        }
    }
}
