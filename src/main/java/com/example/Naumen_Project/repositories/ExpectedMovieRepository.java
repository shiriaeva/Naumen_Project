package com.example.Naumen_Project.repositories;

import com.example.Naumen_Project.models.ExpectedMovie;
import com.example.Naumen_Project.models.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpectedMovieRepository extends JpaRepository<ExpectedMovie,Long> {
    List<ExpectedMovie> findAllByUserId(long userId, Pageable pageable);
    ExpectedMovie findByUserIdAndMovie(long userId, Movie movie);
    List<ExpectedMovie> findAllByMovieId(long movieId);
}
