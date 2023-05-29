package com.example.Naumen_Project.configs;

import com.example.Naumen_Project.models.UserEntity;
import com.example.Naumen_Project.models.UserRole;
import com.example.Naumen_Project.repositories.GenreRepository;
import com.example.Naumen_Project.repositories.TypeRepository;
import com.example.Naumen_Project.repositories.UserRepository;
import com.example.Naumen_Project.services.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
public class SeedDataConfig {

    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final AdminService adminService;
    private final TypeRepository typeRepository;
    private final PasswordEncoder passwordEncoder;

    public SeedDataConfig(UserRepository userRepository, GenreRepository genreRepository, AdminService adminService, TypeRepository typeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.adminService = adminService;
        this.typeRepository = typeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (typeRepository.count() == 0) {
                adminService.loadTypes();
            }
            if (genreRepository.count() == 0) {
                adminService.loadGenres();
            }
            if (userRepository.findByUsername("admin").isEmpty()) {
                var user = new UserEntity();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("123456789"));
                user.setUserRole(UserRole.ADMIN_ROLE);
                user.setRegistrationDate(new Date());
                userRepository.save(user);
            }
        };
    }
}
