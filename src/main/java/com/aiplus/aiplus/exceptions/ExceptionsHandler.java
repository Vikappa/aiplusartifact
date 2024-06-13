package com.aiplus.aiplus.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.aiplus.aiplus.payloads.login.ErrorsResponseDTO;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ErrorsResponseDTO handleUnauthorizedException(UnauthorizedException e) {
        return new ErrorsResponseDTO(e.getMessage(), LocalDateTime.now(), 403);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorsResponseDTO> handleInvalidTokenException(InvalidTokenException e) {
        ErrorsResponseDTO errorResponse = new ErrorsResponseDTO(e.getMessage(), LocalDateTime.now(), e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorsResponseDTO> handleUserNotFoundException(UserNotFoundException e) {
        ErrorsResponseDTO errorResponse = new ErrorsResponseDTO(e.getMessage(), LocalDateTime.now(), 404);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
