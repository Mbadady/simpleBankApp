package com.mbadady.simpleBankApp.service.serviceImpl;

import com.mbadady.simpleBankApp.customException.BankApiException;
import com.mbadady.simpleBankApp.customException.ResourceNotFoundException;
import com.mbadady.simpleBankApp.dao.AccountRepository;
import com.mbadady.simpleBankApp.dao.TransactionRepository;
import com.mbadady.simpleBankApp.dao.UserRepository;
import com.mbadady.simpleBankApp.dto.request.DepositFundsRequest;
import com.mbadady.simpleBankApp.dto.request.TransactionRequest;
import com.mbadady.simpleBankApp.dto.request.TransferFundsRequest;
import com.mbadady.simpleBankApp.dto.request.WithdrawFundsRequest;
import com.mbadady.simpleBankApp.enums.TransactionType;
import com.mbadady.simpleBankApp.model.Account;
import com.mbadady.simpleBankApp.model.Transaction;
import com.mbadady.simpleBankApp.model.User;
import com.mbadady.simpleBankApp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;


    @Override
    public List<TransactionRequest> findTransactionsByAccountNumber(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->
                new ResourceNotFoundException("Account", "account number", accountNumber));

        List<Transaction> transactions = transactionRepository.findByAccountNumber(accountNumber);

        TransactionRequest transactionRequest = new TransactionRequest();

        if(transactions.isEmpty()) {
            throw new BankApiException(HttpStatus.BAD_REQUEST, "No transaction for this account yet");
        }
       return transactions.stream().map(transaction -> {
            transactionRequest.setTransactionAmount(transaction.getTransactionAmount());
            transactionRequest.setAccountNumber(transaction.getAccountNumber());
            transactionRequest.setNarration(transaction.getNarration());
            transactionRequest.setTransactionType(transaction.getTransactionType());
            transactionRequest.setDateCreated(transaction.getDateCreated());

            return transactionRequest;
        }).collect(Collectors.toList());

    }


    @Override
    public String transferFunds(TransferFundsRequest transferFundsRequest) {
        String fromAccountNumber = transferFundsRequest.getFromAccount();
        String toAccountNumber = transferFundsRequest.getToAccount();
        BigDecimal amount = transferFundsRequest.getTransactionAmount();
        String narration = transferFundsRequest.getNarration();



        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber).orElseThrow(()->
                new ResourceNotFoundException("Account", "account number", fromAccountNumber));

        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber).orElseThrow(()->
                new ResourceNotFoundException("Account", "account number", toAccountNumber));

        BigDecimal limit = fromAccount.getDailyTransactionLimit();

        if(fromAccount.getAccountBalance().compareTo(amount) > 0) {

            if (limit.compareTo(transferFundsRequest.getTransactionAmount()) >= 0) {
                fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(amount));
                fromAccount.setDateUpdated(fromAccount.getDateUpdated());

                accountRepository.save(fromAccount);

                toAccount.setAccountBalance(toAccount.getAccountBalance().add(amount));
                toAccount.setDateUpdated(toAccount.getDateUpdated());

                accountRepository.save(toAccount);

                // transactions from an account
                Transaction fromTransactionAccount = new Transaction();
                fromTransactionAccount.setTransactionAmount(amount);
                fromTransactionAccount.setTransactionType(TransactionType.DEBIT);
                fromTransactionAccount.setDateCreated(fromAccount.getDateUpdated());
                fromTransactionAccount.setAccountNumber(fromAccountNumber);
                fromTransactionAccount.setAccount(fromAccount);
                fromTransactionAccount.setNarration(narration);

                transactionRepository.save(fromTransactionAccount);

                // transaction to an account
                Transaction toTransactionAccount = new Transaction();
                toTransactionAccount.setTransactionAmount(amount);
                toTransactionAccount.setTransactionType(TransactionType.CREDIT);
                toTransactionAccount.setDateCreated(toAccount.getDateUpdated());
                toTransactionAccount.setAccountNumber(toAccountNumber);
                toTransactionAccount.setAccount(toAccount);
                toTransactionAccount.setNarration(narration);


                transactionRepository.save(toTransactionAccount);

                emailSenderService.emailSender(toAccount.getUser().getEmailId(), "CREDIT TRANSACTION NOTIFICATION",
                        "Success: " + amount + " was transferred to Account Number " + toAccountNumber );

                emailSenderService.emailSender(fromAccount.getUser().getEmailId(), "DEBIT TRANSACTION NOTIFICATION",
                        "Success: " + amount + " was debited from Account Number " + fromAccountNumber );

                return "Success: " + amount + " was transferred to Account Number " + toAccountNumber;
            }
            else{
                throw new BankApiException(HttpStatus.BAD_REQUEST, "You have exceeded your daily Transaction limit");
            }

        } else{
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }
    }

    @Override
    public String withdrawFunds(WithdrawFundsRequest withdrawFundsRequest) {

        String fromAccountNumber = withdrawFundsRequest.getFromAccount();
        BigDecimal amount = withdrawFundsRequest.getTransactionAmount();


        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber).orElseThrow(()->
                new ResourceNotFoundException("Account", "account number", fromAccountNumber));

        BigDecimal limit = fromAccount.getDailyTransactionLimit();

        if(fromAccount.getAccountBalance().compareTo(amount) >=  0){
            if(limit.compareTo(withdrawFundsRequest.getTransactionAmount()) > 0){
                fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(amount));
                accountRepository.save(fromAccount);

                // transactions from an account
                Transaction fromTransactionAccount = new Transaction();
                fromTransactionAccount.setTransactionAmount(amount);
                fromTransactionAccount.setTransactionType(TransactionType.DEBIT);
                fromTransactionAccount.setDateCreated(fromAccount.getDateUpdated());
                fromTransactionAccount.setAccountNumber(fromAccountNumber);
                fromTransactionAccount.setAccount(fromAccount);

                transactionRepository.save(fromTransactionAccount);
            } else {
                throw new BankApiException(HttpStatus.BAD_REQUEST, "You have exceeded your daily transaction limit");
            }

            emailSenderService.emailSender(fromAccount.getUser().getEmailId(), "DEBIT TRANSACTION NOTIFICATION",
                    "Success: " + amount + " was debited from Account Number " + fromAccountNumber );

            return "Success: An amount of " + amount + " withdrawn successfully";

        } else {
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }
    }

    @Override
    public String depositFunds(DepositFundsRequest depositFundsRequest) {


        String toAccountNumber = depositFundsRequest.getToAccount();
        BigDecimal amount = depositFundsRequest.getTransactionAmount();
        String narration = depositFundsRequest.getNarration();

        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber).orElseThrow(()->
                new ResourceNotFoundException("Account", "account number", toAccountNumber));

        toAccount.setAccountBalance(toAccount.getAccountBalance().add(amount));
        toAccount.setDateUpdated(toAccount.getDateUpdated());



        Account updatedAccount= accountRepository.save(toAccount);


        // transaction to an account
        Transaction toTransactionAccount = new Transaction();
        toTransactionAccount.setTransactionAmount(amount);
        toTransactionAccount.setTransactionType(TransactionType.CREDIT);
        toTransactionAccount.setDateCreated(toAccount.getDateUpdated());
        toTransactionAccount.setAccountNumber(toAccountNumber);
        toTransactionAccount.setAccount(updatedAccount);
        toTransactionAccount.setNarration(narration);



        transactionRepository.save(toTransactionAccount);

        emailSenderService.emailSender(toAccount.getUser().getEmailId(), "CREDIT TRANSACTION NOTIFICATION",
                "Success: " + amount + " was deposited to Account Number " + toAccountNumber );

        return "Success: An amount of " + amount + " was deposited successfully to " + toAccountNumber;

    }
}
