package com.example.Naumen_Project.repositories;

import com.example.Naumen_Project.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    boolean existsByKpId(long id);
}
