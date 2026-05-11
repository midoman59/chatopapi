package com.chatop.chatopapi.controller;

import com.chatop.chatopapi.dto.AuthResponse;
import com.chatop.chatopapi.dto.LoginRequest;
import com.chatop.chatopapi.dto.RegisterRequest;
import com.chatop.chatopapi.dto.UserDto;

import com.chatop.chatopapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
//generation du constructor par lombok
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        String token = userService.register(registerRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public  ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        UserDto userDto = userService.getUserById();
        return ResponseEntity.ok(userDto);
    }
}
