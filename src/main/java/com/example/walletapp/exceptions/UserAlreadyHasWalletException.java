package com.example.walletapp.exceptions;

import com.example.walletapp.models.WalletUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyHasWalletException extends RuntimeException{
    public UserAlreadyHasWalletException(WalletUser walletUser) {
       super("WalletUser "+walletUser.getFirstName()+" "+walletUser.getLastName()+" already owns a wallet : "+walletUser.getWallet().getId());}
}