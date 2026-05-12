package com.chatop.chatopapi.controller;

import com.chatop.chatopapi.dto.UserDto;
import com.chatop.chatopapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur pour l'accès aux informations utilisateurs
 * Permet de récupérer les détails publics d'un utilisateur spécifique par son ID
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints pour la gestion des informations utilisateurs")
public class UserDetailController {

    private final UserService userService;

    /**
     * Récupère les informations publiques d'un utilisateur par son ID
     * Nécessite l'authentification via JWT
     *
     * @param id l'ID de l'utilisateur à récupérer
     * @return données utilisateur (id, name, email, created_at, updated_at)
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Récupérer les informations d'un utilisateur",
            description = "Retourne les informations publiques d'un utilisateur spécifique identifié par son ID"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur récupéré avec succès",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Token manquant, invalide ou expiré"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }
}

