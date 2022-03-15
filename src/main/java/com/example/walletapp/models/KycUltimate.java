package com.example.walletapp.models;

import com.example.walletapp.enums.KycLevel;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class KycUltimate extends BaseModel{

    @Column(name = "NationalId")
    private String NationalId;

    @OneToOne
    private Wallet wallet;
}
