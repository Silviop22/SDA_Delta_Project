package com.ecommerce.ecommerce.commons.fault.model;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception {
    final HttpStatus httpStatus;
    public CustomException(HttpStatus statusCode, String message) {
        super(message);
        this.httpStatus = statusCode;
    }
}