package com.example.Naumen_Project.services;

import com.example.Naumen_Project.dto.*;
import com.example.Naumen_Project.models.Movie;
import com.example.Naumen_Project.models.UserEntity;
import com.example.Naumen_Project.repositories.MovieRepository;
import com.example.Naumen_Project.repositories.UserRepository;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Console;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    private final UserRepository userRepository;

    public AdminService(GenreService genreService, TypeService typeService, MovieRepository movieRepository, UserRepository userRepository) {
        this.genreService = genreService;
        this.typeService = typeService;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
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


    public List<KpMovieDTO> loadMovieByName(String name,int page,int limit) {

        var param = URLDecoder.decode(name, StandardCharsets.UTF_8);
        var url = "https://api.kinopoisk.dev/v1.2/movie/search?page="+page+"&limit="+limit+"&query=" +param;
        var headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-API-KEY", token);
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

        final Slugify slg = Slugify.builder().transliterator(true).build();
        var result = new Movie();
        result.setName(filmDTO.getName());
        result.setDescription(filmDTO.getDescription());
        result.setKpId(filmDTO.getKpId());
        result.setYear(filmDTO.getYear());
        result.setSlug(slg.slugify(filmDTO.getName()));
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
        return page.stream().map(MovieDTO::fromMovie).collect(Collectors.toList());
    }

    public List<UserEntity> getUsers(int offset, int limit) {
        return userRepository.findAll(PageRequest.of(offset, limit)).stream().toList();
    }
}
