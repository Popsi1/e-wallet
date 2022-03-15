package com.example.walletapp.models;

import com.example.walletapp.dtos.WalletDto;
import com.example.walletapp.dtos.WalletUserDto;
import com.example.walletapp.enums.KycLevel;
import com.example.walletapp.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wallet extends BaseModel {

    @Column(nullable = false, name = "walletname")
    private String walletName;

    @Column(nullable = false, unique = true, name = "accountnumber")
    private String accountNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "currentbalance")
    private double balance;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<Transaction> transaction;

    @OneToOne
    private WalletUser walletUser;

    @OneToOne(mappedBy = "wallet")
    private KycMaster kycMaster;

    @OneToOne(mappedBy = "wallet")
    private KycUltimate kycUltimate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "kycLevel")
    private KycLevel kycLevel;

    @PrePersist
    public void setBalance(){ this.balance = 0.0;}

    public static Wallet from(WalletDto walletDto) {
        Wallet wallet = new Wallet();
        wallet.setAccountNumber(walletDto.getWalletName());
        wallet.setDescription(walletDto.getDescription());

        return wallet;
    }
}
