package com.chatop.chatopapi.mapper;


import com.chatop.chatopapi.dto.RegisterRequest;

import com.chatop.chatopapi.dto.UserDto;
import com.chatop.chatopapi.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User fromRegisterRequestToUser(RegisterRequest registerRequest) {
        if (registerRequest == null) {
            return null;
        }
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
        return user;
    }

    public UserDto fromUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        if (user != null) {
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setCreated_at(user.getCreatedAt());
            userDto.setUpdated_at(user.getUpdatedAt());

        }
        return userDto;
    }

}
