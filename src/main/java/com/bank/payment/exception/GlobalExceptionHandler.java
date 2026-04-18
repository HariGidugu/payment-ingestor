package com.bank.payment.exception;

import com.bank.payment.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex,
            HttpServletRequest request) {
        List<ErrorResponse.Violation> violations = List.of(ErrorResponse.Violation.builder()
                .message(ex.getMessage())
                .build());
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Validation failed", request, violations);
    }

    @ExceptionHandler(AccountInactiveException.class)
    public ResponseEntity<ErrorResponse> handleAccountInactive(AccountInactiveException ex,
            HttpServletRequest request) {
        List<ErrorResponse.Violation> violations = List.of(ErrorResponse.Violation.builder()
                .message(ex.getMessage())
                .build());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", request, violations);
    }

    @ExceptionHandler(AccountSuspendedException.class)
    public ResponseEntity<ErrorResponse> handleAccountSuspended(AccountSuspendedException ex,
            HttpServletRequest request) {
        List<ErrorResponse.Violation> violations = List.of(ErrorResponse.Violation.builder()
                .message(ex.getMessage())
                .build());
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed", request, violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        List<ErrorResponse.Violation> violations = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ErrorResponse.Violation.builder()
                        .field(((FieldError) error).getField())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", request, violations);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        List<ErrorResponse.Violation> violations = List.of(ErrorResponse.Violation.builder()
                .message(ex.getMessage())
                .build());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + ex.getMessage(),
                request, violations);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message,
            HttpServletRequest request, List<ErrorResponse.Violation> violations) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(message)
                .path(request.getRequestURI())
                .violations(violations)
                .build();
        return ResponseEntity.status(status).body(errorResponse);
    }
}
