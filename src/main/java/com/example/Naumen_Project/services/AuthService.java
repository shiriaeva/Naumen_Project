package com.example.Naumen_Project.services;

import com.example.Naumen_Project.dto.LoginRequest;
import com.example.Naumen_Project.dto.RegistrationRequest;
import com.example.Naumen_Project.models.UserEntity;
import com.example.Naumen_Project.models.UserRole;
import com.example.Naumen_Project.security.AppUserDetailService;
import com.example.Naumen_Project.security.AppUserDetails;
import com.example.Naumen_Project.security.jwt.JwtService;
import com.example.Naumen_Project.repositories.UserRepository;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailService;
    private final JwtService jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, AppUserDetailService bookstoreUserDetailsService, JwtService jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public UserEntity register(RegistrationRequest registrationDTO) {

        if (userRepository.findByUsername(registrationDTO.getUsername()).isEmpty()) {
            UserEntity user = new UserEntity();
            user.setName(registrationDTO.getName());
            user.setEmail(registrationDTO.getEmail());
            user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
            user.setUsername(registrationDTO.getUsername());
            user.setUserRole(UserRole.AUTHORIZED_ROLE);
            user.setSurname(registrationDTO.getSurname());
            user.setSecondName(registrationDTO.getSecondName());
            user.setRegistrationDate(new Date());
            userRepository.save(user);
            return user;
        } else
            return null;
    }

    public String login(LoginRequest loginDTO) throws AuthException {
        try {
            var authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
                    loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var userDetails = userDetailService.loadUserByUsername(loginDTO.getUsername());
            return jwtUtil.generateToken(userDetails);
        } catch (Exception e) {
            throw new AuthException(e.getMessage());
        }
    }

    public AppUserDetails getCurrentUser() {
        return (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
