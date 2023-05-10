package com.example.Naumen_Project.repositories;

import com.example.Naumen_Project.models.LikedMovie;
import com.example.Naumen_Project.models.MovieRating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRatingRepository extends JpaRepository<MovieRating,Long> {
    List<MovieRating> findAllByUserId(long userId, Pageable pageable);

}
