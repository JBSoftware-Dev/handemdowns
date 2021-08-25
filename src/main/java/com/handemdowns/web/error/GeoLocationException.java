package com.handemdowns.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "GeoLocationError")
public class GeoLocationException extends RuntimeException {
    public GeoLocationException() {
        super("GeoLocationError");
    }

    public GeoLocationException(String message) {
        super(message);
    }
}