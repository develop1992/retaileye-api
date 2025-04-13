package edu.ttu.retaileye.exceptions;

public class InternalException extends RuntimeException {

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
