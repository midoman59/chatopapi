package com.chatop.chatopapi.repository;

import com.chatop.chatopapi.entity.Message;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
