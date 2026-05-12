package com.chatop.chatopapi.mapper;

import com.chatop.chatopapi.dto.RentalRequest;
import com.chatop.chatopapi.dto.RentalResponse;
import com.chatop.chatopapi.entity.Rental;
import com.chatop.chatopapi.service.GestionImageService;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {

    private final GestionImageService gestionImageService;

    public RentalMapper(GestionImageService gestionImageService) {
        this.gestionImageService = gestionImageService;
    }

    public RentalResponse toRentalResponse(Rental rental) {
        return new RentalResponse(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                gestionImageService.recupererUrlPhoto(rental.getPicture()),
                rental.getDescription(),
                rental.getOwner().getId(),
                rental.getCreated_at(),
                rental.getUpdated_at()
        );
    }

    public Rental toRental(RentalRequest request) {
        Rental rental = new Rental();
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        if (request.getPicture() != null && !request.getPicture().isEmpty()) {
            String cheminPhoto = gestionImageService.enregistrerPhoto(request.getPicture());
            rental.setPicture(cheminPhoto);
        }
        return rental;
    }
}
