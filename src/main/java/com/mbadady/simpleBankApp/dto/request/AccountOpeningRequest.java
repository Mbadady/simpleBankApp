package com.mbadady.simpleBankApp.dto.request;

import com.mbadady.simpleBankApp.enums.AccountType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountOpeningRequest {

    @NotBlank(message = "Account Type is Required")
    @ApiModelProperty(notes = "Types: current,CURRENT,savings,SAVINGS")
    private String accountType;
    private BigDecimal dailyTransactionLimit;
}
