package com.mbadady.simpleBankApp.dto.request;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    private String emailIdOrPhoneNumber;

    @NotEmpty
    @Size(min = 8, message = "Password must be 8 characters long")
    private String password;
}
