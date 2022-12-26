package com.example.workout.global.exception.handler;

import com.example.workout.domain.email.exception.AuthCodeExpiredException;
import com.example.workout.domain.email.exception.ManyRequestEmailAuthException;
import com.example.workout.domain.email.exception.MisMatchAuthCodeException;
import com.example.workout.global.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {

    private void printError(HttpServletRequest request, RuntimeException e, String message){
        log.error(request.getRequestURI());
        log.error(message);
        e.printStackTrace();
    }

    @ExceptionHandler(AuthCodeExpiredException.class)
    public ResponseEntity<ErrorMessage> handleAuthCodeExpiredException(HttpServletRequest request, AuthCodeExpiredException e){
        printError(request, e, e.getErrorCode().getMessage());
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), e.getErrorCode().getStatus());
        return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(ManyRequestEmailAuthException.class)
    public ResponseEntity<ErrorMessage> handleManyRequestEmailAuthException(HttpServletRequest request, ManyRequestEmailAuthException e){
        printError(request, e, e.getErrorCode().getMessage());
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), e.getErrorCode().getStatus());
        return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(MisMatchAuthCodeException.class)
    public ResponseEntity<ErrorMessage> handleMisMatchAuthCodeException(HttpServletRequest request, MisMatchAuthCodeException e){
        printError(request, e, e.getErrorCode().getMessage());
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), e.getErrorCode().getStatus());
        return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }
}
