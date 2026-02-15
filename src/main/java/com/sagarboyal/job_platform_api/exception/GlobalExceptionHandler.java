package com.sagarboyal.job_platform_api.exception;

import com.sagarboyal.job_platform_api.exception.custom.APIExceptions;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(APIExceptions.class)
    public ResponseEntity<ErrorResponse> handleException(APIExceptions ex, HttpServletRequest req){
        logger.error("API Exception: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(ex.getStatus() != null ? ex.getStatus() : HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message(ex.getMessage())
                        .errorCode(ex.getErrorCode() != null ? ex.getErrorCode() : "API_ERROR")
                        .status(ex.getStatus() != null ? ex.getStatus().value() : HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                        .path(req.getRequestURI())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnHandledException(Exception ex, HttpServletRequest req){
        logger.error("Unhandled Exception: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .message(ex.getMessage())
                        .errorCode("ERROR")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                        .path(req.getRequestURI())
                        .build());
    }
}
