package com.kalpet.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kalpet.app.service.WalletSeederService;
import java.util.Map;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletSeederController {

    private final WalletSeederService walletSeederService;

    @PostMapping("/seed")
    public ResponseEntity<?> seed(@RequestParam(defaultValue = "10") int numWallets,
                                  @RequestParam(defaultValue = "100") int eventsPerWallet) {
        
        walletSeederService.seedDatabase(numWallets, eventsPerWallet);
        
        return ResponseEntity.accepted().body(Map.of(
            "message", "Le chargement des données a été lancé de manière asynchrone.",
            "status", "Processing",
            "numWallets", numWallets,
            "eventsPerWallet", eventsPerWallet
        ));
    }
}