package com.example.Naumen_Project;

import com.example.Naumen_Project.models.UserEntity;
import com.example.Naumen_Project.models.UserRole;
import com.example.Naumen_Project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class NaumenProjectApplication {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(NaumenProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()) {
                var user = new UserEntity();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("123456789"));
                user.setUserRole(UserRole.ADMIN_ROLE);
                userRepository.save(user);
            }
        };
    }

}
