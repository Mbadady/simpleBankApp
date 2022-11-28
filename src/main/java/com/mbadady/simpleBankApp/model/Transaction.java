package com.mbadady.simpleBankApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mbadady.simpleBankApp.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    private String accountNumber;

    private BigDecimal transactionAmount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @CreationTimestamp
    private Timestamp dateCreated;

    private String narration;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "account_id")
    private Account account;
}
