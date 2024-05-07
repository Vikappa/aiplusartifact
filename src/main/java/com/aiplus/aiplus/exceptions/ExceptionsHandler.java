package com.aiplus.aiplus.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.aiplus.aiplus.payloads.login.ErrorsResponseDTO;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ErrorsResponseDTO handleUnauthorizedException(UnauthorizedException e) {
        return new ErrorsResponseDTO(e.getMessage(), LocalDateTime.now());
    }
}
