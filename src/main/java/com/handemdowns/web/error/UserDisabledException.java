package com.handemdowns.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "UserDisabled")
public class UserDisabledException extends RuntimeException {
    public UserDisabledException() {
        super("The requested user account is disabled");
    }

    public UserDisabledException(String message) {
        super(message);
    }
}