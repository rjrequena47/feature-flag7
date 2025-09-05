package com.bytes7.feature_flag7.exception;

import com.bytes7.feature_flag7.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==============================
    // 400 Bad Request
    // ==============================
    @ExceptionHandler({
        DataIntegrityViolationException.class,
        ConstraintViolationException.class,
        MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex, HttpServletRequest request) {
        String message = "Solicitud inválida";

        if (ex instanceof DataIntegrityViolationException) {
            message = "Nombre de feature ya existe o violación de integridad";
        } else if (ex instanceof ConstraintViolationException cve) {
            message = cve.getMessage();
        } else if (ex instanceof MethodArgumentNotValidException manve) {
            message = manve.getBindingResult().getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, Instant.now(), request.getRequestURI()));
    }

    // ==============================
    // 401 Unauthorized
    // ==============================
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(AuthenticationException ex, HttpServletRequest request) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                    "No autenticado: " + ex.getMessage(),
                    Instant.now(),
                    request.getRequestURI()));
    }

    // ==============================
    // 403 Forbidden
    // ==============================
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(AccessDeniedException ex, HttpServletRequest request) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                    "Acceso denegado: " + ex.getMessage(),
                    Instant.now(),
                    request.getRequestURI()));
    }

    // ==============================
    // 404 / 405 Not Found & Method Not Allowed
    // ==============================
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex,
                                                                HttpServletRequest request) {
        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(),
                    ex.getMessage(),
                    Instant.now(),
                    request.getRequestURI()));
    }

    // ==============================
    // Manejo genérico para ResponseStatusException
    // ==============================
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex,
                                                                       HttpServletRequest request) {
        HttpStatusCode status = ex.getStatusCode();
        String message = ex.getReason() != null ? ex.getReason() : "Error de aplicación";

        return ResponseEntity
            .status(status)
            .body(new ErrorResponse(status.value(), message, Instant.now(), request.getRequestURI()));
    }

    // ==============================
    // 500 Internal Server Error
    // ==============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex, HttpServletRequest request) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error interno del servidor: " + ex.getMessage(),
                    Instant.now(),
                    request.getRequestURI()));
    }
}
