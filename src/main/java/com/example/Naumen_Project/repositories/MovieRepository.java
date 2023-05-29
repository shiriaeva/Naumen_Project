package com.example.Naumen_Project.repositories;

import com.example.Naumen_Project.models.Genre;
import com.example.Naumen_Project.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    boolean existsByKpId(long id);
    Movie findMovieBySlug(String slug);
    List<Movie> getMoviesByMovieGenresContains(Optional<Genre> genre);
}
