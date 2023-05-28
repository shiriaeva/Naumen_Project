package com.example.Naumen_Project.controllers;

import com.example.Naumen_Project.dto.DetailMovie;
import com.example.Naumen_Project.dto.MovieCommon;
import com.example.Naumen_Project.services.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{offset}/{limit}")
    @ResponseBody
    public List<MovieCommon> getMovies(@PathVariable int offset, @PathVariable int limit) {
        return movieService.getMovieList(offset, limit);
    }

    @GetMapping("/{movieId}")
    @ResponseBody
    public DetailMovie getMovie(@PathVariable int movieId) {
        return movieService.getMovieById(movieId);
    }

    @GetMapping("/")
    public String movies(Model model) {
        model.addAttribute("movies", movieService.getMovieList(0, 20));
        return "movies";
    }

    @GetMapping("/{slug}/")
    public String movie(@PathVariable String slug, Model model) {
        model.addAttribute("movie", movieService.getMovieBySlug(slug));
        return "movie";
    }

}
