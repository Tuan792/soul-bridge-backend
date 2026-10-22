package com.soulbridge.app.common.exception;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.soulbridge.app.common.response.ErrorDetail;
import com.soulbridge.app.common.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException exception
    ) {

        List<ErrorDetail> details =
                exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error ->
                                new ErrorDetail(
                                        toApiFieldName(error.getField()),
                                        error.getDefaultMessage()
                                )
                        )
                        .toList();

        return ResponseEntity
                .badRequest()
                .body(
                        ErrorResponse.failure(
                                "Request sai dữ liệu",
                                details
                        )
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson() {

        return ResponseEntity
                .badRequest()
                .body(
                        ErrorResponse.failure(
                                "Request sai dữ liệu",
                                List.of(
                                        new ErrorDetail(
                                                "body",
                                                "JSON không hợp lệ"
                                        )
                                )
                        )
                );
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            ResourceConflictException exception
    ) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.failure(
                                exception.getMessage(),
                                exception.getDetails()
                        )
                );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedException exception
    ) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.failure(
                                exception.getMessage(),
                                List.of()
                        )
                );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConcurrentConflict() {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.failure(
                                "Dữ liệu đã tồn tại",
                                List.of(
                                        new ErrorDetail(
                                                "username/email",
                                                "Username hoặc email đã tồn tại"
                                        )
                                )
                        )
                );
    }

    private String toApiFieldName(String field) {
        return "consentVersion".equals(field)
                ? "consent_version"
                : field;
    }
}