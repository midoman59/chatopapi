package com.chatop.chatopapi.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private Integer userId;
    private Integer rentalId;
    private String message;
}
