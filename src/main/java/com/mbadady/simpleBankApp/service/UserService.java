package com.mbadady.simpleBankApp.service;

import com.mbadady.simpleBankApp.dto.request.UserRequest;

import java.util.List;

public interface UserService {
    List<UserRequest> findAll();
    String signUp(UserRequest userRequest);
    UserRequest findByEmailId(String email);
    UserRequest findByPhoneNumber(String phoneNumber);
    String updateUser(UserRequest user, Long userId);
    String deleteUser(Long userId);
}
