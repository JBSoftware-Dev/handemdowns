package com.handemdowns.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "AdExpired")
public class AdExpiredException extends RuntimeException {
    public AdExpiredException() {
        super("The requested ad has expired");
    }

    public AdExpiredException(String message) {
        super(message);
    }
}