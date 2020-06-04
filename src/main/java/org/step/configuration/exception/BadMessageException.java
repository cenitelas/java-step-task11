package org.step.configuration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadMessageException extends RuntimeException {
    public BadMessageException(String message) {
        super(message);
    }
}
