package com.mbadady.simpleBankApp.dao;

import com.mbadady.simpleBankApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailId(String emailId);
    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmailIdOrPhoneNumber(String emailId, String phoneNumber);
    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByEmailId(String emailId);
}
