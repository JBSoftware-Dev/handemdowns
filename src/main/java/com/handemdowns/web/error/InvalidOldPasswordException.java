package com.handemdowns.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "InvalidOldPassword")
public class InvalidOldPasswordException extends RuntimeException {
    public InvalidOldPasswordException() {
        super("Old password is incorrect or invalid");
    }

    public InvalidOldPasswordException(String message) {
        super(message);
    }
}