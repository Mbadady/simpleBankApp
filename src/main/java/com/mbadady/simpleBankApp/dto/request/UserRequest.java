package com.mbadady.simpleBankApp.dto.request;

import com.mbadady.simpleBankApp.enums.GenderConstant;
import com.mbadady.simpleBankApp.model.Account;
import com.mbadady.simpleBankApp.model.AddressDetails;
import com.mbadady.simpleBankApp.model.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @ApiModelProperty(notes = "first name", required = true)
    @NotBlank(message = "Please Enter your first name")
    private String firstName;

    private String middleName;

    @ApiModelProperty(notes = "Last name", required = true)
    @NotBlank(message = "Please Enter your last name")
    private String lastName;

    @NotEmpty
    @Size(min = 8, message = "Password must be 8 characters long")
    private String password;

    @Email
    @NotEmpty(message = "Email cannot be empty")
    private String emailId;

    @ApiModelProperty(notes = "phone number", required = true)
    @NotBlank(message = "Please provide a phone number")
    private String phoneNumber;

    @ApiModelProperty(notes = "Format: dd/MM/yyyy", required = true)
    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
//    @NotBlank(message = "Please Enter your date of birth")
    private LocalDate dateOfBirth;

    @ApiModelProperty(notes = "Types: MALE,male,FEMALE,female", required = true)
    @Pattern(regexp = "(MALE|male|FEMALE|female)")
    private String gender;

    private AddressDetails address;
    private Set<Role> roles;
    private Set<Account> accounts;
//    private Set<String> strRoles;

}
