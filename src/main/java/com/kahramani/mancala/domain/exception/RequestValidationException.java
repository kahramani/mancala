package com.kahramani.mancala.domain.exception;

public class RequestValidationException extends RuntimeException {

    private final Object[] arguments;

    public RequestValidationException(String message, Object... arguments) {
        super(message);
        this.arguments = arguments;
    }

    public Object[] getArguments() {
        return arguments;
    }
}