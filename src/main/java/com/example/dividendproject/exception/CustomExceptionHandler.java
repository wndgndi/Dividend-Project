package com.example.dividendproject.exception;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(AbstractException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .code(e.getStatusCode())
            .message(e.getMessage())
            .build();

        return new ResponseEntity<>(errorResponse,
            Objects.requireNonNull(HttpStatus.resolve(e.getStatusCode())));
    }
}
