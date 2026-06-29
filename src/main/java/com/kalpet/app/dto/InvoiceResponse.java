package com.kalpet.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {
    private String reference;
    private String serviceName; // ex: WOYAFAL, ISM
    private BigDecimal amount;
    private LocalDate dueDate;
    private boolean isPaid;
}