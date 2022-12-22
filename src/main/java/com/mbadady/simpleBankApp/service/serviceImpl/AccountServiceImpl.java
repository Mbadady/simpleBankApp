package com.mbadady.simpleBankApp.service.serviceImpl;

import com.mbadady.simpleBankApp.customException.BankApiException;
import com.mbadady.simpleBankApp.customException.ResourceNotFoundException;
import com.mbadady.simpleBankApp.dao.AccountRepository;
import com.mbadady.simpleBankApp.dao.UserRepository;
import com.mbadady.simpleBankApp.dto.request.AccountOpeningRequest;
import com.mbadady.simpleBankApp.dto.request.AccountRequest;
import com.mbadady.simpleBankApp.enums.AccountType;
import com.mbadady.simpleBankApp.model.Account;
import com.mbadady.simpleBankApp.model.Transaction;
import com.mbadady.simpleBankApp.model.User;
import com.mbadady.simpleBankApp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private AccountRepository accountRepository;

    public AccountServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public String createNewAccount(AccountOpeningRequest accountOpeningRequest, String emailId) {

        User user = userRepository.findByEmailId(emailId).orElseThrow(() ->
                new ResourceNotFoundException("User", "email", emailId));

        BigDecimal limit = accountOpeningRequest.getDailyTransactionLimit();


        if (limit.compareTo(BigDecimal.ZERO) <= 0) {
            limit = BigDecimal.ZERO;
        }


        Account account = new Account();
        account.setAccountType(AccountType.valueOf(accountOpeningRequest.getAccountType().toUpperCase()));
        account.setDailyTransactionLimit(limit);
        account.setUser(user);
        account.setAccountNumber(generateAccountNumber());

        accountRepository.save(account);

        emailSenderService.emailSender(emailId, "Account Notification", "Account Successfully created: Your account number is " + account.getAccountNumber());


        return "Account Successfully created: Your account number is " + account.getAccountNumber();
    }

    @Override
    public AccountRequest findAccountByAccountNUmber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() ->
                new ResourceNotFoundException("Account", "account number", accountNumber));

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountBalance(account.getAccountBalance());
        accountRequest.setAccountType(account.getAccountType());
        accountRequest.setAccountNumber(account.getAccountNumber());
        accountRequest.setDailyTransactionLimit(account.getDailyTransactionLimit());
        accountRequest.setDateCreated(account.getDateCreated());
        accountRequest.setDateUpdated(account.getDateUpdated());
        accountRequest.setEmail(account.getUser().getEmailId());
        accountRequest.setFirstName(account.getUser().getFirstName());
        accountRequest.setLastName(account.getUser().getLastName());
        accountRequest.setTransactions(account.getTransactions());

        return accountRequest;
    }

    @Override
    public List<AccountRequest> findAllAccountsByUserId(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );


        List<Account> accounts = accountRepository.findByUserId(userId);
        if (accounts.isEmpty()) {
            throw new BankApiException(HttpStatus.BAD_REQUEST, "No Account for this user yet");
        }

        AccountRequest accountRequest = new AccountRequest();

        return accounts.stream().map(account -> {
            accountRequest.setAccountType(account.getAccountType());
            accountRequest.setAccountNumber(account.getAccountNumber());
            accountRequest.setAccountBalance(account.getAccountBalance());
            accountRequest.setEmail(account.getUser().getEmailId());
            accountRequest.setDateCreated(account.getDateCreated());
            accountRequest.setDailyTransactionLimit(account.getDailyTransactionLimit());
            accountRequest.setFirstName(account.getUser().getFirstName());
            accountRequest.setLastName(account.getUser().getLastName());
            accountRequest.setTransactions(account.getTransactions());

            return accountRequest;
        }).collect(Collectors.toList());
    }


    @Override
    public String deleteAccountById(Long userId, String accountNumber) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );

        Account accountToDelete = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() ->
                new ResourceNotFoundException("Account", "account number", accountNumber));

        accountRepository.delete(accountToDelete);

        return "Account deleted successfully";
    }

    private String generateAccountNumber() {

//        converting the string of account number to int, to add 1 to it to make it sequential
        int lastAccountNumberToInt = Integer.parseInt(accountRepository.findTopByOrderByIdDesc().getAccountNumber()) + 1;

        return String.format("%010d", lastAccountNumberToInt);
    }
}
