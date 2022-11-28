package com.mbadady.simpleBankApp.service;

import com.mbadady.simpleBankApp.dto.request.DepositFundsRequest;
import com.mbadady.simpleBankApp.dto.request.TransactionRequest;
import com.mbadady.simpleBankApp.dto.request.TransferFundsRequest;
import com.mbadady.simpleBankApp.dto.request.WithdrawFundsRequest;
import com.mbadady.simpleBankApp.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<TransactionRequest> findTransactionsByAccountNumber(String accountNumber);
    String transferFunds(TransferFundsRequest transferFundsRequest);
    String withdrawFunds(WithdrawFundsRequest withdrawFundsRequest);
    String depositFunds(DepositFundsRequest depositFundsRequest);
}
