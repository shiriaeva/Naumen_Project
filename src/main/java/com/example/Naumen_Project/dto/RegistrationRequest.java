package com.example.Naumen_Project.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public final class RegistrationRequest {
    @Size(min = 3,message = "Минимум 3 символа")
    @NotBlank(message = "Поле обязательно для ввода")
    private String name;
    @Size(min = 3,message = "Минимум 3 символа")
    @NotBlank(message = "Поле обязательно для ввода")
    private String surname;
    private String secondName;
    @Size(min = 3,message = "Минимум 3 символа")
    @NotBlank(message = "Поле обязательно для ввода")
    private String username;
    @Size(min = 6,message = "Минимум 6 символа")
    @NotBlank(message = "Поле обязательно для ввода")
    private String password;
    @Email
    @NotBlank(message = "Поле обязательно для ввода")
    private String email;

    public RegistrationRequest(
            String name,
            String surname,
            String secondName,
            String username,
            String password,
            String email) {
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public RegistrationRequest(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RegistrationRequest) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.surname, that.surname) &&
                Objects.equals(this.secondName, that.secondName) &&
                Objects.equals(this.username, that.username) &&
                Objects.equals(this.password, that.password) &&
                Objects.equals(this.email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, secondName, username, password, email);
    }

    @Override
    public String toString() {
        return "RegistrationRequest[" +
                "name=" + name + ", " +
                "surname=" + surname + ", " +
                "secondName=" + secondName + ", " +
                "username=" + username + ", " +
                "password=" + password + ", " +
                "email=" + email + ']';
    }

}
