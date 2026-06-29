package com.kalpet.app.dto;

import lombok.Data;
import java.util.List;

@Data
public class BulkPaymentRequest {
    private String phoneNumber;
    private String serviceName;
    private List<String> factureReferences;
}