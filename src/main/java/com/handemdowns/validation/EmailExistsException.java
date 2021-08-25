package com.handemdowns.validation;

public class EmailExistsException extends Throwable {
    public EmailExistsException(String message) {
        super(message);
    }
}