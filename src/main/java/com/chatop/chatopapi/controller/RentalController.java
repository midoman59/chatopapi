package com.chatop.chatopapi.controller;

import com.chatop.chatopapi.dto.RentalRequest;
import com.chatop.chatopapi.dto.RentalResponse;
import com.chatop.chatopapi.dto.RentalsListResponse;
import com.chatop.chatopapi.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    // GET /api/rentals
    @GetMapping
    public ResponseEntity<RentalsListResponse> listerRentals() {
        return ResponseEntity.ok(new RentalsListResponse(rentalService.listerRentals()));
    }

    // GET /api/rentals/:id
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> recupererRental(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.recupererRentalParId(id));
    }

    // POST /api/rentals (multipart/form-data)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageReponseSimple> creerRental(@ModelAttribute RentalRequest request) {
        String message = rentalService.creerRental(request);
        return ResponseEntity.ok(new MessageReponseSimple(message));
    }

    // PUT /api/rentals/:id (multipart/form-data)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageReponseSimple> mettreAJourRental(
            @PathVariable Integer id,
            @ModelAttribute RentalRequest request
    ) {
        String message = rentalService.mettreAJourRental(id, request);
        return ResponseEntity.ok(new MessageReponseSimple(message));
    }

    // DTO local simple pour coller au mock {"message":"..."}
    public record MessageReponseSimple(String message) {}
}
