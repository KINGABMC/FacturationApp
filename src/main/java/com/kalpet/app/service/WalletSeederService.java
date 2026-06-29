package com.kalpet.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kalpet.app.model.Wallet;
import com.kalpet.app.model.Transaction;
import com.kalpet.app.repository.WalletRepository;
import com.kalpet.app.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class WalletSeederService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final Random random = new Random();

    @Async
    @Transactional
    public CompletableFuture<Void> seedDatabase(int numWallets, int eventsPerWallet) {
        for (int i = 1; i <= numWallets; i++) {
            String phone = String.format("+2217700000%02d", i);
            String email = "client" + i + "@gmail.com";
            String code = String.format("WLT-%07d", i);
            
            Wallet wallet = Wallet.builder()
                    .phoneNumber(phone)
                    .email(email)
                    .balance(new BigDecimal("50000.00")) // Solde initial pour les tests
                    .code(code)
                    .currency("XOF")
                    .build();
            
            wallet = walletRepository.save(wallet);

            
            for (int j = 0; j < eventsPerWallet; j++) {
                boolean isDeposit = random.nextBoolean();
                BigDecimal amount = new BigDecimal(random.nextInt(5000) + 100);
                
                Transaction tx = Transaction.builder()
                        .type(isDeposit ? "DEPOSIT" : "WITHDRAWAL")
                        .amount(amount)
                        .fees(BigDecimal.ZERO)
                        .timestamp(LocalDateTime.now().minusDays(random.nextInt(30)))
                        .description(isDeposit ? "Dépôt initial automatique" : "Retrait automatique")
                        .wallet(wallet)
                        .build();
                
                transactionRepository.save(tx);
            }
        }
        return CompletableFuture.completedFuture(null);
    }
}