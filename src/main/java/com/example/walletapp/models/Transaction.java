package com.example.walletapp.models;

import com.example.walletapp.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction extends BaseModel{

    @Column(name = "amount")
    private double amount;

    @Column(name = "description")
    private String description;

    @Column(name = "postBalance")
    private double postBalance;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType type;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Wallet wallet;
}
