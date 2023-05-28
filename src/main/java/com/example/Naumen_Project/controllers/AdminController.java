package com.example.Naumen_Project.controllers;

import com.example.Naumen_Project.dto.KpMovie;
import com.example.Naumen_Project.dto.MovieCommon;
import com.example.Naumen_Project.services.AdminService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/")
    public String admin(Model model) {
        model.addAttribute("users", adminService.getUsers(0,20));
        model.addAttribute("films", adminService.getMovieList(0, 20));
        return "admin";
    }

    @GetMapping("/addFilm")
    public String addFilm(Model model) {
        return "add-film";
    }

    @GetMapping("/load/genres")
    @ResponseBody
    public void loadGenresFromKinopoisk() {
        adminService.loadGenres();
    }

    @GetMapping("/load/types")
    @ResponseBody
    public void loadTypesFromKinopoisk() {
        adminService.loadTypes();
    }

    @GetMapping("/movie/load")
    @ResponseBody
    public List<KpMovie> loadMovieByName(@RequestParam String name, @RequestParam int page, @RequestParam int limit) {
        return adminService.loadMovieByName(name,page,limit);
    }

    @PostMapping(value = "/movie/create",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void createMovie(@RequestBody KpMovie filmDTO) {
        adminService.createMovie(filmDTO);
    }

    @GetMapping("/movie/list/{offset}/{limit}")
    @ResponseBody
    public List<MovieCommon> getMovieList(@PathVariable int offset, @PathVariable int limit) {
        return adminService.getMovieList(offset, limit);
    }

    @PutMapping("/movie/edit")
    @ResponseBody
    public MovieCommon editFilm(@RequestBody MovieCommon movie) {
        return adminService.editMovie(movie);
    }

    @DeleteMapping("/movie/delete/{id}")
    @ResponseBody
    public void deleteFilm(@PathVariable long id) {
        adminService.deleteMovie(id);
    }
}
