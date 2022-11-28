package com.mbadady.simpleBankApp.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WithdrawFundsRequest {
    private String fromAccount;
    private BigDecimal transactionAmount;
}
