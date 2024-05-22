package com.example.emtesttask.controller;

import com.example.emtesttask.dto.UserDTO;
import com.example.emtesttask.mapper.UserMapper;
import com.example.emtesttask.model.User;
import com.example.emtesttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDto) {

        User user = userMapper.toEntity(userDto);
        return new ResponseEntity<>(userMapper.toDto(userService.saveUser(user)), HttpStatus.OK);
    }

    @GetMapping("get-by-username/{username}")
    public ResponseEntity<UserDTO> getByUsername(@PathVariable String username) {
        UserDTO userDTO = userMapper.toDto(userService.findByUsername(username).orElseThrow());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }
}