package com.example.Naumen_Project.controllers;

import com.example.Naumen_Project.dto.LoginRequest;
import com.example.Naumen_Project.dto.RegistrationRequest;
import com.example.Naumen_Project.services.AuthService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthController {

    private final AuthService authService;

    public RestAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("api/login")
    public void login(@Valid @RequestBody LoginRequest loginForm,HttpServletResponse response) throws AuthException {
        var token = authService.login(loginForm);
        var cookie =new Cookie("token", token);
        cookie.setPath("/");
        cookie.setDomain("");
        response.addCookie(cookie);
    }

    @PostMapping("api/register")
    public void register(@Valid @RequestBody RegistrationRequest registrationForm, HttpServletResponse response) throws AuthException {
        var user = authService.register(registrationForm);
        var token = authService.login(new LoginRequest(user.getUsername(), registrationForm.getPassword()));
        var cookie =new Cookie("token", token);
        cookie.setPath("/");
        cookie.setDomain("");
        response.addCookie(cookie);
    }
}
