package com.chatop.chatopapi.service;

import com.chatop.chatopapi.dto.LoginRequest;
import com.chatop.chatopapi.dto.RegisterRequest;
import com.chatop.chatopapi.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    String login(LoginRequest loginRequest);
    String register(RegisterRequest registerRequest);
    UserDto getUserById();
}
