package com.kalpet.app.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String type; // DEPOSIT, WITHDRAWAL, TRANSFER_SEND, TRANSFER_RECEIVE, PAYMENT
    private BigDecimal amount;
    private BigDecimal fees;
    private LocalDateTime timestamp;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}