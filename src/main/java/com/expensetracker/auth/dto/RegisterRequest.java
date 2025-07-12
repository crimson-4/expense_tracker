package com.expensetracker.auth.dto;


import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String currencyPreference;
}
