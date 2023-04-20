package com.example.Naumen_Project.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        var claims = new HashMap<String, Object>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }



    public boolean validateToken(UserDetails userDetails, String token) {
        String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public Boolean isTokenExpired(String token) {
        return getClaims(token)
                .getExpiration().before(new Date());
    }

}

