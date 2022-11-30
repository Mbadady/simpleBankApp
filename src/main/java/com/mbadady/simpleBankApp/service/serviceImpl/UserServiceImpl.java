package com.mbadady.simpleBankApp.service.serviceImpl;

import com.mbadady.simpleBankApp.customException.BankApiException;
import com.mbadady.simpleBankApp.customException.ResourceNotFoundException;
import com.mbadady.simpleBankApp.dao.RoleRepository;
import com.mbadady.simpleBankApp.dao.UserRepository;
import com.mbadady.simpleBankApp.dto.request.UserRequest;
import com.mbadady.simpleBankApp.enums.GenderConstant;
import com.mbadady.simpleBankApp.model.Role;
import com.mbadady.simpleBankApp.model.User;
import com.mbadady.simpleBankApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public List<UserRequest> findAll() {

        List<User> users = userRepository.findAll();

        if(users.size() > 0){
         return users.stream().map(this::mapToDto).collect(Collectors.toList());
        } else{
            throw new BankApiException(HttpStatus.BAD_REQUEST, "No record found");
        }
//        return null;
    }

    @Override
    public String signUp(UserRequest userRequest) {
        if(userRepository.existsByEmailId(userRequest.getEmailId())){
            throw new BankApiException(HttpStatus.BAD_REQUEST, "User already Exists with email address: " + userRequest.getEmailId());
        }

        if(userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())){
            throw new BankApiException(HttpStatus.BAD_REQUEST, "User already Exists with phone number: " + userRequest.getPhoneNumber());
        }
        User user = mapToEntity(userRequest);

//        Role role = roleRepository.findByName("ROLE_ADMIN").get();
//
//        user.setRoles(Collections.singleton(role));
        userRepository.save(user);



        emailSenderService.emailSender(userRequest.getEmailId(),"User Notification", "Account User Created Successfully");
        return "User Created Successfully";
    }

    @Override
    public UserRequest findByEmailId(String email) {

        User user = userRepository.findByEmailId(email).orElseThrow(()-> new ResourceNotFoundException("User", "Email", email));

        return mapToDto(user);
    }

    @Override
    public UserRequest findByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new ResourceNotFoundException
                ("User", "Phone Number", phoneNumber));

        return mapToDto(user);
    }

    @Override
    public String updateUser(UserRequest userRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User", "Id", userId));

        user.setId(userId);
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setAddress(userRequest.getAddress());
        user.setGender(GenderConstant.valueOf(userRequest.getGender().toUpperCase()));
        user.setEmailId(userRequest.getEmailId());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setFirstName(userRequest.getFirstName());
        user.setMiddleName(userRequest.getMiddleName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.save(user);

        emailSenderService.emailSender(userRequest.getEmailId(), "User notification", "Account user details updated successfully");

        return "User Details Updated Successfully";
    }

    @Override
    public String deleteUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);

        return "User Deleted Successfully";
    }

    private User mapToEntity(UserRequest userRequest){
        User user = new User();
        user.setAddress(userRequest.getAddress());
        user.setGender(GenderConstant.valueOf(userRequest.getGender().toUpperCase()));
        user.setEmailId(userRequest.getEmailId());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setFirstName(userRequest.getFirstName());
        user.setMiddleName(userRequest.getMiddleName());
        user.setLastName(userRequest.getLastName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Role roles = roleRepository.findByName("ROLE_USER").isPresent() ? roleRepository.findByName("ROLE_USER").get(): null;

        user.setRoles(Collections.singleton(roles));

        return user;
    }

    private UserRequest mapToDto(User user){
        UserRequest userRequest = new UserRequest();

        userRequest.setAccounts(user.getAccounts());
        userRequest.setAddress(user.getAddress());
        userRequest.setGender(user.getGender().name());
        userRequest.setFirstName(user.getFirstName());
        userRequest.setEmailId(user.getEmailId());
        userRequest.setLastName(user.getLastName());
        userRequest.setMiddleName(user.getMiddleName());
        userRequest.setPhoneNumber(user.getPhoneNumber());
        userRequest.setDateOfBirth(user.getDateOfBirth());

        return userRequest;
    }


}
