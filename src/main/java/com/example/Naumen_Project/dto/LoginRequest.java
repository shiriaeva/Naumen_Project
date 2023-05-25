package com.example.Naumen_Project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public final class LoginRequest {
    @Size(min = 3, message = "ASDSADASD")
    @NotBlank(message = "Поле обязательно для ввода")
    private String username;
    @Size(min = 6,message = "ASDASDDSA")
    @NotBlank(message = "Поле обязательно для ввода")
    private String password;

   public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest(){

    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (LoginRequest) obj;
        return Objects.equals(this.username, that.username) &&
                Objects.equals(this.password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "LoginRequest[" +
                "username=" + username + ", " +
                "password=" + password + ']';
    }

}

