package com.kalpet.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kalpet.app.model.Wallet;
import com.kalpet.app.model.Transaction;
import com.kalpet.app.repository.WalletRepository;
import com.kalpet.app.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    // Endpoint 1.6 : Effectuer un Dépôt par ID de Wallet
    @Transactional
    public Transaction deposit(Long walletId, BigDecimal amount, String method) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Portefeuille introuvable avec l'ID: " + walletId));

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        Transaction tx = Transaction.builder()
                .type("DEPOSIT")
                .amount(amount)
                .fees(BigDecimal.ZERO)
                .timestamp(LocalDateTime.now())
                .description("Dépôt via " + (method != null ? method : "Guichet"))
                .wallet(wallet)
                .build();

        return transactionRepository.save(tx);
    }

    // Endpoint 1.7 : Effectuer un Retrait (Frais 1% plafonnés à 5000 CFA)
    @Transactional
    public Transaction withdraw(String phoneNumber, BigDecimal amount) {
        Wallet wallet = walletRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Portefeuille introuvable pour ce numéro."));

        // Calcul des frais : montant * 0.01 (1%)
        BigDecimal fees = amount.multiply(new BigDecimal("0.01"));
        
        // Application du plafond de 5000 CFA
        if (fees.compareTo(new BigDecimal("5000")) > 0) {
            fees = new BigDecimal("5000");
        }

        BigDecimal totalDeduction = amount.add(fees);

        // Règle de gestion : vérifier si le solde couvre le montant + les frais
        if (wallet.getBalance().compareTo(totalDeduction) < 0) {
            throw new RuntimeException("Solde insuffisant pour effectuer le retrait de " + amount + " avec " + fees + " CFA de frais.");
        }

        wallet.setBalance(wallet.getBalance().subtract(totalDeduction));
        walletRepository.save(wallet);

        Transaction tx = Transaction.builder()
                .type("WITHDRAWAL")
                .amount(amount)
                .fees(fees)
                .timestamp(LocalDateTime.now())
                .description("Retrait au guichet automatique")
                .wallet(wallet)
                .build();

        return transactionRepository.save(tx);
    }

    // Endpoint 1.8 : Effectuer un Transfert de compte à compte
    @Transactional
    public Transaction transfer(String senderPhone, String receiverPhone, BigDecimal amount) {
        if (senderPhone.equals(receiverPhone)) {
            throw new RuntimeException("Opération impossible : les comptes émetteur et récepteur sont identiques.");
        }

        Wallet sender = walletRepository.findByPhoneNumber(senderPhone)
                .orElseThrow(() -> new RuntimeException("Portefeuille émetteur introuvable."));
        Wallet receiver = walletRepository.findByPhoneNumber(receiverPhone)
                .orElseThrow(() -> new RuntimeException("Portefeuille récepteur introuvable."));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Solde insuffisant pour effectuer le transfert.");
        }

        // Débit émetteur
        sender.setBalance(sender.getBalance().subtract(amount));
        walletRepository.save(sender);

        // Crédit récepteur
        receiver.setBalance(receiver.getBalance().add(amount));
        walletRepository.save(receiver);

        // Historique pour l'émetteur
        Transaction txSender = Transaction.builder()
                .type("TRANSFER_SEND")
                .amount(amount)
                .fees(BigDecimal.ZERO)
                .timestamp(LocalDateTime.now())
                .description("Transfert envoyé à " + receiverPhone)
                .wallet(sender)
                .build();
        transactionRepository.save(txSender);

        // Historique pour le récepteur
        Transaction txReceiver = Transaction.builder()
                .type("TRANSFER_RECEIVE")
                .amount(amount)
                .fees(BigDecimal.ZERO)
                .timestamp(LocalDateTime.now())
                .description("Transfert reçu de " + senderPhone)
                .wallet(receiver)
                .build();
        transactionRepository.save(txReceiver);

        return txSender;
    }

    public List<Transaction> getHistory(String phoneNumber) {
        return transactionRepository.findByWalletPhoneNumber(phoneNumber);
    }
}