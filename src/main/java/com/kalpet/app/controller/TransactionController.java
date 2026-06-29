package com.kalpet.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kalpet.app.dto.DepositRequest;
import com.kalpet.app.dto.WithdrawRequest;
import com.kalpet.app.dto.TransferRequest;
import com.kalpet.app.model.Transaction;
import com.kalpet.app.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // Endpoint 1.6 : Effectuer un Dépôt
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Transaction> deposit(@PathVariable Long id, @RequestBody DepositRequest request) {
        return ResponseEntity.ok(transactionService.deposit(id, request.getAmount(), request.getPaymentMethod()));
    }

    // Endpoint 1.7 : Effectuer un Retrait (avec frais)
    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestBody WithdrawRequest request) {
        return ResponseEntity.ok(transactionService.withdraw(request.getPhoneNumber(), request.getAmount()));
    }

    // Endpoint 1.8 : Effectuer un Transfert
    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestBody TransferRequest request) {
        return ResponseEntity.ok(transactionService.transfer(
                request.getSenderPhone(), request.getReceiverPhone(), request.getAmount()));
    }

    // Endpoint 1.11 : Consulter l'historique des transactions d'un portefeuille
    @GetMapping("/{phoneNumber}/transactions")
    public ResponseEntity<List<Transaction>> getHistory(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(transactionService.getHistory(phoneNumber));
    }
}