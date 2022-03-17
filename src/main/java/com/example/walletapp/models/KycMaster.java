package com.example.walletapp.models;

import com.example.walletapp.dtos.KycMasterDto;
import com.example.walletapp.dtos.WalletDto;
import com.example.walletapp.enums.KycLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class KycMaster extends BaseModel{

    private String bvnNumber;

    @OneToOne
    @JsonIgnore
    private Wallet wallet;

    public static KycMaster from(KycMasterDto kycMasterDto) {
        KycMaster kycMaster = new KycMaster();
        kycMaster.setBvnNumber(kycMasterDto.getBvnNumber());

        return kycMaster;
    }
}
