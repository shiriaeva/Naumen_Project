package com.example.Naumen_Project.repositories;

import com.example.Naumen_Project.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
   boolean existsByName(String name);
   Genre findByName(String name);
}
