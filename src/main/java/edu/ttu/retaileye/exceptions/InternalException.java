package edu.ttu.retaileye.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InternalException extends RuntimeException {
    public InternalException(String message) {
        super(message);
        log.error(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}
