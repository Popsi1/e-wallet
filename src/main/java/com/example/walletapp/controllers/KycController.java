package com.example.walletapp.controllers;

import com.example.walletapp.dtos.KycMasterDto;
import com.example.walletapp.dtos.KycUltimateDto;
import com.example.walletapp.models.KycMaster;
import com.example.walletapp.models.KycUltimate;
import com.example.walletapp.repositories.KycMasterRepository;
import com.example.walletapp.services.KycService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KycController {

    @Autowired
    private KycService kycService;

    @PostMapping("/walletUser/{walletUserId}/createKycMaster/{kycLevel}")
    public ResponseEntity<String> createKycForMaster(@RequestBody KycMasterDto kycMasterDto, @PathVariable Long walletUserId, @PathVariable String kycLevel) throws Exception {
        KycMaster kycMasterFromDto = KycMaster.from(kycMasterDto);
        String kycMasterVerificationPending = kycService.createKycForMasterVerification(kycMasterFromDto, walletUserId, kycLevel);

        return new ResponseEntity<>(kycMasterVerificationPending, HttpStatus.CREATED);
    }

    @PostMapping("/walletUser/{walletUserId}/createKycUltimate/{kycLevel}")
    public ResponseEntity<String> createKycForMaster(@RequestBody KycUltimateDto kycUltimateDto, @PathVariable Long walletUserId, @PathVariable String kycLevel) throws Exception {
        KycUltimate kycUltimateFromDto = KycUltimate.from(kycUltimateDto);
        String kycUltimateDtoDtoVerificationPending = kycService.createKycForUltimateVerification(kycUltimateFromDto, walletUserId, kycLevel);

        return new ResponseEntity<>(kycUltimateDtoDtoVerificationPending, HttpStatus.CREATED);
    }
}
