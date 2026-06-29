package com.kalpet.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kalpet.app.model.Wallet;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByPhoneNumber(String phoneNumber);
    Optional<Wallet> findByCode(String code);
}