package com.mbadady.simpleBankApp.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DepositFundsRequest {
//    private Long userId;
    private String toAccount;
    private BigDecimal transactionAmount;
    private String narration;
}
