package com.chatop.chatopapi.controller;

import com.chatop.chatopapi.dto.AuthResponse;
import com.chatop.chatopapi.dto.LoginRequest;
import com.chatop.chatopapi.dto.RegisterRequest;
import com.chatop.chatopapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
//generation du constructor par lombok
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        String token = userService.register(registerRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public  ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
