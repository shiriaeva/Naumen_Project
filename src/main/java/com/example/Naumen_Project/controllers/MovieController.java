package com.example.Naumen_Project.controllers;

import com.example.Naumen_Project.dto.DetailMovie;
import com.example.Naumen_Project.dto.MovieCommon;
import com.example.Naumen_Project.repositories.GenreRepository;
import com.example.Naumen_Project.services.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final GenreRepository genreRepository;

    public MovieController(MovieService movieService, GenreRepository genreRepository) {
        this.movieService = movieService;
        this.genreRepository = genreRepository;
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
        model.addAttribute("genres", movieService.getGenreList());
        return "movies";
    }

    @GetMapping("/genre/{genreName}")
    @ResponseBody
    public List<MovieCommon> index(Model model, @PathVariable String genreName) {
        var genre = genreRepository.findByName(genreName);
        model.addAttribute("genre_movies", movieService.getGenreMovies(genre));
        return movieService.getGenreMovies(genre);
    }

    @GetMapping("/{slug}/")
    public String movie(@PathVariable String slug, Model model) {
        model.addAttribute("movie", movieService.getMovieBySlug(slug));
        return "movie";
    }

}
