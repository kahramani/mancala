package com.kahramani.mancala.domain.exception;

public class MancalaBusinessValidationException extends RuntimeException {

    private final Object[] arguments;

    public MancalaBusinessValidationException(String message, Object... arguments) {
        super(message);
        this.arguments = arguments;
    }

    public Object[] getArguments() {
        return arguments;
    }
}
