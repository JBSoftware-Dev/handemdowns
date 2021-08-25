package com.handemdowns.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "UserNotFound")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("The requested user could not be found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}