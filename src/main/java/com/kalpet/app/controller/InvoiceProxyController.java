package com.kalpet.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kalpet.app.dto.InvoiceResponse;
import com.kalpet.app.service.InvoiceProxyService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/external/factures")
@RequiredArgsConstructor
public class InvoiceProxyController {

    private final InvoiceProxyService invoiceProxyService;

    // Endpoints 2.2 et 2.3 : Consulter les factures impayées du mois en cours (filtrées ou non)
    @GetMapping("/{walletCode}/current")
    public ResponseEntity<List<InvoiceResponse>> getCurrentMonthInvoices(
            @PathVariable String walletCode,
            @RequestParam(required = false) String unite) {
        
        List<InvoiceResponse> invoices = invoiceProxyService.getCurrentMonthUnpaidInvoices(walletCode, unite);
        return ResponseEntity.ok(invoices);
    }

    // Endpoint 2.4 : Consulter les factures impayées sur une période
    @GetMapping("/{walletCode}/periode")
    public ResponseEntity<List<InvoiceResponse>> getInvoicesByPeriod(
            @PathVariable String walletCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        
        List<InvoiceResponse> invoices = invoiceProxyService.getInvoicesByPeriod(walletCode, debut, fin);
        return ResponseEntity.ok(invoices);
    }
}