package com.nab.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
public class AuthoException extends RuntimeException{

    public AuthoException(String message) {
        super(message);
    }

    public AuthoException(String message, Throwable cause) {
        super(message, cause);
    }
}
