package com.mbadady.simpleBankApp.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransferFundsRequest {

    private String fromAccount;
    private String toAccount;
    private BigDecimal transactionAmount;
    private String narration;
}
