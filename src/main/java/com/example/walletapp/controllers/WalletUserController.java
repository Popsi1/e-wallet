package com.example.walletapp.controllers;

import com.example.walletapp.dtos.WalletUserDto;
import com.example.walletapp.exceptions.PhoneNumberAlreadyExistException;
import com.example.walletapp.models.WalletUser;
import com.example.walletapp.repositories.WalletUserRepository;
import com.example.walletapp.services.WalletUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletUserController {

    @Autowired
    private WalletUserService walletUserService;

    @PostMapping("/create/walletUser")
    public ResponseEntity<WalletUser> createWalletUserController(@RequestBody WalletUserDto walletUserDto){
        WalletUser walletUserFromDto = WalletUser.from(walletUserDto);
        WalletUser walletUser = walletUserService.createWalletUser(walletUserFromDto);
        return new ResponseEntity<>(walletUser, HttpStatus.CREATED);
    }

    @GetMapping("/approveKycMaster/{adminWalletUserId}/user/{walletUserId}")
    public ResponseEntity<String> approveKycMasterVerification(@PathVariable Long adminWalletUserId, @PathVariable Long walletUserId) throws Exception {
        String approveKycMaster = walletUserService.adminToApproveKycMasterVerification(adminWalletUserId,walletUserId);
        return new ResponseEntity<>(approveKycMaster, HttpStatus.OK);
    }

    @GetMapping("/approveKycUltimate/{adminWalletUserId}/user/{walletUserId}")
    public ResponseEntity<String> approveKycUltimateVerification(@PathVariable Long adminWalletUserId,@PathVariable Long walletUserId) throws Exception {
        String approveKycUltimate = walletUserService.adminToApproveKycUltimateVerification(adminWalletUserId,walletUserId);
        return new ResponseEntity<>(approveKycUltimate, HttpStatus.OK);
    }

}
