package com.handemdowns.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "UserExists")
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("The requested user already exists");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}