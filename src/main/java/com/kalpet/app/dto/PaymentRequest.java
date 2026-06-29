package com.kalpet.app.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private String phoneNumber;
    private String serviceName;
    private BigDecimal amount;
}