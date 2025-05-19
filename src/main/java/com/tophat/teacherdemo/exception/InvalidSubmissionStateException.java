package com.tophat.teacherdemo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class InvalidSubmissionStateException extends RuntimeException {
    private final String currentState;
    private HttpStatusCode statusCode = HttpStatus.LOCKED;

    public InvalidSubmissionStateException(String message, String currentState) {
        super(message);
        this.currentState = currentState;
    }

    public InvalidSubmissionStateException(String message, String currentState, HttpStatusCode statusCode) {
        super(message);
        this.currentState = currentState;
        this.statusCode = statusCode;
    }
}
