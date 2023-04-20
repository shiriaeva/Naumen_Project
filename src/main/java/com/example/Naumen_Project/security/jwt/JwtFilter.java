package com.example.Naumen_Project.security.jwt;

import com.example.Naumen_Project.security.AppUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final AppUserDetailService userDetailService;
    private final JwtService jwtService;

    public JwtFilter(AppUserDetailService bookstoreUserDetailsService, JwtService jwtService) {
        this.userDetailService = bookstoreUserDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (request.getCookies() != null) {

            var tokenCookie = Arrays
                    .stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("token"))
                    .findFirst();

            if (tokenCookie.isPresent()) {
                var token = tokenCookie.get().getValue();
                var username = jwtService.getUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var userDetails = userDetailService.loadUserByUsername(username);
                    if (jwtService.validateToken(userDetails, token)) {
                        var authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
