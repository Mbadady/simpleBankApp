package com.mbadady.simpleBankApp.dao;

import com.mbadady.simpleBankApp.dto.request.TransactionRequest;
import com.mbadady.simpleBankApp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountNumber(String accountNumber);
}
