package com.expensetracker.auth.service;

import com.expensetracker.auth.JwtProperties;
import  com.expensetracker.auth.dto.AuthResponse;
import  com.expensetracker.auth.dto.LoginRequest;
import  com.expensetracker.auth.dto.RegisterRequest;
import com.expensetracker.auth.util.JwtUtils;
import com.expensetracker.model.User;
import  com.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthResponse register(RegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw  new RuntimeException("Email already registered");
        }

        User user=User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .currencyPreference(request.getCurrencyPreference())
                .build();
        userRepository.save(user);
        String token =jwtUtils.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    public  AuthResponse login(LoginRequest request){
        User user= userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException("Invalid Email or password"));
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword()))
        {
            throw  new RuntimeException("Invalid Email or password");
        }
        String token =jwtUtils.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}
