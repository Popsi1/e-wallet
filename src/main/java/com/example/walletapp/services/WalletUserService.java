package com.example.walletapp.services;
import com.example.walletapp.enums.KycLevel;
import com.example.walletapp.enums.Role;
import com.example.walletapp.exceptions.*;
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
        try{
             WalletUser walletUserByEmail = walletUserRepository.findWalletUserByEmail(walletUser.getEmail());
             if(walletUserByEmail.getEmail() != null){
                throw new EmailAlreadyExistException(walletUserByEmail.getEmail());
            }

        }catch (NullPointerException ex){

        }
        try {
            WalletUser walletUserByPhoneNumber = walletUserRepository.findWalletUserByPhoneNumber(walletUser.getPhoneNumber());
            if(walletUserByPhoneNumber.getPhoneNumber() != null){
                throw new PhoneNumberAlreadyExistException(walletUserByPhoneNumber.getPhoneNumber());
            }
        }catch (NullPointerException ex){

        }

        WalletUser walletUserSaved = walletUserRepository.save(walletUser);

        return walletUserSaved;
    }

    @Transactional
    public String adminToApproveKycMasterVerification(Long adminWalletUserId, Long walletUserId) throws Exception {
        WalletUser admin = walletUserRepository.findWalletUserById(adminWalletUserId);
        WalletUser walletUser = walletUserRepository.findById(walletUserId).orElse(null);
        // try catch catches only one null exception not two
        try {
            if (walletUser == null) {
                throw new UserDoesNotExistException(walletUserId);
            }
        }catch (NullPointerException ex){

        }

        try {
            assert walletUser != null;
            if (walletUser.getWallet() == null) { // nullpointerexception can occur here
                throw new WalletIdDoesNotExistException(walletUser.getFirstName() +" dont have a wallet"); // and also occur here, so its better u use message to avoid double errors which cannot be caught by try catch(only catch one exception not two)
            }
        }catch (NullPointerException ex){

        }

        try {

            if (walletUser.getWallet().getKycLevel() == KycLevel.Master && walletUser.getWallet().getKycLevel() ==KycLevel.Ultimate) {
                throw new kycLevelAlreadyExistException(walletUser.getWallet().getId());
            }
        }catch (NullPointerException ex){

        }

        try {

            if (walletUser.getWallet().getKycMaster() == null) {
                throw new KycMasterDoesNotExistException(walletUser.getWallet().getId());
            }
        }catch (NullPointerException ex){

        }

        if(admin.getRole().equals(Role.Admin)){
            walletUser.getWallet().setKycLevel(KycLevel.Master);
            walletUserRepository.save(walletUser);
        }else
            throw new Exception("wallet user role must be admin");


        return "kyc master verified";
    }


    @Transactional
    public String adminToApproveKycUltimateVerification(Long adminWalletUserId, Long walletUserId) throws Exception {

        WalletUser admin = walletUserRepository.findWalletUserById(adminWalletUserId);
        WalletUser walletUser = walletUserRepository.findById(walletUserId).orElse(null);


        if (walletUser == null) {
            throw new UserDoesNotExistException(walletUserId);
        }

        if (walletUser.getWallet() == null) {
            throw new WalletIdDoesNotExistException(walletUser.getFirstName() +" dont have a wallet");
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

        if(admin.getRole().equals(Role.Admin) && walletUser.getWallet().getKycLevel() == KycLevel.Master){
            walletUser.getWallet().setKycLevel(KycLevel.Ultimate);
            walletUserRepository.save(walletUser);
        }else
            throw new Exception("wallet user role must be admin");

        return "kyc ultimate verified";
    }
}
