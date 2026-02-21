package com.sagarboyal.job_platform_api.exception.custom;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class APIExceptions extends RuntimeException {

    private final String errorCode;
    private final HttpStatus status;

    public APIExceptions(String message) {
        super(message);
        this.errorCode = "API_ERROR";
        this.status = HttpStatus.BAD_REQUEST;
    }

    public APIExceptions(String message, HttpStatus status) {
        super(message);
        this.errorCode = "API_ERROR";
        this.status = status;
    }

    public APIExceptions(String message, String errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public APIExceptions(String message, String errorCode, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.status = status;
    }
}
