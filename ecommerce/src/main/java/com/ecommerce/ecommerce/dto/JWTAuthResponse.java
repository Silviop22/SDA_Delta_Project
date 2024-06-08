package com.ecommerce.ecommerce.dto;

import lombok.Data;

@Data
public class JWTAuthResponse {
    private String token;

    private UserDTO user;
}
