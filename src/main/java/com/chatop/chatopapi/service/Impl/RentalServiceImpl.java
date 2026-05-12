package com.chatop.chatopapi.service.Impl;

import com.chatop.chatopapi.dto.RentalRequest;
import com.chatop.chatopapi.dto.RentalResponse;
import com.chatop.chatopapi.entity.Rental;
import com.chatop.chatopapi.entity.User;
import com.chatop.chatopapi.exception.InvalidCredentialsException;
import com.chatop.chatopapi.exception.ResourceNotFoundException;
import com.chatop.chatopapi.mapper.RentalMapper;
import com.chatop.chatopapi.repository.RentalRepository;
import com.chatop.chatopapi.repository.UserRepository;
import com.chatop.chatopapi.service.GestionImageService;
import com.chatop.chatopapi.service.RentalService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final GestionImageService gestionImageService;
    private final RentalMapper rentalMapper;

    public RentalServiceImpl(RentalRepository rentalRepository, UserRepository userRepository, GestionImageService gestionImageService, RentalMapper rentalMapper) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.gestionImageService = gestionImageService;
        this.rentalMapper = rentalMapper;
    }

    /**
     * @return
     */
    @Override
    public List<RentalResponse> listerRentals() {
        return rentalRepository.findAll().stream().map(rentalMapper::toRentalResponse).toList();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public RentalResponse recupererRentalParId(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental introuvable"));
        return rentalMapper.toRentalResponse(rental);
    }

    /**
     * @param request
     * @return
     */
    @Override
    public String creerRental(RentalRequest request) {
        User utilisateurConnecte = recupererUtilisateurConnecte();

         Rental rental = rentalMapper.toRental(request);
         rental.setOwner(utilisateurConnecte);

         rentalRepository.save(rental);
        return "Rental created !";
    }

    /**
     * @param id
     * @param request
     * @return
     */
    @Override
    public String mettreAJourRental(Integer id, RentalRequest request) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental introuvable"));

        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());

        if (request.getPicture() != null && !request.getPicture().isEmpty()) {
            String cheminPhoto = gestionImageService.enregistrerPhoto(request.getPicture());
            rental.setPicture(cheminPhoto);
        }

        rentalRepository.save(rental);
        return "Rental updated !";
    }
    private User recupererUtilisateurConnecte() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidCredentialsException("Pas d'authentification valide trouvée");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof Integer userId)) {
            throw new InvalidCredentialsException("Identifiant utilisateur invalide");
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new InvalidCredentialsException("Utilisateur introuvable"));
    }
}
