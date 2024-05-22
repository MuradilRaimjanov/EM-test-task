package com.example.emtesttask.dto;

import lombok.Data;

@Data
public class UserProfileUpdateRequest {
    private String email;
    private String phone;
}
