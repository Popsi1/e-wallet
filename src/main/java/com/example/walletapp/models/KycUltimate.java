package com.example.walletapp.models;

import com.example.walletapp.enums.KycLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class KycUltimate extends BaseModel{

    @Column(name = "NationalId")
    private String NationalId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "kycLevel")
    private KycLevel kycLevel;

    @OneToOne
    private Wallet wallet;
}
