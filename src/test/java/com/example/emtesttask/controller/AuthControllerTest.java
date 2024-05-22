package com.example.emtesttask.controller;


import com.example.emtesttask.dto.AuthRequest;
import com.example.emtesttask.dto.AuthResponse;
import com.example.emtesttask.dto.UserRegistrationRequest;
import com.example.emtesttask.model.Account;
import com.example.emtesttask.model.User;
import com.example.emtesttask.service.AccountService;
import com.example.emtesttask.service.UserService;
import com.example.emtesttask.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AccountService accountService;
    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;

    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAuthenticationToken_Successful() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("testpassword");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

        when(jwtTokenUtil.generateToken(userDetails)).thenReturn("testtoken");

        AuthResponse expectedResponse = new AuthResponse("testtoken");

        AuthResponse response = authController.createAuthenticationToken(request);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void testCreateAuthenticationToken_BadCredentials() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("testpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        assertThrows(Exception.class, () -> authController.createAuthenticationToken(request));
    }

    @Test
    public void testRegisterUser_SuccessfulRegistration() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        // Подготовка данных для запроса

        when(userService.saveUser(any())).thenReturn(new User());
        when(accountService.saveAccount(any())).thenReturn(new Account());

        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Successfully", HttpStatus.OK);

        ResponseEntity<String> response = authController.registerUser(request);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void testRegisterUser_FailedRegistration() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        // Подготовка данных для запроса

        when(userService.saveUser(any())).thenThrow(new RuntimeException());

        ResponseEntity<String> expectedResponse = new ResponseEntity<>("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        ResponseEntity<String> response = authController.registerUser(request);

        assertEquals(expectedResponse, response);
    }

}