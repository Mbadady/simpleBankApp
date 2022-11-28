package com.mbadady.simpleBankApp.controller;


import com.mbadady.simpleBankApp.dto.request.DepositFundsRequest;
import com.mbadady.simpleBankApp.dto.request.TransactionRequest;
import com.mbadady.simpleBankApp.dto.request.TransferFundsRequest;
import com.mbadady.simpleBankApp.dto.request.WithdrawFundsRequest;
import com.mbadady.simpleBankApp.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Transaction Rest Api")
@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "Get  all the transactions associated with an account")
    @GetMapping("/transactions/{acctNo}")
    public ResponseEntity<List<TransactionRequest>> getTransactionsByAccountNumber(@PathVariable(value = "acctNo")
                                                                                 String accountNumber){
        return new ResponseEntity<>(transactionService.findTransactionsByAccountNumber(accountNumber), HttpStatus.OK);
    }

    @ApiOperation(value = "Transfer money resource")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/transactions/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransferFundsRequest transferFundsRequest){
        return new ResponseEntity<>(transactionService.transferFunds(transferFundsRequest), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Deposit money resource")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/transactions/deposit")
    public ResponseEntity<String> depositMoney(@RequestBody DepositFundsRequest depositFundsRequest){
        return new ResponseEntity<>(transactionService.depositFunds(depositFundsRequest), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Withdraw money resource")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/transactions/withdraw")
    public ResponseEntity<String> withdrawMoney(@RequestBody WithdrawFundsRequest withdrawFundsRequest){
        return new ResponseEntity<>(transactionService.withdrawFunds(withdrawFundsRequest), HttpStatus.CREATED);
    }
}
