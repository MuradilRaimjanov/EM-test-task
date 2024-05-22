package com.example.emtesttask.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}