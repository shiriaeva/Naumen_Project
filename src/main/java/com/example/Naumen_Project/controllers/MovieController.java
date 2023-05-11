package com.example.Naumen_Project.controllers;

import com.example.Naumen_Project.DTO.DetailMovieDTO;
import com.example.Naumen_Project.DTO.MovieDTO;
import com.example.Naumen_Project.services.AuthService;
import com.example.Naumen_Project.services.MovieService;
import com.example.Naumen_Project.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{offset}/{limit}")
    public List<MovieDTO> getMovies(@PathVariable int offset, @PathVariable int limit) {
        return movieService.getMovieList(offset, limit);
    }

    @GetMapping("/{movieId}")
    public DetailMovieDTO getMovie(@PathVariable int movieId) {
        return movieService.getMovie(movieId);
    }

}
