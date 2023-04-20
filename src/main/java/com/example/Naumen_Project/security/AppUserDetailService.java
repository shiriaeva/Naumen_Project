package com.example.Naumen_Project.security;

import com.example.Naumen_Project.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       var user = userRepository.findByUsername(username);
       if(user.isPresent()){
           return new AppUserDetails(user.get());
       }
       throw new UsernameNotFoundException("Пользователь не найден");

    }
}
