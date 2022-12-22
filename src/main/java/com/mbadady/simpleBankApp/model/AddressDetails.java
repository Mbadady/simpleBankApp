package com.mbadady.simpleBankApp.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "Address")
public class AddressDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;
    private String address;
    private String city;
    private String state;
    private String country;

    @OneToMany(mappedBy="address",cascade = CascadeType.ALL)
    private List<User> user;
}
