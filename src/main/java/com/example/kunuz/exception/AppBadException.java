package com.example.kunuz.exception;

public class AppBadException extends RuntimeException{

    public AppBadException() {
    }

    public AppBadException(String message) {
        super(message);
    }
}
