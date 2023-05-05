package com.example.Naumen_Project.controllers;

import com.example.Naumen_Project.DTO.KpMovieDTO;
import com.example.Naumen_Project.DTO.MovieDTO;
import com.example.Naumen_Project.services.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/load/genres")
    public void loadGenresFromKinopoisk() {
        adminService.loadGenres();
    }

    @GetMapping("/load/types")
    public void loadTypesFromKinopoisk() {
        adminService.loadTypes();
    }

    @GetMapping("/load/Movie")
    public List<KpMovieDTO> loadMovieByName(@RequestParam String name) {
        return adminService.loadMovieByName(name);
    }

    @PostMapping("movie/create")
    public void createMovie(@RequestBody KpMovieDTO filmDTO) {
        adminService.createMovie(filmDTO);
    }

    @PutMapping("movie/edit")
    public MovieDTO editFilm(@RequestBody MovieDTO movie) {
        return adminService.editMovie(movie);
    }

    @DeleteMapping("movie/delete/{id}")
    public void deleteFilm(@PathVariable long id) {
        adminService.deleteMovie(id);
    }
}
