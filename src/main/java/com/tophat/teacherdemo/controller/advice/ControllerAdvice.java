package com.tophat.teacherdemo.controller.advice;

import com.tophat.teacherdemo.controller.vo.ErrorResponse;
import com.tophat.teacherdemo.exception.InvalidAnswerException;
import com.tophat.teacherdemo.exception.InvalidSubmissionStateException;
import com.tophat.teacherdemo.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(
                ErrorResponse.builder()
                        .message(Objects.requireNonNull(exception.getBindingResult().getFieldError())
                                .getDefaultMessage())
                        .code(exception.getStatusCode().toString())
                        .build()
        );
    }

    @ExceptionHandler(InvalidSubmissionStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSubmissionStateException(InvalidSubmissionStateException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(
                ErrorResponse.builder()
                        .message(exception.getMessage())
                        .code(exception.getStatusCode().toString())
                        .build()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .message(exception.getMessage())
                        .code(HttpStatus.NOT_FOUND.toString())
                        .build()
        );
    }

    @ExceptionHandler(InvalidAnswerException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAnswerException(InvalidAnswerException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .message(exception.getMessage())
                        .code(HttpStatus.BAD_REQUEST.toString())
                        .build()
        );
    }
}
