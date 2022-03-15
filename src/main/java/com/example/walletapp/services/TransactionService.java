package com.example.walletapp.services;

import com.example.walletapp.constant.Constants;
import com.example.walletapp.enums.KycLevel;
import com.example.walletapp.enums.TransactionType;
import com.example.walletapp.exceptions.AccountNumberNotAssociatedWithWalletException;
import com.example.walletapp.exceptions.InsufficientBalanceInWalletException;
import com.example.walletapp.exceptions.UserDoesNotExistException;
import com.example.walletapp.exceptions.WalletIdDoesNotExistException;
import com.example.walletapp.models.Transaction;
import com.example.walletapp.models.WalletUser;
import com.example.walletapp.repositories.TransactionRepository;
import com.example.walletapp.repositories.WalletUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletUserRepository walletUserRepository;

    public Transaction withdrawOrTopUpWallet(Long walletUserId, double amount, String transactionType) throws Exception {

        Transaction transaction = new Transaction();

        WalletUser walletUser = walletUserRepository.findWalletUserById(walletUserId);

        try {
            if (walletUser == null) {
                throw new UserDoesNotExistException(walletUserId);
            }

            if (walletUser.getWallet()==null) {
                throw new WalletIdDoesNotExistException(walletUser.getWallet().getId());
            }

            if (walletUser.getWallet().getAccountNumber().isEmpty()){
                throw new AccountNumberNotAssociatedWithWalletException(walletUser.getWallet().getId());
            }
        }catch (NullPointerException ex){

        }


        if(transactionType.equals("withdraw")) {

            if (walletUser.getWallet().getKycLevel() == null) {
                if(amount>=100 && amount<=10000) {
                    transaction = withdrawalTransaction(walletUser, amount, transaction);
                }else
                    throw new Exception("cannot withdraw");
            }

            if (walletUser.getWallet().getKycLevel() == KycLevel.Master) {
                if(amount>=100 && amount<=100000) {
                    transaction = withdrawalTransaction(walletUser, amount, transaction);
                }else
                    throw new Exception("cannot withdraw");
            }

            if (walletUser.getWallet().getKycLevel() == KycLevel.Ultimate) {
                if(amount>=100 && amount<=1000000) {
                    transaction = withdrawalTransaction(walletUser, amount, transaction);
                }else
                    throw new Exception("cannot withdraw");
            }
        }


        if(transactionType.equals("deposit")) {

            if (walletUser.getWallet().getKycLevel() == null) {

                if (amount >= 100 && amount <= 10000) {
                   transaction = depositTransaction(walletUser, amount, transaction);
                }else
                    throw new Exception("cannot deposit");
            }

            if (walletUser.getWallet().getKycLevel() == KycLevel.Master) {
                if(amount>=100 && amount<=100000) {
                    transaction = depositTransaction(walletUser, amount, transaction);
                }else
                    throw new Exception("cannot deposit");
            }

            if (walletUser.getWallet().getKycLevel() == KycLevel.Ultimate) {
                if(amount>=100 && amount<=1000000) {
                    transaction = depositTransaction(walletUser, amount, transaction);
                }else
                    throw new Exception("cannot deposit");
            }
        }

        return transactionRepository.save(transaction);
    }



    public Transaction withdrawalTransaction(WalletUser walletUser, double amount, Transaction transaction){
        if (walletUser.getWallet().getBalance() < amount) {
            System.out.println(walletUser.getEmail());
            throw new InsufficientBalanceInWalletException(walletUser.getWallet().getId());
        }
        double currentBalance = walletUser.getWallet().getBalance() - amount;
        double postBalance = currentBalance + amount;
        walletUser.getWallet().setBalance(currentBalance);

        transaction.setAmount(amount);
        transaction.setDescription(Constants.WITHDRAW_DESCRIPTION);
        transaction.setPostBalance(postBalance);
        transaction.setType(TransactionType.WITHDRAWALS);
        transaction.setWallet(walletUser.getWallet());

        return transaction;
    }



    public Transaction depositTransaction(WalletUser walletUser, double amount, Transaction transaction){

        double currentBalance = walletUser.getWallet().getBalance() + amount;
        double postBalance = currentBalance - amount;
        walletUser.getWallet().setBalance(currentBalance);

        transaction.setAmount(amount);
        transaction.setDescription(Constants.DEPOSIT_DESCRIPTION);
        transaction.setPostBalance(postBalance);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setWallet(walletUser.getWallet());

        return transaction;
    }



    public String transferFromOneWalletUserToAnotherWalletUser (Long walletUserSenderId, Long walletUserReceiverId, double amount) throws Exception {
        withdrawOrTopUpWallet(walletUserSenderId, amount, Constants.WITHDRAW);
        withdrawOrTopUpWallet(walletUserReceiverId, amount, Constants.DEPOSIT);

        return Constants.TRANSFER;
    }




    @Transactional
    public Transaction transferFromWalletByEmailAndAccountNumber(String emailOfWalletUserReceiver, String accountNumberOfWalletUserReceiver, Long walletUserSenderId, double amount) throws Exception {

        Transaction transactionForWalletUserSender = new Transaction();
        Transaction transactionForWalletReceiver = new Transaction();

        WalletUser walletUserReceiver = walletUserRepository.findWalletUserByEmail(emailOfWalletUserReceiver);
        WalletUser walletUserSender = walletUserRepository.findById(walletUserSenderId).orElse(null);

        try {

            if (walletUserReceiver == null || walletUserSender == null) {
                throw new UserDoesNotExistException(walletUserReceiver == null ? walletUserReceiver.getId() : walletUserSender.getId());
            }

            if (walletUserReceiver.getWallet() == null || walletUserSender.getWallet() == null) {
                throw new WalletIdDoesNotExistException(walletUserReceiver.getWallet() == null ? walletUserReceiver.getWallet().getId() : walletUserSender.getWallet().getId());
            }

            if (walletUserReceiver.getWallet().getAccountNumber().isEmpty() || walletUserSender.getWallet().getAccountNumber().isEmpty()) {
                throw new AccountNumberNotAssociatedWithWalletException(walletUserReceiver.getWallet().getAccountNumber().isEmpty() ? walletUserReceiver.getWallet().getId() : walletUserSender.getWallet().getId());
            }
        }catch (NullPointerException ex){

        }

        if (walletUserReceiver.getWallet().getAccountNumber().equals(accountNumberOfWalletUserReceiver)){
            if (walletUserSender.getWallet().getKycLevel() == null) {
                if (amount >= 100 && amount <= 10000) {
                    transactionForWalletUserSender = withdrawalTransaction(walletUserSender, amount, transactionForWalletUserSender);
                }else
                    throw new Exception("cannot transfer 1");
            }

            if (walletUserSender.getWallet().getKycLevel() == KycLevel.Master) {
                if (amount >= 100 && amount <= 100000) {
                    transactionForWalletUserSender = withdrawalTransaction(walletUserSender, amount, transactionForWalletUserSender);
                }else
                    throw new Exception("cannot transfer 2");
            }

            if (walletUserSender.getWallet().getKycLevel() == KycLevel.Ultimate) {
                if (amount >= 100 && amount <= 1000000) {
                    transactionForWalletUserSender = withdrawalTransaction(walletUserSender, amount, transactionForWalletUserSender);
                }else
                    throw new Exception("cannot transfer 3");
            }

            transactionRepository.save(transactionForWalletUserSender);

            if (walletUserSender.getWallet().getKycLevel() == null) {
                if (amount >= 100 && amount <= 10000) {
                    transactionForWalletReceiver = depositTransaction(walletUserReceiver, amount, transactionForWalletReceiver);
                }else
                    throw new Exception("cannot transfer 4");
            }

            if (walletUserSender.getWallet().getKycLevel() == KycLevel.Master) {
                if (amount >= 100 && amount <= 100000) {
                    transactionForWalletReceiver = depositTransaction(walletUserReceiver, amount, transactionForWalletReceiver);
                }throw new Exception("cannot transfer 5");
            }

            if (walletUserSender.getWallet().getKycLevel() == KycLevel.Ultimate) {
                if (amount >= 100 && amount <= 1000000) {
                    transactionForWalletReceiver = depositTransaction(walletUserReceiver, amount, transactionForWalletReceiver);
                }throw new Exception("cannot transfer 6");
            }

            transactionRepository.save(transactionForWalletReceiver);

        }

        return transactionForWalletReceiver;
    }

}
