package com.mbadady.simpleBankApp.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;

    private String fieldName;

    private String fieldValue;

    private Long field;

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFoundException(String resourceName, String fieldName, Long field) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, field));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.field = field;
    }
}
