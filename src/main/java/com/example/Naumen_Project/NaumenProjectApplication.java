package com.example.Naumen_Project;

import com.example.Naumen_Project.models.UserEntity;
import com.example.Naumen_Project.models.UserRole;
import com.example.Naumen_Project.repositories.GenreRepository;
import com.example.Naumen_Project.repositories.TypeRepository;
import com.example.Naumen_Project.repositories.UserRepository;
import com.example.Naumen_Project.services.AdminService;
import com.example.Naumen_Project.services.GenreService;
import com.example.Naumen_Project.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class NaumenProjectApplication {


    public static void main(String[] args) {
        SpringApplication.run(NaumenProjectApplication.class, args);
    }
}
