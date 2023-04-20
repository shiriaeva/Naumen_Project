package com.example.Naumen_Project.controllers;

import com.example.Naumen_Project.DTO.LoginRequest;
import com.example.Naumen_Project.DTO.RegistrationRequest;
import com.example.Naumen_Project.models.UserEntity;
import com.example.Naumen_Project.services.AuthService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginDTO, HttpServletResponse response) throws AuthException {
        var token = authService.login(loginDTO);
        response.addCookie(new Cookie("token", token));
    }

    @PostMapping("/register")
    public UserEntity register(@RequestBody RegistrationRequest registrationDTO) {
        return authService.register(registrationDTO);
    }


}
