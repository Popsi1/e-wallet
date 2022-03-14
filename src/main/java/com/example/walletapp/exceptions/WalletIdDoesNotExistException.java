package com.example.walletapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WalletIdDoesNotExistException extends RuntimeException{
    public WalletIdDoesNotExistException(Long walletId) {
        super("Wallet with walletId : "+walletId+" does not exist");}
}