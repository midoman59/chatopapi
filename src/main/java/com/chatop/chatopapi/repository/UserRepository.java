package com.chatop.chatopapi.repository;

import com.chatop.chatopapi.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

     User findByEmail(String email);


}
