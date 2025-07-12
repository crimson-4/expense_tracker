package com.expensetracker.auth.controller;

import com.expensetracker.auth.dto.AuthResponse;
import com.expensetracker.auth.dto.LoginRequest;
import com.expensetracker.auth.dto.RegisterRequest;
import com.expensetracker.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        AuthResponse response= authService.register(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        AuthResponse response=authService.login(request);
        return ResponseEntity.ok(response);
    }

}
