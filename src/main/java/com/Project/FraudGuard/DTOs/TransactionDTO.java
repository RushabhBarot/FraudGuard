package com.Project.FraudGuard.DTOs;

import com.Project.FraudGuard.Entities.Enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private double amount;
    private Date timestamp;
    private TransactionType transactionType;

    private Long senderAccountId; // Account ID instead of Account Number
    private Long recipientAccountId; // Account ID instead of Account Number

    private String senderBank = "Bank1"; // Default bank for sender
    private String receiverBank; // Receiver's bank
    private String remarks;

    public double getAmount() {
        return amount;
    }
}
