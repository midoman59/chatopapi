package com.chatop.chatopapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    int id;
    String name;
    String email;
    LocalDate created_at;
    LocalDate updated_at;
}
