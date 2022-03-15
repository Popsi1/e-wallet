package com.example.walletapp.models;

import com.example.walletapp.dtos.WalletUserDto;
import com.example.walletapp.enums.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletUser extends BaseModel {

    @Column(name = "email")
    private String  email;

    @Column( name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "walletUser")
    private Wallet wallet;


    public static WalletUser from(WalletUserDto walletUserDto){
        WalletUser walletUser = new WalletUser();
        walletUser.setEmail(walletUserDto.getEmail());
        walletUser.setFirstName(walletUserDto.getFirstName());
        walletUser.setLastName(walletUserDto.getLastName());
        walletUser.setRole(Role.valueOf(walletUserDto.getRole()));
        walletUser.setPhoneNumber(walletUserDto.getPhoneNumber());
        walletUser.setPassword(walletUserDto.getPassword());
        return walletUser;
    }
}
