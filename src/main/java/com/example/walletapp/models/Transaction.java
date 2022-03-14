package com.example.walletapp.models;

import com.example.walletapp.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction extends BaseModel{

    @Column(nullable = false, name = "amount")
    private double amount;

    @Column(name = "description")
    private String description;

    @Column(name = "postBalance")
    private double postBalance;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, name = "transaction_type")
    private TransactionType type;

    @ManyToOne
    @JoinColumn
    private Wallet wallet;
}
