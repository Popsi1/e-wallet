package com.example.walletapp.controllers;

import com.example.walletapp.dtos.WalletUserDto;
import com.example.walletapp.models.WalletUser;
import com.example.walletapp.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/walletUser/{walletUserId}/withdrawOrTopUp/{amount}/kyc/{transactionType}")
    public ResponseEntity<String> withdrawOrTopUpWalletController(@PathVariable Long walletUserId, @PathVariable double amount, @PathVariable String transactionType) throws Exception {
        String transactionStatus = transactionService.withdrawOrTopUpWallet(walletUserId, amount, transactionType);
        return new ResponseEntity<>(transactionStatus, HttpStatus.CREATED);
    }

    @GetMapping("/walletUserSender/{walletUserSenderId}/walletUserReceiver/{walletUserReceiverId}/transfer/{amount}")
    public ResponseEntity<String> transferFromOneWalletUserToAnotherWalletUserController(@PathVariable Long walletUserSenderId, @PathVariable Long walletUserReceiverId, @PathVariable double amount) throws Exception {
        String transactionStatus = transactionService.transferFromOneWalletUserToAnotherWalletUser(walletUserSenderId, walletUserReceiverId, amount);
        return new ResponseEntity<>(transactionStatus, HttpStatus.CREATED);
    }

    @GetMapping("/walletUserReceiverEmail/{emailOfWalletUserReceiver}/walletUserReceiverAccountNumber/{accountNumberOfWalletUserReceiver}/walletUserSender/{walletUserSenderId}/transfer/{amount}")
    public ResponseEntity<String> transferFromWalletByEmailAndAccountNumberController(@PathVariable String emailOfWalletUserReceiver, @PathVariable String accountNumberOfWalletUserReceiver, @PathVariable Long walletUserSenderId, @PathVariable double amount) throws Exception {
        String transactionStatus = transactionService.transferFromWalletByEmailAndAccountNumber(emailOfWalletUserReceiver, accountNumberOfWalletUserReceiver, walletUserSenderId, amount);
        return new ResponseEntity<>(transactionStatus, HttpStatus.CREATED);
    }
}
