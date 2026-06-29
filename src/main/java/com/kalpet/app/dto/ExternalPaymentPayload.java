package com.kalpet.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExternalPaymentPayload {
    private String walletCode;
    private String service;
    private BigDecimal amount;
    private String reference;
}