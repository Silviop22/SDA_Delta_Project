package com.ecommerce.ecommerce.commons.fault.model;

import org.springframework.http.HttpStatus;

public class InternalErrorException extends CustomException {
    public InternalErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong during your request! Please try again later.");
    }
}

