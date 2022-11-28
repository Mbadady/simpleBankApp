package com.mbadady.simpleBankApp.controller;

import com.mbadady.simpleBankApp.dto.request.LoginRequest;
import com.mbadady.simpleBankApp.dto.request.UserRequest;
import com.mbadady.simpleBankApp.dto.response.JWTResponse;
import com.mbadady.simpleBankApp.security.JwtTokenProvider;
import com.mbadady.simpleBankApp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "Rest API to Sign in and register a user")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }




    @ApiOperation(value = "Rest Api to Sign in a User")
    @PostMapping("/signin")
    public ResponseEntity<JWTResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmailIdOrPhoneNumber(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        JWTResponse jwtResponse = new JWTResponse(token);

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Rest Api to Signup or register a User")
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.signUp(userRequest), HttpStatus.CREATED);
    }

}
