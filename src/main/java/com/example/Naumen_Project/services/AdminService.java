package com.example.Naumen_Project.services;

import com.example.Naumen_Project.DTO.*;
import com.example.Naumen_Project.models.Genre;
import com.example.Naumen_Project.models.Movie;
import com.example.Naumen_Project.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Value("${kinopoisk.token}")
    private String token;
    private final RestTemplate restTemplate = new RestTemplate();
    private final GenreService genreService;
    private final TypeService typeService;
    private final MovieRepository movieRepository;

    public AdminService(GenreService genreService, TypeService typeService, MovieRepository movieRepository) {
        this.genreService = genreService;
        this.typeService = typeService;
        this.movieRepository = movieRepository;
    }


    public void loadGenres() {
        var url = "https://api.kinopoisk.dev/v1/movie/possible-values-by-field?field=genres.name";
        var headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-API-KEY", token);
        var httpEntity = new HttpEntity<Void>(headers);
        var result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<GenreDTO>>() {
        });
        if (result.getBody() != null) {
            result.getBody().forEach(genreService::saveGenre);
        }

    }

    public void loadTypes() {
        var url = "https://api.kinopoisk.dev/v1/movie/possible-values-by-field?field=type";
        var headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-API-KEY", token);
        var httpEntity = new HttpEntity<Void>(headers);
        var result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<TypeDTO>>() {
        });
        if (result.getBody() != null) {
            if (result.getBody() != null) {
                result.getBody().forEach(typeService::saveType);
            }
        }

    }


    public List<KpMovieDTO> loadMovieByName(String name) {
        var url = "https://api.kinopoisk.dev/v1.2/movie/search?page=1&limit=10&query=" + name;
        var headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-API-KEY", token);
        var params = new HashMap<String, String>();

        var httpEntity = new HttpEntity<Void>(headers);
        var result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<KpMovieListDTO>() {
        });
        if (result.getBody() != null) {
            return result.getBody().getDocs();
        }
        return null;
    }

    public void createMovie(KpMovieDTO filmDTO) {
        if (movieRepository.existsByKpId(filmDTO.getKpId()))
            throw new IllegalArgumentException("Фильм уже есть в базе");

        var result = new Movie();
        result.setName(filmDTO.getName());
        result.setDescription(filmDTO.getDescription());
        result.setKpId(filmDTO.getKpId());
        result.setYear(filmDTO.getYear());
        result.setPosterUrl(filmDTO.getPoster());
        result.setKpRating(filmDTO.getRating());
        if (filmDTO.getGenres() != null)
            result.setMovieGenres(filmDTO.getGenres().stream().map(genreService::getGenreByName).collect(Collectors.toSet()));
        result.setMovieTypes(Set.of(typeService.getTypeByName(filmDTO.getType())));
        movieRepository.save(result);
    }

    public MovieDTO editMovie(MovieDTO filmDTO) {
        var result = movieRepository.findById(filmDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Данного фильма не существует id - " + filmDTO.getId()));
        result.setName(filmDTO.getName());
        result.setDescription(filmDTO.getDescription());
        result.setYear(filmDTO.getYear());
        result.setKpId(filmDTO.getKpId());
        result.setPosterUrl(filmDTO.getPoster());
        result.setKpRating(filmDTO.getRating());
        if (filmDTO.getGenres() != null)
            result.setMovieGenres(filmDTO.getGenres().stream().map(genreService::getGenreByName).collect(Collectors.toSet()));
        result.setMovieTypes(Set.of(typeService.getTypeByName(filmDTO.getType())));
        movieRepository.save(result);
        return filmDTO;
    }

    public void deleteMovie(long id) {
        movieRepository.deleteById(id);
    }

    public List<MovieDTO> getMovieList(int offset, int limit) {
        var page = movieRepository.findAll(PageRequest.of(offset, limit));
        return page.stream().map(movie -> {
            var result = new MovieDTO();
            result.setDescription(movie.getDescription());
            result.setRating(movie.getKpRating());
            result.setId(movie.getId());
            result.setKpId(movie.getKpId());
            result.setGenres(movie.getMovieGenres().stream().map(Genre::getName).collect(Collectors.toList()));
            result.setType(movie.getMovieTypes().stream().findFirst().get().getName());
            result.setName(movie.getName());
            result.setPoster(movie.getPosterUrl());
            result.setYear(movie.getYear());
            return result;
        }).collect(Collectors.toList());
    }
}
