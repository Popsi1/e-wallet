package com.example.walletapp.repositories;

import com.example.walletapp.models.KycUltimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycUltimateRepository extends JpaRepository<KycUltimate, Long> {
}
