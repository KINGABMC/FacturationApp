package com.kalpet.app.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class WalletCreationRequest {
    private String phoneNumber;
    private String email;
    private BigDecimal initialBalance;
    private String code;
    private String currency;
}