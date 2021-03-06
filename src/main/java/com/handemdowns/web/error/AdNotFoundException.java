package com.handemdowns.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "AdNotFound")
public class AdNotFoundException extends RuntimeException {
    public AdNotFoundException() {
        super("The requested ad could not be found");
    }

    public AdNotFoundException(String message) {
        super(message);
    }
}