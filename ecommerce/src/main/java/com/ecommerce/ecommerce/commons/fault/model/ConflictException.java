package com.ecommerce.ecommerce.commons.fault.model;

import org.springframework.http.HttpStatus;

public class ConflictException extends CustomException {
    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
