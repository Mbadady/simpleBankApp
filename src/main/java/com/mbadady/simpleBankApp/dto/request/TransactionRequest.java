package com.mbadady.simpleBankApp.dto.request;

import com.mbadady.simpleBankApp.enums.TransactionType;
import com.mbadady.simpleBankApp.model.Account;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private String accountNumber;
    private BigDecimal transactionAmount;

    @ApiModelProperty(notes = "Types: debit,DEBIT,CREDIT,credit")
    private TransactionType transactionType;
    private Timestamp dateCreated;
    private String narration;
    private Account account;
}
