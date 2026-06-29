package com.kalpet.app.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DepositRequest {
    private BigDecimal amount;
    private String paymentMethod;
}