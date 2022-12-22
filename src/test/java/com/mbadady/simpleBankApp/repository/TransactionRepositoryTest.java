package com.mbadady.simpleBankApp.repository;

import com.mbadady.simpleBankApp.dao.AccountRepository;
import com.mbadady.simpleBankApp.dao.TransactionRepository;
import com.mbadady.simpleBankApp.enums.AccountStatusConstant;
import com.mbadady.simpleBankApp.enums.AccountType;
import com.mbadady.simpleBankApp.enums.TransactionType;
import com.mbadady.simpleBankApp.model.Account;
import com.mbadady.simpleBankApp.model.Transaction;
import com.mbadady.simpleBankApp.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Account account;

    private Transaction transaction;

    @BeforeEach
    void setUp(){
        account = Account.builder()
                .id(1L)
                .user(User.builder().id(1L).build())
                .accountNumber("00000000003")
                .AccountBalance(BigDecimal.valueOf(5000))
                .accountType(AccountType.SAVINGS)
                .dailyTransactionLimit(BigDecimal.valueOf(1000))
                .accountStatusConstant(AccountStatusConstant.ACTIVE)
                .build();

        transaction = Transaction.builder()
//                .id(1L)
                .transactionAmount(BigDecimal.valueOf(200))
                .transactionType(TransactionType.CREDIT)
                .accountNumber("00000000003")
                .narration("Credit Alert")
                .build();
    }

    // Junit for find all transactions by account number

        @Test
        public void givenUserAccountNumber_whenFindAllTransactions_thenReturnAccountObject(){
            // given- precondition or setup

          Transaction transaction1 = Transaction.builder()
//                    .id(2L)
                    .transactionAmount(BigDecimal.valueOf(200))
                    .transactionType(TransactionType.CREDIT)
                    .accountNumber("00000000003")
                    .narration("Credit Alert")
                    .build();
          transactionRepository.save(transaction1);
          transactionRepository.save(transaction);
          accountRepository.save(account);

            // when- action or behaviour we are going to test

            Account savedAccount = accountRepository.findByAccountNumber(account.getAccountNumber()).get();

            List<Transaction> listOfTransactions = transactionRepository.findAll();
            // the- verify the output

            Assertions.assertThat(savedAccount).isNotNull();
            Assertions.assertThat(listOfTransactions).isNotNull();
            Assertions.assertThat(listOfTransactions.size()).isEqualTo(2);
        }

        // Junit for transfer funds

            @Test
            public void givenAccountNumber_whenTransferFunds_thenReturnTransactionObject(){
                // given- precondition or setup

             Account toAccount = Account.builder()
                        .id(1L)
                        .user(User.builder().id(1L).build())
                        .accountNumber("00000000004")
                        .AccountBalance(BigDecimal.TEN)
                        .accountType(AccountType.SAVINGS)
                        .dailyTransactionLimit(BigDecimal.valueOf(1000))
                        .accountStatusConstant(AccountStatusConstant.ACTIVE)
                        .build();

             Account fromAccount = Account.builder()
                        .id(2L)
                        .user(User.builder().id(1L).build())
                        .accountNumber("00000000005")
                        .AccountBalance(BigDecimal.valueOf(2000))
                        .accountType(AccountType.SAVINGS)
                        .dailyTransactionLimit(BigDecimal.valueOf(1000))
                        .accountStatusConstant(AccountStatusConstant.ACTIVE)
                        .build();

             accountRepository.save(toAccount);
             accountRepository.save(fromAccount);

                // when- action or behaviour we are going to test

                Account savedToAccount = accountRepository.findByAccountNumber(toAccount.getAccountNumber()).get();

                Account savedFromAccount = accountRepository.findByAccountNumber(fromAccount.getAccountNumber()).get();

                savedFromAccount.setAccountBalance(BigDecimal.valueOf(1000));
                savedFromAccount.setDateUpdated(account.getDateUpdated());

                BigDecimal amount = BigDecimal.valueOf(1000);

                accountRepository.save(savedToAccount);

                savedToAccount.setDateUpdated(account.getDateUpdated());
                savedToAccount.setAccountBalance(BigDecimal.valueOf(3000));

                accountRepository.save(savedToAccount);

                Transaction transactionToAccount = Transaction.builder()
//                    .id(2L)
                        .transactionAmount(amount)
                        .transactionType(TransactionType.CREDIT)
                        .accountNumber(toAccount.getAccountNumber())
                        .account(toAccount)
                        .narration("Credit Alert")
                        .build();

                Transaction transactionFromAccount = Transaction.builder()
//                    .id(2L)
                        .transactionAmount(amount)
                        .transactionType(TransactionType.DEBIT)
                        .accountNumber(fromAccount.getAccountNumber())
                        .account(fromAccount)
                        .narration("Debit Alert")
                        .build();
                transactionRepository.save(transactionFromAccount);
                transactionRepository.save(transactionToAccount);

                // the- verify the output

                Assertions.assertThat(transactionFromAccount).isNotNull();
                Assertions.assertThat(transactionToAccount).isNotNull();
                Assertions.assertThat(savedFromAccount).isNotNull();
                Assertions.assertThat(savedToAccount).isNotNull();

                Assertions.assertThat(transactionToAccount.getTransactionAmount()).isEqualTo(amount);
                Assertions.assertThat(transactionFromAccount.getTransactionAmount()).isEqualTo(amount);

                Assertions.assertThat(transactionFromAccount.getAccount().getAccountNumber()).isEqualTo(savedFromAccount.getAccountNumber());
                Assertions.assertThat(transactionToAccount.getAccount().getAccountNumber()).isEqualTo(savedToAccount.getAccountNumber());

                Assertions.assertThat(transactionToAccount.getNarration()).isEqualTo("Credit Alert");
                Assertions.assertThat(transactionFromAccount.getNarration()).isEqualTo("Debit Alert");
            }

            // Junit for deposit funds

                @Test
                public void givenAccountNumber_whenDepositFunds_thenReturnTransactionObject(){
                    // given- precondition or setup

                    accountRepository.save(account);

                    BigDecimal amount = BigDecimal.valueOf(1000);

                    // when- action or behaviour we are going to test

                    Account savedAccount = accountRepository.findByAccountNumber(account.getAccountNumber()).get();

                    savedAccount.setAccountBalance(account.getAccountBalance().add(amount));
                    savedAccount.setDateUpdated(account.getDateUpdated());

                    accountRepository.save(savedAccount);

                    Transaction transactionToAccount = Transaction.builder()
//                    .id(2L)
                            .transactionAmount(amount)
                            .transactionType(TransactionType.CREDIT)
                            .accountNumber(account.getAccountNumber())
                            .account(account)
                            .narration("Credit Alert")
                            .build();

                    transactionRepository.save(transactionToAccount);
                    // the- verify the output

                    Assertions.assertThat(savedAccount).isNotNull();
                    Assertions.assertThat(transactionToAccount).isNotNull();
                    Assertions.assertThat(transactionToAccount.getTransactionAmount()).isEqualTo(amount);

                    Assertions.assertThat(transactionToAccount.getAccount().getAccountNumber()).isEqualTo(account.getAccountNumber());
                    Assertions.assertThat(transactionToAccount.getNarration()).isEqualTo("Credit Alert");


                }

                // Junit for withdrawing funds

                    @Test
                    public void givenAccountNumber_whenWithdrawFunds_thenReturnTransactionObject(){
                        // given- precondition or setup

                        accountRepository.save(account);

                        BigDecimal amount = BigDecimal.valueOf(1000);

                        // when- action or behaviour we are going to test

                        Account savedAccount = accountRepository.findByAccountNumber(account.getAccountNumber()).get();

                        savedAccount.setAccountBalance(account.getAccountBalance().subtract(amount));
                        savedAccount.setDateUpdated(account.getDateUpdated());

                        accountRepository.save(savedAccount);

                        Transaction transactionToAccount = Transaction.builder()
//                    .id(2L)
                                .transactionAmount(amount)
                                .transactionType(TransactionType.DEBIT)
                                .accountNumber(account.getAccountNumber())
                                .account(account)
                                .narration("Debit Alert")
                                .build();

                        transactionRepository.save(transactionToAccount);
                        // the- verify the output

                        Assertions.assertThat(savedAccount).isNotNull();
                        Assertions.assertThat(savedAccount.getAccountBalance()).isEqualTo(account.getAccountBalance().subtract(amount));
                        Assertions.assertThat(transactionToAccount).isNotNull();
                        Assertions.assertThat(transactionToAccount.getTransactionAmount()).isEqualTo(amount);

                        Assertions.assertThat(transactionToAccount.getAccount().getAccountNumber()).isEqualTo(account.getAccountNumber());
                        Assertions.assertThat(transactionToAccount.getNarration()).isEqualTo("Debit Alert");

                    }
}
