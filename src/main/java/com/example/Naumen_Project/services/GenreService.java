package com.example.Naumen_Project.services;

import com.example.Naumen_Project.dto.GenreCommon;
import com.example.Naumen_Project.models.Genre;
import com.example.Naumen_Project.repositories.GenreRepository;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void saveGenre(GenreCommon genreDTO) {
        if (!genreRepository.existsByName(genreDTO.getName())) {
            var result = new Genre();
            result.setName(genreDTO.getName());
            genreRepository.save(result);
        }
    }

    public Genre getGenreByName(String name) {
        return genreRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Жанра " + name + " не существует"));
    }
}
