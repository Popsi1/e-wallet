package com.example.walletapp.repositories;

import com.example.walletapp.models.WalletUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletUserRepository extends JpaRepository<WalletUser, Long> {
    WalletUser findWalletUserByEmail(String email);
}
