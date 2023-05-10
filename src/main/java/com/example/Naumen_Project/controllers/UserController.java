package com.example.Naumen_Project.controllers;

import com.example.Naumen_Project.DTO.MovieDTO;
import com.example.Naumen_Project.DTO.user.UserCommonDTO;
import com.example.Naumen_Project.services.AuthService;
import com.example.Naumen_Project.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("")
    public UserCommonDTO getUserInfo() {
        var user = authService.getCurrentUser();
        return userService.getUserCommonInfo(user.getUser());
    }

    @GetMapping("/liked/movies/{offset}/{limit}")
    public List<MovieDTO> getLikedMovieList(@PathVariable int offset, @PathVariable int limit) {
        var user = authService.getCurrentUser();
        return userService.getUserLikedMovies(user.getUser(), offset, limit);
    }

    @GetMapping("/expected/movies/{offset}/{limit}")
    public List<MovieDTO> getExpectedMovieList(@PathVariable int offset, @PathVariable int limit) {
        var user = authService.getCurrentUser();
        return userService.getExpectedMovies(user.getUser(), offset, limit);
    }

    @GetMapping("/rated/movies/{offset}/{limit}")
    public List<MovieDTO> getRatedList(@PathVariable int offset, @PathVariable int limit) {
        var user = authService.getCurrentUser();
        return userService.getRatedMovies(user.getUser(), offset, limit);
    }

}
