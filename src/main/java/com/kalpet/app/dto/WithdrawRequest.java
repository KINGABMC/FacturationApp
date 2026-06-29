package com.kalpet.app.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class WithdrawRequest {
    private String phoneNumber;
    private BigDecimal amount;
}