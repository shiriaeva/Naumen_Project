package com.example.Naumen_Project.repositories;

import com.example.Naumen_Project.models.Movie;
import com.example.Naumen_Project.models.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByUserId(long userId, Pageable pageable);
    Review findByUserIdAndMovie(long userId, Movie movie);
    List<Review> findAllByMovieId(long movieId);
}
