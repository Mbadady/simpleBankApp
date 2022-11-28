package com.mbadady.simpleBankApp.controller;

import com.mbadady.simpleBankApp.dto.request.UserRequest;
import com.mbadady.simpleBankApp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "User Rest Api")
@RestController
@RequestMapping("/api/v1")
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get all users of the Bank app")
    @GetMapping("/users")
    public ResponseEntity<List<UserRequest>> findAllUsers(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get user by email id")
    @GetMapping("/users/email/{email}")
    public ResponseEntity<UserRequest> findUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.findByEmailId(email), HttpStatus.OK);
    }

    @ApiOperation(value = "Get User by phone number provided")
    @GetMapping("/users/{phoneNumber}")
    public ResponseEntity<UserRequest> findUserByPhoneNumber(@PathVariable String phoneNumber){
        return new ResponseEntity<>(userService.findByPhoneNumber(phoneNumber), HttpStatus.OK);
    }

    @ApiOperation(value = "Update user details resource")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/users/{userId}")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserRequest userRequest,
                                             @PathVariable Long userId){
        return new ResponseEntity<>(userService.updateUser(userRequest, userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete User resource")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }

}
