package com.example.Naumen_Project.controllers;

import com.example.Naumen_Project.dto.LoginRequest;
import com.example.Naumen_Project.dto.RegistrationRequest;
import com.example.Naumen_Project.models.UserRole;
import com.example.Naumen_Project.services.AuthService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/signin")
    public String handleSignIn(Model model) {
        model.addAttribute("loginForm", new LoginRequest());
        return "signin";
    }

    @GetMapping("/signup")
    public String handleSignUp(Model model) {
        model.addAttribute("registrationForm", new RegistrationRequest());
        return "signup";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginRequest loginForm, BindingResult bindingResult, HttpServletResponse response, Model model) {
        if (bindingResult.hasErrors()) {
            //model.addAttribute("loginForm", loginForm);
            return "/signin";
        }
        try {
            var token = authService.login(loginForm);
            response.addCookie(new Cookie("token", token));
            var user = authService.getCurrentUser();
            if (user.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ADMIN_ROLE.name()))) {
                return "redirect:/admin/";
            }
            return "redirect:/user";
        } catch (Exception e) {
           // model.addAttribute("loginForm", loginForm);
            return "/signin";
        }
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registrationForm") RegistrationRequest registrationForm, BindingResult bindingResult, HttpServletResponse response, Model model) throws AuthException {
        if (bindingResult.hasErrors()) {
            //model.addAttribute("registrationForm", registrationForm);
            return "signup";
        }
        var user = authService.register(registrationForm);
        var token = authService.login(new LoginRequest(user.getUsername(), registrationForm.getPassword()));
        response.addCookie(new Cookie("token", token));
        return "redirect:/user";
    }


}
