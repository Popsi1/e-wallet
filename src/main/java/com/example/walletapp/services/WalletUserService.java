package com.example.walletapp.services;

import com.example.walletapp.enums.KycLevel;
import com.example.walletapp.enums.Role;
import com.example.walletapp.exceptions.*;
import com.example.walletapp.models.KycMaster;
import com.example.walletapp.models.WalletUser;
import com.example.walletapp.repositories.WalletUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletUserService {

    @Autowired
    private WalletUserRepository walletUserRepository;

    public WalletUser createWalletUser(WalletUser walletUser){
        WalletUser walletUserByEmail = walletUserRepository.findWalletUserByEmail(walletUser.getEmail());
        WalletUser walletUserByPhoneNumber = walletUserRepository.findWalletUserByPhoneNumber(walletUser.getPhoneNumber());
        System.out.println(walletUserByEmail);
        System.out.println(walletUserByPhoneNumber);
        try {
            if(walletUserByEmail.getEmail() != null){
                throw new EmailAlreadyExistException(walletUserByEmail.getEmail());
            }
            if(walletUserByPhoneNumber.getPhoneNumber() != null){
                throw new PhoneNumberAlreadyExistException(walletUserByPhoneNumber.getPhoneNumber());
            }
        }catch (NullPointerException ex){
            System.out.println(ex);
        }

        WalletUser walletUserSaved = walletUserRepository.save(walletUser);

        return walletUserSaved;
    }

    @Transactional
    public String adminToApproveKycMasterVerification(Long walletUserId) throws Exception {

        WalletUser walletUser = walletUserRepository.findById(walletUserId).orElse(null);

        try{
            if (walletUser == null) {
                throw new UserDoesNotExistException(walletUserId);
            }

            if (walletUser.getWallet() == null) {
                throw new WalletIdDoesNotExistException(walletUser.getWallet().getId());
            }

            if (walletUser.getWallet().getKycLevel() != null) {
                throw new kycLevelAlreadyExistException(walletUser.getWallet().getId());
            }

            if (walletUser.getWallet().getKycMaster() == null) {
                throw new KycMasterDoesNotExistException(walletUser.getWallet().getId());
            }
        }catch (NullPointerException ex){
            System.out.println(ex);
        }


        if(walletUser.getRole().equals(Role.Admin)){
            walletUser.getWallet().setKycLevel(KycLevel.Master);
            walletUserRepository.save(walletUser);
        }else
            throw new Exception("wallet user role must be admin");


        return "kyc master verified";
    }


    @Transactional
    public String adminToApproveKycUltimateVerification(Long walletUserId) throws Exception {

        WalletUser walletUser = walletUserRepository.findById(walletUserId).orElse(null);


        if (walletUser == null) {
            throw new UserDoesNotExistException(walletUserId);
        }

        if (walletUser.getWallet() == null) {
            throw new WalletIdDoesNotExistException(walletUser.getWallet().getId());
        }

        if (walletUser.getWallet().getKycLevel() == KycLevel.Ultimate) {
            throw new kycLevelAlreadyExistException(walletUser.getWallet().getId());
        }

        if (walletUser.getWallet().getKycLevel() == null) {
            throw new Exception("you need to verify kyc master");
        }

        if (walletUser.getWallet().getKycUltimate() == null) {
            throw new KycUltimateDoesNotExistException(walletUser.getWallet().getId());
        }

        if(walletUser.getRole().equals(Role.Admin) && walletUser.getWallet().getKycLevel() == KycLevel.Master){
            walletUser.getWallet().setKycLevel(KycLevel.Ultimate);
            walletUserRepository.save(walletUser);
        }else
            throw new Exception("wallet user role must be admin");

        return "kyc ultimate verified";
    }
}
