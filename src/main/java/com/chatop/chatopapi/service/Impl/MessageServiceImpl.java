package com.chatop.chatopapi.service.Impl;

import com.chatop.chatopapi.dto.MessageRequest;
import com.chatop.chatopapi.entity.Message;
import com.chatop.chatopapi.entity.Rental;
import com.chatop.chatopapi.entity.User;
import com.chatop.chatopapi.exception.InvalidCredentialsException;
import com.chatop.chatopapi.exception.ResourceNotFoundException;
import com.chatop.chatopapi.mapper.MessageMapper;
import com.chatop.chatopapi.repository.MessageRepository;
import com.chatop.chatopapi.repository.RentalRepository;
import com.chatop.chatopapi.repository.UserRepository;
import com.chatop.chatopapi.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final MessageMapper messageMapper;
    /**
     * @param request
     * @return
     */
    @Override
    public String envoyerMessage(MessageRequest request) {

        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new InvalidCredentialsException("Utilisateur introuvable"));

        Rental rental = rentalRepository.findById(request.getRental_id())
                .orElseThrow(() -> new ResourceNotFoundException("Rental introuvable"));
        Message message = messageMapper.toEntity(request.getMessage(), user, rental);
        messageRepository.save(message);
        return "Message send with success";
    }
}
