package com.kalpet.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kalpet.app.model.Transaction;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletPhoneNumber(String phoneNumber);
    
    // Pagination support
    Page<Transaction> findByWalletPhoneNumber(String phoneNumber, Pageable pageable);
}