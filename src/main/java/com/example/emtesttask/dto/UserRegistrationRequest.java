package com.example.emtesttask.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private BigDecimal initialDeposit;
}
