package com.chatop.chatopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageRequest {
    @JsonProperty("user_id")
    private Integer user_id;

    @JsonProperty("rental_id")
    private Integer rental_id;

    private String message;
}
