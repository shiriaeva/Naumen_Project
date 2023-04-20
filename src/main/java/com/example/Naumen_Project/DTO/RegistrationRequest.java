package com.example.Naumen_Project.DTO;


public record RegistrationRequest(
        String name,
        String surname,
        String secondName,
        String username,
        String password,
        String email) {
}
