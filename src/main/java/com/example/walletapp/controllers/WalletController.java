package com.example.walletapp.controllers;

import com.example.walletapp.exceptions.UserAlreadyHasWalletException;
import com.example.walletapp.exceptions.UserDoesNotExistException;
import com.example.walletapp.models.Wallet;
import com.example.walletapp.services.ValidationErrorService;
import com.example.walletapp.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private ValidationErrorService validationErrorService;

    @PostMapping("/{id}")
    public ResponseEntity<?> createWallet(@Valid @RequestBody Wallet wallet, @PathVariable Long id) throws UserAlreadyHasWalletException, UserDoesNotExistException {

        //ResponseEntity errors = validationErrorService.validate(result);
       // if (errors != null) return errors;
        Wallet walletSaved = walletService.createWallet(id, wallet);
        return new ResponseEntity<Wallet>(walletSaved,HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Wallet wallet, BindingResult result){
//
//        ResponseEntity errors = validationErrorService.validate(result);
//        if (errors != null) return errors;
//        wallet.setId(id);
//        Wallet walletSaved = walletService.createWallet(wallet);
//        return new ResponseEntity<Wallet>(walletSaved,HttpStatus.OK);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable Long id){
//        walletService.delete(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
