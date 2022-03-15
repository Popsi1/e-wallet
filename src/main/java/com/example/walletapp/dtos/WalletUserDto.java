package com.example.walletapp.dtos;

import com.example.walletapp.enums.Role;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WalletUserDto {

    private String  email;

    private String firstName;

    private String lastName;

    private String role;

    private String phoneNumber;

    private String password;


}
