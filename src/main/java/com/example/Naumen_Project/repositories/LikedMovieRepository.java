package com.example.Naumen_Project.repositories;

import com.example.Naumen_Project.models.LikedMovie;
import com.example.Naumen_Project.models.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedMovieRepository extends JpaRepository<LikedMovie,Long> {

    List<LikedMovie> findAllByUserId(long userId, Pageable pageable);
    LikedMovie findByUserIdAndMovie(long userId, Movie movie);
    List<LikedMovie> findAllByMovieId(long movieId);
}
