package com.example.walletapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class KycAlreadyExistException extends RuntimeException{
    public KycAlreadyExistException(Long kycId) {
        super("Wallet user with kycId : "+kycId+" exist");}
}