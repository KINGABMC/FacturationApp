package com.kalpet.app.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String senderPhone;
    private String receiverPhone;
    private BigDecimal amount;
}