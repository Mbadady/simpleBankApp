package com.mbadady.simpleBankApp.repository;


import com.mbadady.simpleBankApp.dao.AccountRepository;
import com.mbadady.simpleBankApp.dao.UserRepository;
import com.mbadady.simpleBankApp.enums.AccountStatusConstant;
import com.mbadady.simpleBankApp.enums.AccountType;
import com.mbadady.simpleBankApp.model.Account;
import com.mbadady.simpleBankApp.model.AddressDetails;
import com.mbadady.simpleBankApp.model.Role;
import com.mbadady.simpleBankApp.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.mbadady.simpleBankApp.enums.GenderConstant.MALE;

@DataJpaTest
public class AccountRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    private User user;

    private Account account;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .emailId("victorsomtochukwu@gmail.com")
                .gender(MALE)
                .dateOfBirth(LocalDate.ofEpochDay(1994- 5 -18))
                .firstName("Somtochukwu")
                .lastName("Mbah")
                .middleName("Victor")
                .password("Mbadady1_")
                .phoneNumber("08165195382")
                .address(AddressDetails.builder().state("Enugu").city("Enugu").address("Test")
                        .country("Nigeria").build())
                .roles(Set.of(Role.builder().name("ADMIN").build(), Role.builder().name("USER").build()))
                .build();

        account = Account.builder()
                .id(1L)
                .user(User.builder().id(1L).build())
                .accountNumber("0000000002")
                .AccountBalance(BigDecimal.TEN)
                .accountType(AccountType.SAVINGS)
                .dailyTransactionLimit(BigDecimal.valueOf(1000))
                .accountStatusConstant(AccountStatusConstant.ACTIVE)
                .build();
    }

    // Junit for creating an account

        @Test
        public void givenAccountObject_whenCreateAccount_thenReturnAccountObject(){
            // given- precondition or setup

            // when- action or behaviour we are going to test
            Account savedAccount = accountRepository.save(account);

            // the- verify the output
            Assertions.assertThat(savedAccount).isNotNull();
            Assertions.assertThat(savedAccount.getId()).isGreaterThan(0);
        }

        // Junit for find account by account number

            @Test
            public void givenAccountNumber_whenFindByAccountNumber_thenReturnAccountObject(){
                // given- precondition or setup
        accountRepository.save(account);

                // when- action or behaviour we are going to test

                Account savedAccount = accountRepository.findByAccountNumber(account.getAccountNumber()).get();

                // the- verify the output

                Assertions.assertThat(savedAccount).isNotNull();

            }

            // Junit for

                @Test
                public void givenListOfAccounts_whenFindAllAccountByUserId_thenReturnListOfAccounts(){
                    // given- precondition or setup
            Account account1 = Account.builder().id(2L)
                    .AccountBalance(BigDecimal.TEN).accountType(AccountType.SAVINGS)
                    .accountNumber("0000000003").accountStatusConstant(AccountStatusConstant.ACTIVE)
                    .dailyTransactionLimit(BigDecimal.valueOf(1000)).user(User.builder().id(1L).build()).build();



                    userRepository.save(user);
                    accountRepository.save(account1);
                    accountRepository.save(account);
                    // when- action or behaviour we are going to test

                    User savedUser = userRepository.findById(user.getId()).get();

                    List<Account> listOfAccounts = accountRepository.findAll();

                    // the- verify the output

                    Assertions.assertThat(savedUser).isNotNull();
                    Assertions.assertThat(listOfAccounts.size()).isEqualTo(2);


                }

                // Junit for

                    @Test
                    public void givenAccountNumber_whenDeleteByAccountNumber_thenReturnNothing(){
                        // given- precondition or setup
                accountRepository.save(account);

                        // when- action or behaviour we are going to test
                accountRepository.delete(account);

                Optional<Account> accountDeleted = accountRepository.findById(account.getId());
                        // the- verify the output

                      Assertions.assertThat(accountDeleted).isEmpty();
                    }
}
