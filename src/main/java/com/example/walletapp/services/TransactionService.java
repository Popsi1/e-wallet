package com.example.walletapp.services;

import com.example.walletapp.constant.Constants;
import com.example.walletapp.enums.TransactionType;
import com.example.walletapp.exceptions.AccountNumberNotAssociatedWithWalletException;
import com.example.walletapp.exceptions.InsufficientBalanceInWalletException;
import com.example.walletapp.exceptions.UserDoesNotExistException;
import com.example.walletapp.exceptions.WalletIdDoesNotExistException;
import com.example.walletapp.models.Transaction;
import com.example.walletapp.models.Wallet;
import com.example.walletapp.models.WalletUser;
import com.example.walletapp.repositories.TransactionRepository;
import com.example.walletapp.repositories.WalletRepository;
import com.example.walletapp.repositories.WalletUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletUserRepository walletUserRepository;

    @Transactional
    public Transaction withdrawOrTopUpWallet(Long walletUserId, double amount, String type) {

        Transaction transaction = new Transaction();

        WalletUser walletUser = walletUserRepository.findById(walletUserId).orElse(null);

        if (walletUser == null) {
            throw new UserDoesNotExistException(walletUserId);
        }

        if (walletUser.getWallet()==null) {
            throw new WalletIdDoesNotExistException(walletUser.getWallet().getId());
        }

        if (walletUser.getWallet().getAccountNumber().isEmpty()){
            throw new AccountNumberNotAssociatedWithWalletException(walletUser.getWallet().getId());
        }

        if(type.equals(Constants.WITHDRAW)) {

            if (walletUser.getWallet().getBalance() < amount) {
                throw new InsufficientBalanceInWalletException(walletUser.getWallet().getId());
            }

            double currentBalance = walletUser.getWallet().getBalance() - amount;
            double postBalance = currentBalance - amount;
            walletUser.getWallet().setBalance(currentBalance);

            transaction.setAmount(amount);
            transaction.setDescription(Constants.WITHDRAW_DESCRIPTION);
            transaction.setPostBalance(postBalance);
            transaction.setType(TransactionType.WITHDRAWALS);
            transaction.setWallet(walletUser.getWallet());
        }

        if(type.equals(Constants.DEPOSIT)) {

            double currentBalance = walletUser.getWallet().getBalance() + amount;
            double postBalance = currentBalance - amount;
            walletUser.getWallet().setBalance(currentBalance);

            transaction.setAmount(amount);
            transaction.setDescription(Constants.DEPOSIT_DESCRIPTION);
            transaction.setPostBalance(postBalance);
            transaction.setType(TransactionType.DEPOSIT);
            transaction.setWallet(walletUser.getWallet());
        }

        walletUser.getWallet().getTransaction().add(transaction);
        walletUserRepository.save(walletUser);
        transactionRepository.save(transaction);

        return transaction;
    }

    public String transferFromOneWalletUserToAnotherWalletUser (Long walletUserSenderId, Long walletUserReceiverId, double amount) {

        Transaction withdrawalTransaction = withdrawOrTopUpWallet(walletUserSenderId, amount, Constants.WITHDRAW);
        Transaction depositTransaction = withdrawOrTopUpWallet(walletUserReceiverId, amount, Constants.DEPOSIT);

        return Constants.TRANSFER;
    }


    @Transactional
    public String transferFromWalletByEmailAndAccountNumber(String emailOfWalletUserReceiver, String accountNumberOfWalletUserReceiver, Long walletUserSenderId, double amount, String type){

        Transaction transactionForWalletUserSender = new Transaction();
        Transaction transactionForWalletReceiver = new Transaction();

        WalletUser walletUserReceiver = walletUserRepository.findWalletUserByEmail(emailOfWalletUserReceiver);
        WalletUser walletUserSender = walletUserRepository.findById(walletUserSenderId).orElse(null);


        if (walletUserReceiver == null || walletUserSender == null) {
            throw new UserDoesNotExistException(walletUserReceiver==null ? walletUserReceiver.getId() : walletUserSender.getId());
        }

        if (walletUserReceiver.getWallet() == null || walletUserSender.getWallet() == null) {
            throw new WalletIdDoesNotExistException(walletUserReceiver.getWallet()==null ? walletUserReceiver.getWallet().getId() : walletUserSender.getWallet().getId());
        }

        if (walletUserReceiver.getWallet().getAccountNumber().isEmpty() || walletUserSender.getWallet().getAccountNumber().isEmpty()){
            throw new AccountNumberNotAssociatedWithWalletException(walletUserReceiver.getWallet().getAccountNumber().isEmpty() ? walletUserReceiver.getWallet().getId() : walletUserSender.getWallet().getId());
        }


        if (walletUserReceiver.getWallet().getAccountNumber().equals(accountNumberOfWalletUserReceiver)){

                if (walletUserSender.getWallet().getBalance() < amount) {
                    throw new InsufficientBalanceInWalletException(walletUserSender.getWallet().getId());
                }

                double walletUserSenderCurrentBalance = walletUserSender.getWallet().getBalance() - amount;
                double walletUserSenderPostBalance = walletUserSenderCurrentBalance - amount;
                walletUserSender.getWallet().setBalance(walletUserSenderCurrentBalance);

                transactionForWalletUserSender.setAmount(amount);
                transactionForWalletUserSender.setDescription(Constants.WITHDRAW_DESCRIPTION);
                transactionForWalletUserSender.setPostBalance(walletUserSenderPostBalance);
                transactionForWalletUserSender.setType(TransactionType.WITHDRAWALS);
                transactionForWalletUserSender.setWallet(walletUserSender.getWallet());

                walletUserSender.getWallet().getTransaction().add(transactionForWalletUserSender);
                walletUserRepository.save( walletUserSender);
                transactionRepository.save(transactionForWalletUserSender);


                double walletUserReceiverCurrentBalance = walletUserReceiver.getWallet().getBalance() + amount;
                double walletUserReceiverPostBalance = walletUserReceiverCurrentBalance - amount;
                walletUserReceiver.getWallet().setBalance(walletUserReceiverCurrentBalance);

                transactionForWalletReceiver.setAmount(amount);
                transactionForWalletReceiver.setDescription(Constants.DEPOSIT_DESCRIPTION);
                transactionForWalletReceiver.setPostBalance(walletUserReceiverPostBalance);
                transactionForWalletReceiver.setType(TransactionType.DEPOSIT);
                transactionForWalletReceiver.setWallet(walletUserReceiver.getWallet());

                walletUserReceiver.getWallet().getTransaction().add(transactionForWalletReceiver);
                walletUserRepository.save(walletUserReceiver);
                transactionRepository.save(transactionForWalletReceiver);
        }

        return Constants.TRANSFER;
    }

}
