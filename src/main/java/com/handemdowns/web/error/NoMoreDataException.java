package com.handemdowns.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "NoMoreData")
public class NoMoreDataException extends RuntimeException {
    public NoMoreDataException() {
        super("There is no more data from the requested resource");
    }

    public NoMoreDataException(String message) {
        super(message);
    }
}