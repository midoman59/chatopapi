package com.chatop.chatopapi.controller;

import com.chatop.chatopapi.dto.MessageRequest;
import com.chatop.chatopapi.dto.MessageResponse;
import com.chatop.chatopapi.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> envoyerMessage(@RequestBody MessageRequest request) {
        String message = messageService.envoyerMessage(request);
        return ResponseEntity.ok(new MessageResponse(message));
    }
}
