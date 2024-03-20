package com.jobnet.common.advice;

import com.jobnet.common.dtos.ErrorResponse;
import com.jobnet.common.exceptions.DataIntegrityViolationException;
import com.jobnet.common.exceptions.ResourceNotFoundException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, List<String>> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.computeIfAbsent(field, k -> new ArrayList<>())
                .add(errorMessage);
        });
        return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .message(
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
            )
            .errors(errors)
            .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e) {
        return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.NOT_FOUND.value())
            .message(e.getMessage())
            .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.CONFLICT.value())
            .message(e.getMessage())
            .build();
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeignException(FeignException e) {
        return ResponseEntity.status(e.status())
            .contentType(MediaType.APPLICATION_JSON)
            .body(e.contentUTF8());
    }
}
