package edu.ttu.retaileye.exceptions;

import edu.ttu.retaileye.records.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all exceptions that are not explicitly handled by other methods.
     *
     * @param ex     the exception that was thrown
     * @param request the HTTP request
     * @return a ResponseEntity containing the error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        var error = ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build();

        return ResponseEntity.status(status).body(error);
    }

    /**
     * Handles NotFoundException.
     *
     * @param ex     the NotFoundException that was thrown
     * @param request the HTTP request
     * @return a ResponseEntity containing the error details
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        var error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    /**
     * Handles InternalException.
     *
     * @param ex     the InternalException that was thrown
     * @param request the HTTP request
     * @return a ResponseEntity containing the error details
     */
    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ApiError> handleInternalException(InternalException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        var error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }
}