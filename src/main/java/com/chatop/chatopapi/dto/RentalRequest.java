package com.chatop.chatopapi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class RentalRequest {
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private MultipartFile picture;
    private String description;
}
