package com.example.emtesttask.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserSearchRequest {
    private String fullName;
    private String email;
    private String phone;
    private LocalDate birthDate;
}
