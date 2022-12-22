package com.mbadady.simpleBankApp.repository;


import com.mbadady.simpleBankApp.dao.UserRepository;
import com.mbadady.simpleBankApp.enums.GenderConstant;
import com.mbadady.simpleBankApp.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.mbadady.simpleBankApp.enums.GenderConstant.MALE;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

//    private User user;

    private User user;

    private AddressDetails addressDetails;

    private Role role;

    private Account account;

    private Transaction transaction;

    @BeforeEach
    public void setUp(){
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
    }

    // Junit for user signUp

        @Test
        public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
            // given- precondition or setup

//            Refactored************
//          User user = User.builder().emailId("victorsomtochukwu@gmail.com")
//                    .gender("MALE")
//                    .dateOfBirth(LocalDate.ofEpochDay(1994- 5 -18))
//                    .firstName("Somtochukwu")
//                    .lastName("Mbah")
//                    .middleName("Victor")
//                    .password("Mbadady1_")
//                    .phoneNumber("08165195382")
//                    .address(AddressDetails.builder().state("Enugu").city("Enugu").address("Test")
//                            .country("Nigeria").build())
//                    .roles(Set.of(Role.builder().name("ADMIN").build(), Role.builder().name("USER").build()))
//                    .build();

            // when- action or behaviour we are going to test

            User savedUser = userRepository.save(user);


            // the- verify the output

            Assertions.assertThat(savedUser).isNotNull();
            Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

        }

    // Junit test for get all users
    @Test
    public void givenListOfUsers_whenFindAll_thenReturnListOfUsers(){
        // given- precondition or setup

//        Refactored**********

        User user1 = User.builder().emailId("azubuine.emeka@gmail.com")
                    .gender(MALE)
                    .dateOfBirth(LocalDate.ofEpochDay(1994- 5 -18))
                    .firstName("Emeka")
                    .lastName("Azubuine")
                    .middleName("Chijioke")
                    .password("Emeka1_")
                    .phoneNumber("08164404457")
                    .address(AddressDetails.builder().state("Abuja").city("Kurudu").address("Test")
                            .country("Nigeria").build())
                    .roles(Set.of(Role.builder().name("ADMIN").build(), Role.builder().name("USER").build()))
                    .build();

        userRepository.save(user1);
        userRepository.save(user);


        // when- action or behaviour we are going to test

        List<User> userList = userRepository.findAll();

        // the- verify the output
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isGreaterThan(0);

    }

    // Junit for getting user by email

        @Test
        public void givenUserEmail_whenFindByEmail_thenReturnUserObject(){
            // given- precondition or setup

            userRepository.save(user);

            // when- action or behaviour we are going to test
    User savedUser = userRepository.findByEmailId(user.getEmailId()).get();

            // the- verify the output

            Assertions.assertThat(savedUser).isNotNull();

        }

    // Junit for getting user by phone number

    @Test
    public void givenUserPhoneNumber_whenFindByPhoneNumber_thenReturnUserObject(){
        // given- precondition or setup

        userRepository.save(user);

        // when- action or behaviour we are going to test
        User savedUser = userRepository.findByPhoneNumber(user.getPhoneNumber()).get();

        // the- verify the output

        Assertions.assertThat(savedUser).isNotNull();

    }

    // Junit for Update user details

        @Test
        public void givenUserObject_whenUpdateUser_thenUpdatedUserObject(){
            // given- precondition or setup
           userRepository.save(user);

            // when- action or behaviour we are going to test
     User savedUser = userRepository.findByEmailId(user.getEmailId()).get();
    savedUser.setPhoneNumber("08164404457");
    savedUser.setMiddleName("Chijioke");
    savedUser.setLastName("Azubuine");
    savedUser.setEmailId("azubuine.emeka@gmail.com");

//      userRepository.save(savedUser);

            // the- verify the output
    Assertions.assertThat(savedUser.getMiddleName()).isEqualTo("Chijioke");
    Assertions.assertThat(savedUser.getEmailId())
            .isEqualTo("azubuine.emeka@gmail.com");
    Assertions.assertThat(savedUser.getPhoneNumber()).isEqualTo("08164404457");
    Assertions.assertThat(savedUser.getLastName()).isEqualTo("Azubuine");

        }

        // Junit for delete user

            @Test
            public void givenUserId_whenDeleteById_thenReturnNothing(){
                // given- precondition or setup
        userRepository.save(user);

                // when- action or behaviour we are going to test
                userRepository.delete(user);
        Optional<User> userToDelete = userRepository.findById(user.getId());


                // the- verify the output

        Assertions.assertThat(userToDelete).isEmpty();
            }

}
