package com.mbadady.simpleBankApp.dto.request;

import com.mbadady.simpleBankApp.enums.AccountStatusConstant;
import com.mbadady.simpleBankApp.enums.AccountType;
import com.mbadady.simpleBankApp.model.Transaction;
import com.mbadady.simpleBankApp.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AccountRequest {

    private String accountNumber;

    private BigDecimal AccountBalance;

    private BigDecimal dailyTransactionLimit;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private  Timestamp dateUpdated;

    @ApiModelProperty(notes = "Types: current,CURRENT,savings,SAVINGS")
    private AccountType accountType;

    private String firstName;

    private String lastName;

    private String email;

    private List<Transaction> transactions;
}
