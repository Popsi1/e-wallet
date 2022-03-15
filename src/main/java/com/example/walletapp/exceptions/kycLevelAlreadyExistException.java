package com.example.walletapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class kycLevelAlreadyExistException extends RuntimeException{
    public kycLevelAlreadyExistException(Long walletId) {
        super("Wallet user with walled id : "+walletId+" already been verified");}
}