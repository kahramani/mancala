package com.kahramani.mancala.application.validator;

import com.kahramani.mancala.domain.exception.RequestValidationException;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Thrown exceptions from classes which implement this interface will be handled on global exception handler
 * which is can be found here: {@link com.kahramani.mancala.infrastructure.interceptor.RestControllerExceptionHandler}
 * <p>
 * Throwing exception has considerable cost and handling all these validations with exceptions might seem unappropriate.
 * However it allows you to introduce new rules easily in the future.
 * <p>
 * Other business exceptions, which may need to reach data store in validation process, will be thrown on deeper layers.
 */
public interface RequestValidator<T> {

    void validate(T t) throws RequestValidationException;

    default void validateIfPresentOrThrow(Object o, Supplier<RequestValidationException> exceptionSupplier) {
        Optional.ofNullable(o)
                .orElseThrow(exceptionSupplier);
    }

    default void validateLengthOrThrow(String s, int size, Supplier<RequestValidationException> exceptionSupplier) {
        Optional.ofNullable(s)
                .filter(StringUtils::hasText)
                .filter(str -> str.length() == size)
                .orElseThrow(exceptionSupplier);
    }
}