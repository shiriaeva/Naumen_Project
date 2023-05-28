package com.example.Naumen_Project.controllers;

import com.example.Naumen_Project.dto.DetailReview;
import com.example.Naumen_Project.dto.MovieDTO;
import com.example.Naumen_Project.dto.ReviewDTO;
import com.example.Naumen_Project.services.AuthService;
import com.example.Naumen_Project.services.MovieService;
import com.example.Naumen_Project.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
    public String getUserInfo(Model model) {
        var offset = 0;
        var limit = 10;
        var user = authService.getCurrentUser();
        model.addAttribute("userInfo",userService.getUserCommonInfo(user.getUser()));
        model.addAttribute("lickedMovies",userService.getUserLikedMovies(user.getUser(), offset, limit));
        model.addAttribute("expectedMovies",userService.getExpectedMovies(user.getUser(), offset, limit));
        model.addAttribute("ratedMovies",userService.getRatedMovies(user.getUser(), offset, limit));
        return "personal_area";
    }

    @GetMapping("/liked/movies/{offset}/{limit}")
    @ResponseBody
    public List<MovieDTO> getLikedMovieList(@PathVariable int offset, @PathVariable int limit) {
        var user = authService.getCurrentUser();
        return userService.getUserLikedMovies(user.getUser(), offset, limit);
    }

    @GetMapping("/expected/movies/{offset}/{limit}")
    @ResponseBody
    public List<MovieDTO> getExpectedMovieList(@PathVariable int offset, @PathVariable int limit) {
        var user = authService.getCurrentUser();
        return userService.getExpectedMovies(user.getUser(), offset, limit);
    }

    @GetMapping("/rated/movies/{offset}/{limit}")
    @ResponseBody
    public List<MovieDTO> getRatedList(@PathVariable int offset, @PathVariable int limit) {
        var user = authService.getCurrentUser();
        return userService.getRatedMovies(user.getUser(), offset, limit);
    }

    @PostMapping("/liked/movie/")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Movie added to liked")
    public void addMovieToLiked(@RequestBody MovieDTO filmDTO) {
        var user = authService.getCurrentUser();
        movieService.addToLiked(user.getUser(), filmDTO);
    }

    @PostMapping("/expected/movie/")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Movie added to expected")
    public void addMovieToExpected(@RequestBody MovieDTO filmDTO) {
        var user = authService.getCurrentUser();
        movieService.addToExpected(user.getUser(), filmDTO);
    }

    @PostMapping("/movie/review")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Review created")
    public DetailReview createMovieReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        var user = authService.getCurrentUser();
        return movieService.createReview(user.getUser(), reviewDTO);
    }

}
