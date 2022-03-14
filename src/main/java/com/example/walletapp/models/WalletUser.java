package com.example.walletapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletUser extends BaseModel {

    @Column(nullable = false, unique = true, name = "email")
    private String  email;

    @Column(nullable = false, name = "firstname")
    private String firstName;

    @Column(nullable = false, name = "lastname")
    private String lastName;

    @Column(nullable = false, unique = true, name = "phonenumber")
    private String phoneNumber;

    @Column(nullable = false, name = "password")
    private String password;

    @OneToOne(mappedBy = "walletUser")
    private Wallet wallet;

    @OneToOne(mappedBy = "walletUser")
    private WalletUser walletUser;

}
