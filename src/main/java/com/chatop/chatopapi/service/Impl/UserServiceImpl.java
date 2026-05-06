package com.chatop.chatopapi.service.Impl;

import com.chatop.chatopapi.dto.LoginRequest;
import com.chatop.chatopapi.dto.RegisterRequest;
import com.chatop.chatopapi.entity.User;
import com.chatop.chatopapi.mapper.UserMapper;
import com.chatop.chatopapi.repository.UserRepository;
import com.chatop.chatopapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


private UserRepository userRepository;

private UserMapper userMapper;

private final PasswordEncoder passwordEncoder;



    /**
     * @param loginRequest
     * @return
     */
    @Override
    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new RuntimeException("User not found");

        } else if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return "token";
    }

    /**
     * @param registerRequest
     * @return
     */
    @Override
    public String register(RegisterRequest registerRequest) {
        User user = userMapper.fromRegisterRequestToUser(registerRequest);
        userRepository.save(user);
        return "token";
    }
}
