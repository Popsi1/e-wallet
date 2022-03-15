package com.example.walletapp.controllers;

import com.example.walletapp.dtos.WalletDto;
import com.example.walletapp.exceptions.UserAlreadyHasWalletException;
import com.example.walletapp.exceptions.UserDoesNotExistException;
import com.example.walletapp.models.Wallet;
import com.example.walletapp.services.ValidationErrorService;
import com.example.walletapp.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private ValidationErrorService validationErrorService;

    @PostMapping("/create/{id}")
    public ResponseEntity<Wallet> createWalletController(@Valid @RequestBody WalletDto walletDto, @PathVariable Long id) throws UserAlreadyHasWalletException, UserDoesNotExistException {
        Wallet walletFromDto = Wallet.from(walletDto);
        Wallet walletSaved = walletService.createWallet(id, walletFromDto);

        return new ResponseEntity<>(walletSaved,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        walletService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
