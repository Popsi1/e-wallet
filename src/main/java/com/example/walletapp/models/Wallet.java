package com.example.walletapp.models;

import com.example.walletapp.dtos.WalletDto;
import com.example.walletapp.dtos.WalletUserDto;
import com.example.walletapp.enums.KycLevel;
import com.example.walletapp.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wallet extends BaseModel {

    @Column(name = "walletname")
    private String walletName;

    @Column(unique = true, name = "accountnumber")
    private String accountNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "currentbalance")
    private double balance;

    @OneToOne
    @JsonIgnore
    private WalletUser walletUser;

    @OneToOne
    @JsonIgnore
    private KycMaster kycMaster;

    @OneToOne
    @JsonIgnore
    private KycUltimate kycUltimate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "kycLevel")
    private KycLevel kycLevel;

    @PrePersist
    public void setBalance(){ this.balance = 0.0;}

    public static Wallet from(WalletDto walletDto) {
        Wallet wallet = new Wallet();
        wallet.setWalletName(walletDto.getWalletName());
        wallet.setDescription(walletDto.getDescription());

        return wallet;
    }
}
