package com.kalpet.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kalpet.app.dto.WalletCreationRequest;
import com.kalpet.app.model.Wallet;
import com.kalpet.app.service.WalletService;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    // Endpoint 1.2 : Créer un nouveau portefeuille
    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody WalletCreationRequest request) {
        Wallet created = walletService.createWallet(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Endpoint 1.3 : Lister tous les portefeuilles (Pagination)
    @GetMapping
    public ResponseEntity<Page<Wallet>> listWallets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(walletService.getAllWallets(pageable));
    }

    // Endpoint 1.4 : Consulter un portefeuille par numéro de téléphone
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Wallet> getWallet(@PathVariable String phoneNumber) {
        return walletService.getWalletByPhone(phoneNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint 1.5 : Consulter uniquement le solde à jour
    @GetMapping("/{phoneNumber}/balance")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable String phoneNumber) {
        BigDecimal balance = walletService.getBalanceByPhone(phoneNumber);
        return ResponseEntity.ok(Map.of(
                "phoneNumber", phoneNumber,
                "balance", balance,
                "currency", "XOF"
        ));
    }
}