package com.chatop.chatopapi.controller;

import com.chatop.chatopapi.dto.MessageRequest;
import com.chatop.chatopapi.dto.MessageResponse;
import com.chatop.chatopapi.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur de gestion des messages
 * Gère l'envoi de messages entre utilisateurs concernant les locations
 */
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Messages", description = "Endpoints pour la gestion des messages entre utilisateurs")
public class MessageController {
    private final MessageService messageService;

    /**
     * Envoie un message d'un utilisateur à un autre concernant une location
     * L'utilisateur authenticateur envoie le message au propriétaire de la location
     *
     * @param request données du message (user_id, rental_id, message)
     * @return message de confirmation
     */
    @PostMapping
    @Operation(
            summary = "Envoyer un message",
            description = "Envoie un message d'un utilisateur concernant une location spécifique"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Message envoyé avec succès",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Données manquantes ou invalides"),
            @ApiResponse(responseCode = "401", description = "Token manquant, invalide ou expiré"),
            @ApiResponse(responseCode = "404", description = "Utilisateur ou location non trouvé(e)"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<MessageResponse> envoyerMessage(@RequestBody MessageRequest request) {
        String message = messageService.envoyerMessage(request);
        return ResponseEntity.ok(new MessageResponse(message));
    }
}
