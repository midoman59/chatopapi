package com.chatop.chatopapi.service;

import com.chatop.chatopapi.dto.MessageRequest;

public interface MessageService {
    String envoyerMessage(MessageRequest request);
}
