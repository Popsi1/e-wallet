package com.example.walletapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class KycUltimateDoesNotExistException extends RuntimeException{
    public KycUltimateDoesNotExistException(Long walletId) {
        super("Wallet with walletId : "+walletId+" does not have kycUltimate");}
}