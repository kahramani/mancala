package com.kahramani.mancala.application.model.response;

public class ErrorResponse implements Response {

    private String errorCode;
    private String errorMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{errorCode=%s, errorMessage=%s}",
                this.getClass().getSimpleName(),
                getErrorCode(),
                getErrorMessage());
    }
}