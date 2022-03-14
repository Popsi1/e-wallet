package com.example.walletapp.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
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


    @PrePersist
    public void setBalance(){ this.balance = 0.0;}

}
