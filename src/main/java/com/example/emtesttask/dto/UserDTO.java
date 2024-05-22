package com.example.emtesttask.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
@Data
public class UserDTO {


    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate birthDate;

}
