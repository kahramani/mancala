package com.kahramani.mancala.infrastructure.interceptor;

import com.kahramani.mancala.application.model.response.ErrorResponse;
import com.kahramani.mancala.domain.exception.*;
import com.kahramani.mancala.domain.exception.notFound.AvailableGameNotFoundException;
import com.kahramani.mancala.domain.exception.notFound.BoardNotFoundException;
import com.kahramani.mancala.domain.exception.notFound.GameSessionNotFoundException;
import com.kahramani.mancala.domain.exception.notFound.PlayerNotFoundException;
import com.kahramani.mancala.infrastructure.localization.MessageLocalizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler to manage all rest exceptions on one hand.
 * <p>
 * Throwing exception has considerable cost and handling all these request/business validations with exceptions might seem unappropriate.
 * However it allows you to introduce new rules easily and to track all responses your api returns while your rule count increasing.
 */
@RestControllerAdvice
public class RestControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);
    private static final String ERROR_MESSAGE_SPLITTER = ";";

    private final MessageLocalizationService messageLocalizationService;

    public RestControllerExceptionHandler(MessageLocalizationService messageLocalizationService) {
        this.messageLocalizationService = messageLocalizationService;
    }

    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ErrorResponse> handleRequestValidationException(RequestValidationException e) {
        logger.warn("A request validation exception occurred.", e); // client-based error so log level can be warn
        ErrorResponse response = createErrorResponse(e.getMessage(), e.getArguments());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler(MancalaBusinessValidationException.class)
    public ResponseEntity<ErrorResponse> handleMancalaBusinessValidationException(MancalaBusinessValidationException e) {
        logger.warn("A mancala business validation exception occurred.", e); // client-based error so log level can be warn
        ErrorResponse response = createErrorResponse(e.getMessage(), e.getArguments());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler({AvailableGameNotFoundException.class, GameSessionNotFoundException.class, BoardNotFoundException.class, PlayerNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(Exception e) {
        logger.warn("An available game not found exception occurred.", e); // client-based error so log level can be warn
        ErrorResponse response = createErrorResponse("validation.business.game.not.found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.warn("A http request method not supported exception occurred", e);
        ErrorResponse response = createErrorResponse("validation.request.http.verb.not.supported");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.warn("A http message not readable exception occurred", e);
        ErrorResponse response = createErrorResponse("validation.request.http.message.not.readable");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({Exception.class, GameUrlCreationException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        logger.error("An error occurred.", e); // server error so log level must be error
        ErrorResponse response = createErrorResponse("system.error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private ErrorResponse createErrorResponse(String messageKey, Object... args) {
        String message = messageLocalizationService.getLocaleMessage(messageKey, args);
        String[] errorPair = message.split(ERROR_MESSAGE_SPLITTER);
        return new ErrorResponse(errorPair[0], errorPair[1]);
    }
}