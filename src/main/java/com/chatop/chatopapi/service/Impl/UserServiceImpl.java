package com.chatop.chatopapi.service.Impl;

import com.chatop.chatopapi.dto.LoginRequest;
import com.chatop.chatopapi.dto.RegisterRequest;
import com.chatop.chatopapi.dto.UserDto;
import com.chatop.chatopapi.entity.User;
import com.chatop.chatopapi.exception.EmailAlreadyUsedException;
import com.chatop.chatopapi.exception.InvalidCredentialsException;
import com.chatop.chatopapi.mapper.UserMapper;
import com.chatop.chatopapi.repository.UserRepository;
import com.chatop.chatopapi.security.JwtService;
import com.chatop.chatopapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


private final UserRepository userRepository;

private final UserMapper userMapper;

private final PasswordEncoder passwordEncoder;

private final JwtService jwtService;



    /**
     * @param loginRequest
     * @return
     */
    @Override
    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new InvalidCredentialsException("User not found");

        } if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        return jwtService.generateToken(user.getId());
    }

    /**
     * @param registerRequest
     * @return
     */
    @Override
    public String register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new EmailAlreadyUsedException("Email already used");
        }
        User user = userMapper.fromRegisterRequestToUser(registerRequest);
        userRepository.save(user);
        return jwtService.generateToken(user.getId());
    }

    /**
     * @param
     * @return
     */
    @Override
    public UserDto getUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidCredentialsException("Pas d'authentification valide trouvée");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof Integer userId)) {
            throw new InvalidCredentialsException("Identifiant utilisateur invalide");
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new InvalidCredentialsException("Utilisateur introuvable");
        }
        return userMapper.fromUserToUserDto(user.get());
    }


}
