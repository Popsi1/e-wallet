package com.example.walletapp.repositories;

import com.example.walletapp.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  WalletRepository extends JpaRepository<Wallet,Long> {
}
