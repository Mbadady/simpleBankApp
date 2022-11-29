package com.mbadady.simpleBankApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mbadady.simpleBankApp.enums.AccountStatusConstant;
import com.mbadady.simpleBankApp.enums.AccountType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private String accountNumber;

    @Builder.Default
    private BigDecimal AccountBalance = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal dailyTransactionLimit = BigDecimal.ZERO;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private  Timestamp dateUpdated;

    @Enumerated(EnumType.STRING)
    private AccountStatusConstant accountStatusConstant = AccountStatusConstant.ACTIVE;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @JsonManagedReference
    @OneToMany(mappedBy="account",cascade = CascadeType.ALL)
    List<Transaction> transactions;

}
