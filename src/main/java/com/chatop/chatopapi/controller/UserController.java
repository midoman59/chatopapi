package com.chatop.chatopapi.controller;

import com.chatop.chatopapi.dto.AuthResponse;
import com.chatop.chatopapi.dto.LoginRequest;
import com.chatop.chatopapi.dto.RegisterRequest;
import com.chatop.chatopapi.dto.UserDto;

import com.chatop.chatopapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur d'authentification
 * Gère l'enregistrement, la connexion et la récupération de l'utilisateur connecté
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints pour l'authentification des utilisateurs")
public class UserController {

    private final UserService userService;

    /**
     * Enregistre un nouvel utilisateur et retourne un token JWT
     *
     * @param registerRequest données d'enregistrement (name, email, password)
     * @return token JWT pour l'utilisateur créé
     */
    @PostMapping("/register")
    @Operation(
            summary = "Enregistrer un nouvel utilisateur",
            description = "Crée un compte utilisateur et retourne un token JWT"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur enregistré avec succès",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Email déjà utilisé ou données invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        String token = userService.register(registerRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Connecte un utilisateur avec ses identifiants et retourne un token JWT
     *
     * @param loginRequest données de connexion (email, password)
     * @return token JWT pour l'utilisateur
     */
    @PostMapping("/login")
    @Operation(
            summary = "Connecter un utilisateur",
            description = "Authentifie un utilisateur et retourne un token JWT"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Connexion réussie",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Identifiants invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Récupère les informations de l'utilisateur authentifié
     * Basé sur le token JWT fourni dans l'en-tête Authorization
     *
     * @return données utilisateur (id, name, email, created_at, updated_at)
     */
    @GetMapping("/me")
    @Operation(
            summary = "Récupérer l'utilisateur connecté",
            description = "Retourne les informations de l'utilisateur actuellement authentifié"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur récupéré avec succès",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Token manquant, invalide ou expiré"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<UserDto> getCurrentUser() {
        UserDto userDto = userService.getUserById();
        return ResponseEntity.ok(userDto);
    }
}
