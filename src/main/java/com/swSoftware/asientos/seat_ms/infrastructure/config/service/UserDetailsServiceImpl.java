package com.swSoftware.asientos.seat_ms.infrastructure.config.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String token){

        List<SimpleGrantedAuthority> roles = jwtService.getRoles(token);
        String username = jwtService.getSubject(token);

        return User.builder()
                .username(username)
                .password("")
                .roles(roles.toArray(new String[0]))
                .build();

    }
}