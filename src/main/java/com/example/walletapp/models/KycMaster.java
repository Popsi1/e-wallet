package com.example.walletapp.models;

import com.example.walletapp.enums.KycLevel;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class KycMaster extends BaseModel{

    @Column(name = "BvnNumber")
    private String BvnNumber;

    @OneToOne
    private Wallet wallet;
}
