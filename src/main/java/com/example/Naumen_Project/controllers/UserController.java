package com.example.Naumen_Project.controllers;

import com.example.Naumen_Project.DTO.KpMovieDTO;
import com.example.Naumen_Project.DTO.MovieDTO;
import com.example.Naumen_Project.DTO.ReviewDTO;
import com.example.Naumen_Project.DTO.user.UserCommonDTO;
import com.example.Naumen_Project.services.AuthService;
import com.example.Naumen_Project.services.MovieService;
import com.example.Naumen_Project.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final MovieService movieService;

    public UserController(UserService userService, AuthService authService, MovieService movieService) {
        this.userService = userService;
        this.authService = authService;
        this.movieService = movieService;
    }

    @GetMapping("")
    public UserCommonDTO getUserInfo() {
        var user = authService.getCurrentUser();
        return userService.getUserCommonInfo(user.getUser());
    }

    @GetMapping("/liked/movies/{offset}/{limit}")
    public List<MovieDTO> getLikedMovieList(@PathVariable int offset, @PathVariable int limit) {
        var user = authService.getCurrentUser();
        return userService.getUserLikedMovies(user.getUser(), offset, limit);
    }

    @GetMapping("/expected/movies/{offset}/{limit}")
    public List<MovieDTO> getExpectedMovieList(@PathVariable int offset, @PathVariable int limit) {
        var user = authService.getCurrentUser();
        return userService.getExpectedMovies(user.getUser(), offset, limit);
    }

    @GetMapping("/rated/movies/{offset}/{limit}")
    public List<MovieDTO> getRatedList(@PathVariable int offset, @PathVariable int limit) {
        var user = authService.getCurrentUser();
        return userService.getRatedMovies(user.getUser(), offset, limit);
    }

    @PostMapping("/liked/movie/")
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Movie added to liked")
    public void addMovieToLiked(@RequestBody MovieDTO filmDTO) {
        var user = authService.getCurrentUser();
        movieService.addToLiked(user.getUser(), filmDTO);
    }

    @PostMapping("/expected/movie/")
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Movie added to expected")
    public void addMovieToExpected(@RequestBody MovieDTO filmDTO) {
        var user = authService.getCurrentUser();
        movieService.addToExpected(user.getUser(), filmDTO);
    }

    @PostMapping("/movie/review")
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Review created")
    public void createMovieReview(@RequestBody ReviewDTO reviewDTO) {
        var user = authService.getCurrentUser();
        movieService.createReview(user.getUser(), reviewDTO);
    }

}
