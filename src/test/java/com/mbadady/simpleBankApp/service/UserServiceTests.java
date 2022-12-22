package com.mbadady.simpleBankApp.service;


import com.mbadady.simpleBankApp.config.SecurityConfig;
import com.mbadady.simpleBankApp.dao.RoleRepository;
import com.mbadady.simpleBankApp.dao.UserRepository;
import com.mbadady.simpleBankApp.dto.request.UserRequest;
import com.mbadady.simpleBankApp.model.AddressDetails;
import com.mbadady.simpleBankApp.model.Role;
import com.mbadady.simpleBankApp.model.User;
import com.mbadady.simpleBankApp.service.serviceImpl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static com.mbadady.simpleBankApp.enums.GenderConstant.MALE;

@ExtendWith(MockitoExtension.class) // used to tell junit that we are using annotations
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;


    @Mock
    private SecurityConfig securityConfig;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;


    private UserRequest userRequest;

    @BeforeEach
    void setUp(){

        user = User.builder()
                .id(1L)
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

        userRequest = UserRequest.builder()
                .emailId("victorsomtochukwu@gmail.com")
                .gender("MALE")
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

    // Junit for

        @Test
        public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
            // given- precondition or setup

            BDDMockito.given(userRepository.findByEmailId(user.getEmailId())).willReturn(Optional.empty());
            BDDMockito.given(userRepository.findByPhoneNumber(user.getPhoneNumber())).willReturn(Optional.empty());

//            BDDMockito.when(securityConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
            BDDMockito.given(userRepository.save(user)).willReturn(user);

            // when- action or behaviour we are going to test

            String savedUser = userService.signUp(userRequest);
            // the- verify the output
            Assertions.assertThat(savedUser).isNotNull();
        }
}
