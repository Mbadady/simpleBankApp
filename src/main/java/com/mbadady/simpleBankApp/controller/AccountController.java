package com.mbadady.simpleBankApp.controller;


import com.mbadady.simpleBankApp.dto.request.AccountOpeningRequest;
import com.mbadady.simpleBankApp.dto.request.AccountRequest;
import com.mbadady.simpleBankApp.dto.request.UserRequest;
import com.mbadady.simpleBankApp.model.User;
import com.mbadady.simpleBankApp.service.AccountService;
import com.mbadady.simpleBankApp.service.serviceImpl.EmailSenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Account Rest API")
@RestController
@RequestMapping("/api/v1")
public class AccountController {


    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    private EmailSenderService emailSenderService;

    @ApiOperation(value = "Create an account resource")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/accounts/{emailId}")
    public ResponseEntity<String> createAccount(@RequestBody AccountOpeningRequest accountOpeningRequest,
                                             @PathVariable String emailId){
        return new ResponseEntity<>(accountService.createNewAccount(accountOpeningRequest, emailId), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get all accounts by a user")
    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountRequest>> getAllAccountsByUser(@PathVariable Long userId){
        return new ResponseEntity<>(accountService.findAllAccountsByUserId(userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Get account by Account number")
    @GetMapping("/accounts/accountNumber/{acctNo}")
    public ResponseEntity<AccountRequest> getAccountsByAcctNumber(@PathVariable(value = "acctNo") String accountNumber){
        return new ResponseEntity<>(accountService.findAccountByAccountNUmber(accountNumber), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete an account by account number")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{userId}/accounts/{acctNo}")
    public ResponseEntity<String> deleteAccountByAccountNumber(@PathVariable Long userId,
                                                               @PathVariable(value = "acctNo") String accountNumber){
        return new ResponseEntity<>(accountService.deleteAccountById(userId, accountNumber), HttpStatus.OK);
    }
}
