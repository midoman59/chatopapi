package com.chatop.chatopapi.mapper;

import com.chatop.chatopapi.entity.Message;
import com.chatop.chatopapi.entity.Rental;
import com.chatop.chatopapi.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    public Message toEntity(String message, User user, Rental rental) {
        Message m = new Message();
        m.setMessage(message);
        m.setUser(user);
        m.setRental(rental);
        return m;
    }
}
