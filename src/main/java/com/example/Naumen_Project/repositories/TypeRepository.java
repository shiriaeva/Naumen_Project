package com.example.Naumen_Project.repositories;

import com.example.Naumen_Project.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    boolean existsByName(String name);

    Optional<Type> findByName(String type);
}
