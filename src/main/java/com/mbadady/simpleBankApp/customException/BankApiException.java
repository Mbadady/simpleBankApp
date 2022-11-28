package com.mbadady.simpleBankApp.customException;

import org.springframework.http.HttpStatus;

public class BankApiException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public BankApiException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
