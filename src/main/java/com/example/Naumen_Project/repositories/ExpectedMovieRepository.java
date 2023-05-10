package com.example.Naumen_Project.repositories;

import com.example.Naumen_Project.models.ExpectedMovie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpectedMovieRepository extends JpaRepository<ExpectedMovie,Long> {
    List<ExpectedMovie> findAllByUserId(long userId, Pageable pageable);

}
