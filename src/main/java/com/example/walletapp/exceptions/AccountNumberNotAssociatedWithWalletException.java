package com.example.walletapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountNumberNotAssociatedWithWalletException extends RuntimeException{
    public AccountNumberNotAssociatedWithWalletException(Long walletId) {
        super("Wallet with walledId : "+walletId+" does not have an account number");}
}