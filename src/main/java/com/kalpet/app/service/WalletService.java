package com.kalpet.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kalpet.app.dto.WalletCreationRequest;
import com.kalpet.app.model.Wallet;
import com.kalpet.app.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    public Wallet createWallet(WalletCreationRequest request) {
        // Règle de gestion : Unicité du numéro de téléphone
        if (walletRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Un portefeuille avec ce numéro de téléphone existe déjà.");
        }

        // Design Pattern Builder pour instancier proprement notre entité
        Wallet wallet = Wallet.builder()
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .balance(request.getInitialBalance() != null ? request.getInitialBalance() : BigDecimal.ZERO)
                .code(request.getCode())
                .currency(request.getCurrency() != null ? request.getCurrency() : "XOF")
                .build();

        return walletRepository.save(wallet);
    }

    public Page<Wallet> getAllWallets(Pageable pageable) {
        return walletRepository.findAll(pageable);
    }

    public Optional<Wallet> getWalletByPhone(String phoneNumber) {
        return walletRepository.findByPhoneNumber(phoneNumber);
    }

    public BigDecimal getBalanceByPhone(String phoneNumber) {
        return walletRepository.findByPhoneNumber(phoneNumber)
                .map(Wallet::getBalance)
                .orElseThrow(() -> new RuntimeException("Portefeuille introuvable pour ce numéro."));
    }
}