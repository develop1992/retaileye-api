package edu.ttu.retaileye.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles NotFoundException and returns a 404 Not Found response.
     *
     * @param ex the NotFoundException
     * @return a ResponseEntity with a 404 status and the exception message
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        try {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    /**
     * Handles InternalException and returns a 500 Internal Server Error response.
     *
     * @param ex the InternalException
     * @return a ResponseEntity with a 500 status and the exception message
     */
    @ExceptionHandler(InternalException.class)
    public ResponseEntity<String> handleInternalException(InternalException ex) {
        try {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    /**
     * Handles generic exceptions and returns a 500 Internal Server Error response.
     *
     * @param ex the Exception
     * @return a ResponseEntity with a 500 status and a generic error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        try {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
