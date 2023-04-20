package com.example.Naumen_Project.services;

import com.example.Naumen_Project.DTO.LoginRequest;
import com.example.Naumen_Project.DTO.RegistrationRequest;
import com.example.Naumen_Project.models.UserEntity;
import com.example.Naumen_Project.models.UserRole;
import com.example.Naumen_Project.security.AppUserDetailService;
import com.example.Naumen_Project.security.jwt.JwtService;
import com.example.Naumen_Project.repositories.UserRepository;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        if (userRepository.findByUsername(registrationDTO.username()).isEmpty()) {
            UserEntity user = new UserEntity();
            user.setName(registrationDTO.name());
            user.setEmail(registrationDTO.email());
            user.setPassword(passwordEncoder.encode(registrationDTO.password()));
            user.setUsername(registrationDTO.username());
            user.setUserRole(UserRole.AUTHORIZED_ROLE);
            user.setSurname(registrationDTO.surname());
            user.setSecondName(registrationDTO.secondName());
            userRepository.save(user);
            return user;
        } else
            return null;
    }

    public String login(LoginRequest loginDTO) throws AuthException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.username(),
                    loginDTO.password()));

            var userDetails = userDetailService.loadUserByUsername(loginDTO.username());
            return jwtUtil.generateToken(userDetails);
        } catch (Exception e) {
            throw new AuthException(e.getMessage());
        }
    }
}
