package com.example.emtesttask.controller;

import com.example.emtesttask.dto.AuthRequest;
import com.example.emtesttask.dto.AuthResponse;
import com.example.emtesttask.dto.UserDTO;
import com.example.emtesttask.dto.UserRegistrationRequest;
import com.example.emtesttask.model.Account;
import com.example.emtesttask.model.User;
import com.example.emtesttask.service.AccountService;
import com.example.emtesttask.service.UserService;
import com.example.emtesttask.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return new AuthResponse(jwt);
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        try {
            User user = new User();
            user.setUsername(userRegistrationRequest.getUsername());
            user.setPassword(userRegistrationRequest.getPassword());
            user.setFullName(userRegistrationRequest.getFullName());
            user.setEmail(userRegistrationRequest.getEmail());
            user.setPhone(userRegistrationRequest.getPhone());
            user.setBirthDate(userRegistrationRequest.getBirthDate());

            user = userService.saveUser(user);
            log.info("User with id {} saved", user.getId());

            Account account = new Account();
            account.setUser(user);
            account.setBalance(userRegistrationRequest.getInitialDeposit());
            account.setInitialDeposit(userRegistrationRequest.getInitialDeposit());
            accountService.saveAccount(account);
            return new ResponseEntity<>("Successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("An error has occurred when registering user");
            return new ResponseEntity<>("An error has occurred", HttpStatus.valueOf("500"));
        }
    }
}
