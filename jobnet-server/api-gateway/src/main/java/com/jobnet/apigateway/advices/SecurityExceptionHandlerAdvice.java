package com.jobnet.apigateway.advices;

import com.jobnet.apigateway.dtos.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class SecurityExceptionHandlerAdvice {

    @ExceptionHandler({
        UsernameNotFoundException.class,
        BadCredentialsException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Mono<ErrorResponse> handleAuthenticationException(Exception e) {
        return Mono.just(
            ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build()
        );
    }

    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Mono<ErrorResponse> handleAccountStatusException(AccountStatusException e) {
        return Mono.just(
            ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Mono<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        return Mono.just(
            ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("No permission.")
                .build()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Mono<ErrorResponse> handleAccessDeniedException(AuthenticationException e) {
        return Mono.just(
            ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("No permission.")
                .build()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherException(Exception e) {
        return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message("Internal server error.")
            .build();
    }
}
