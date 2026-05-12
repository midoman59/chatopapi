package com.chatop.chatopapi.service;

import com.chatop.chatopapi.dto.RentalRequest;
import com.chatop.chatopapi.dto.RentalResponse;

import java.util.List;

public interface RentalService {
    List<RentalResponse> listerRentals();
    RentalResponse recupererRentalParId(Integer id);
    String creerRental(RentalRequest request);
    String mettreAJourRental(Integer id, RentalRequest request);
}
