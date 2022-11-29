package com.mbadady.simpleBankApp.service;

import com.mbadady.simpleBankApp.dto.request.AccountOpeningRequest;
import com.mbadady.simpleBankApp.dto.request.AccountRequest;

import java.util.List;

public interface AccountService {
   String createNewAccount(AccountOpeningRequest accountOpeningRequest, String emailId);

   AccountRequest findAccountByAccountNUmber(String accountNumber);

    List<AccountRequest> findAllAccountsByUserId(Long userId);
    String deleteAccountById(Long userId, String accountNumber);

}
