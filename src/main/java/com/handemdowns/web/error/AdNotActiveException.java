package com.handemdowns.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "AdNotActive")
public class AdNotActiveException extends RuntimeException {
    public AdNotActiveException() {
        super("The requested ad in not active");
    }

    public AdNotActiveException(String message) {
        super(message);
    }
}