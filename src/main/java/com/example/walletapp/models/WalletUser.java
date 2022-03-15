package com.example.walletapp.models;

import com.example.walletapp.enums.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletUser extends BaseModel {

    @Column(nullable = false, unique = true, name = "email")
    private String  email;

    @Column(nullable = false, name = "firstname")
    private String firstName;

    @Column(nullable = false, name = "lastname")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(nullable = false, unique = true, name = "phonenumber")
    private String phoneNumber;

    @Column(nullable = false, name = "password")
    private String password;

    @OneToOne(mappedBy = "walletUser")
    private Wallet wallet;

}
