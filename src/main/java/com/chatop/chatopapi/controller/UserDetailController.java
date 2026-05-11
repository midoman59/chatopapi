package com.chatop.chatopapi.controller;

import com.chatop.chatopapi.dto.UserDto;
import com.chatop.chatopapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur pour les endpoints utilisateur
 * Retourne les infos d'un utilisateur spécifique par ID
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserService userService;

    /**
     * Retourne les infos d'un utilisateur par son ID
     * Nécessite le token JWT en Authorization header
     *
     * @param id l'ID de l'utilisateur
     * @return UserDto avec id, name, email, created_at, updated_at
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }
}

