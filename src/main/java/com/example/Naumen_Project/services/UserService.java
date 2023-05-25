package com.example.Naumen_Project.services;


import com.example.Naumen_Project.dto.MovieDTO;
import com.example.Naumen_Project.dto.user.UserCommonDTO;
import com.example.Naumen_Project.models.UserEntity;
import com.example.Naumen_Project.repositories.ExpectedMovieRepository;
import com.example.Naumen_Project.repositories.LikedMovieRepository;
import com.example.Naumen_Project.repositories.MovieRatingRepository;
import com.example.Naumen_Project.repositories.MovieRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final LikedMovieRepository likedMovieRepository;
    private final ExpectedMovieRepository expectedMovieRepository;
    private final MovieRatingRepository movieRatingRepository;
    private final MovieRepository movieRepository;

    public UserService(LikedMovieRepository likedMovieRepository, ExpectedMovieRepository expectedMovieRepository, MovieRatingRepository movieRatingRepository, MovieRepository movieRepository) {
        this.likedMovieRepository = likedMovieRepository;
        this.expectedMovieRepository = expectedMovieRepository;
        this.movieRatingRepository = movieRatingRepository;
        this.movieRepository = movieRepository;
    }

    public UserCommonDTO getUserCommonInfo(UserEntity user) {
        var result = new UserCommonDTO();
        result.setName(user.getName());
        result.setEmail(user.getEmail());
        result.setUsername(user.getUsername());
        result.setSurname(user.getSurname());
        result.setSecondName(user.getSecondName());
        return result;
    }

    public List<MovieDTO> getUserLikedMovies(UserEntity user, int offset, int limit) {
        var movies = likedMovieRepository.findAllByUserId(user.getId(), PageRequest.of(offset, limit));
        return movies.stream().map(
                likedMovie -> {
                    var movie = likedMovie.getMovie();
                    return MovieDTO.fromMovie(movie);
                }
        ).collect(Collectors.toList());
    }

    public List<MovieDTO> getExpectedMovies(UserEntity user, int offset, int limit) {
        var movies = expectedMovieRepository.findAllByUserId(user.getId(), PageRequest.of(offset, limit));
        return movies.stream().map(
                likedMovie -> {
                    var movie = likedMovie.getMovie();
                    return MovieDTO.fromMovie(movie);
                }
        ).collect(Collectors.toList());
    }

    public List<MovieDTO> getRatedMovies(UserEntity user, int offset, int limit) {
        var movies = movieRatingRepository.findAllByUserId(user.getId(), PageRequest.of(offset, limit));
        return movies.stream().map(
                likedMovie -> {
                    var movie = likedMovie.getMovie();
                    return MovieDTO.fromMovie(movie);
                }
        ).collect(Collectors.toList());
    }
}
