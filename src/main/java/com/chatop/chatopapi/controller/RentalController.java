package com.chatop.chatopapi.controller;

import com.chatop.chatopapi.dto.RentalRequest;
import com.chatop.chatopapi.dto.RentalResponse;
import com.chatop.chatopapi.dto.RentalsListResponse;
import com.chatop.chatopapi.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur de gestion des locations
 * Gère la liste, la création, la lecture et la modification des locations (rentals)
 */
@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Tag(name = "Rentals", description = "Endpoints pour la gestion des locations immobilières")
public class RentalController {
    private final RentalService rentalService;

    /**
     * Récupère la liste de toutes les locations disponibles
     *
     * @return liste des locations avec wrapper "rentals"
     */
    @GetMapping
    @Operation(
            summary = "Lister toutes les locations",
            description = "Retourne la liste de toutes les locations disponibles"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des locations récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = RentalsListResponse.class))),
            @ApiResponse(responseCode = "401", description = "Token manquant, invalide ou expiré"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<RentalsListResponse> listerRentals() {
        return ResponseEntity.ok(new RentalsListResponse(rentalService.listerRentals()));
    }

    /**
     * Récupère une location spécifique par son ID
     *
     * @param id l'ID de la location
     * @return détails complets de la location
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Récupérer une location par ID",
            description = "Retourne les détails complets d'une location spécifique"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = RentalResponse.class))),
            @ApiResponse(responseCode = "401", description = "Token manquant, invalide ou expiré"),
            @ApiResponse(responseCode = "404", description = "Location non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<RentalResponse> recupererRental(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.recupererRentalParId(id));
    }

    /**
     * Crée une nouvelle location avec image
     * Accepte multipart/form-data pour upload fichier
     *
     * @param request données de la location (name, surface, price, picture, description)
     * @return message de confirmation
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Créer une nouvelle location",
            description = "Crée une location avec upload d'image. L'utilisateur connecté devient propriétaire."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données manquantes ou invalides"),
            @ApiResponse(responseCode = "401", description = "Token manquant, invalide ou expiré"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<MessageReponseSimple> creerRental(@org.springframework.web.bind.annotation.RequestBody @org.springframework.web.bind.annotation.ModelAttribute RentalRequest request) {
        String message = rentalService.creerRental(request);
        return ResponseEntity.ok(new MessageReponseSimple(message));
    }

    /**
     * Met à jour une location existante
     * Accepte multipart/form-data pour upload d'une nouvelle image (optionnel)
     *
     * @param id l'ID de la location à modifier
     * @param request données mises à jour de la location
     * @return message de confirmation
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Modifier une location",
            description = "Met à jour les informations d'une location existante. Image optionnelle."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "401", description = "Token manquant, invalide ou expiré"),
            @ApiResponse(responseCode = "404", description = "Location non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    public ResponseEntity<MessageReponseSimple> mettreAJourRental(
            @PathVariable Integer id,
            @org.springframework.web.bind.annotation.ModelAttribute RentalRequest request
    ) {
        String message = rentalService.mettreAJourRental(id, request);
        return ResponseEntity.ok(new MessageReponseSimple(message));
    }

    /**
     * DTO local pour réponses simples avec message
     * Utilisé pour POST et PUT rentals
     */
    public record MessageReponseSimple(String message) {}
}
