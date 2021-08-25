package com.handemdowns.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "InvalidToken")
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Token is invalid or does not exist on the server");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}