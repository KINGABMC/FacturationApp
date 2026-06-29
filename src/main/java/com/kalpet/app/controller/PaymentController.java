package com.kalpet.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kalpet.app.dto.PaymentRequest;
import com.kalpet.app.dto.BulkPaymentRequest;
import com.kalpet.app.model.Transaction;
import com.kalpet.app.service.PaymentService;

import java.util.Map;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // Endpoint 1.9 : Payer une facture du mois en cours
    @PostMapping("/pay")
    public ResponseEntity<Transaction> payInvoice(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.payInvoice(request));
    }

    // Endpoint 1.10 : Payer des factures spécifiques
    @PostMapping("/pay-factures")
    public ResponseEntity<Map<String, String>> payInvoices(@RequestBody BulkPaymentRequest request) {
        paymentService.payMultipleInvoices(request);
        return ResponseEntity.ok(Map.of(
                "status", "SUCCESS", 
                "message", "Toutes les factures du lot ont été réglées avec succès."
        ));
    }
}