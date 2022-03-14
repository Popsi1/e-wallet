package com.example.walletapp.repositories;

import com.example.walletapp.models.KycMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycMasterRepository extends JpaRepository<KycMaster, Long> {
}
