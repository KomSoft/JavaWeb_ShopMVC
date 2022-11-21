package com.komsoft.shopmvc.exception;

public class ValidationException extends Exception {
//    private String message;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {
    }

}
