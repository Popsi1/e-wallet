package com.example.walletapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException(Long userId) {
        super("Wallet User with user ID:" + userId + " does not exist");}
}