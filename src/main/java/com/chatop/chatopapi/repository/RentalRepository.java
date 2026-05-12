package com.chatop.chatopapi.repository;

import com.chatop.chatopapi.entity.Rental;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
public interface RentalRepository extends JpaRepository<Rental, Integer> {

}
