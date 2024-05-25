package org.yosua.binfood.controller;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.yosua.binfood.model.response.ApiResponse;

@RestControllerAdvice
public class ErrorController {

    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    private ResponseEntity<ApiResponse<String>> errorResponse(Exception exception, HttpStatusCode httpStatus) {
        log.error("{}: {}", exception.getClass().getSimpleName(), exception.getMessage());
        return ResponseEntity.status(httpStatus)
                .body(ApiResponse.<String>builder()
                        .data(null)
                        .errors(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<String>> constraintViolationException(ConstraintViolationException exception) {
        return errorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<String>> apiException(ResponseStatusException exception) {
        return errorResponse(exception, exception.getStatusCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> illegalArgumentException(IllegalArgumentException exception) {
        return errorResponse(exception, HttpStatus.BAD_REQUEST);
    }
}
