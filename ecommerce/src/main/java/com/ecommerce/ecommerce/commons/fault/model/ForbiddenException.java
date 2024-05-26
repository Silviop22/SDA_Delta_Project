package com.ecommerce.ecommerce.commons.fault.model;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends CustomException {
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}

