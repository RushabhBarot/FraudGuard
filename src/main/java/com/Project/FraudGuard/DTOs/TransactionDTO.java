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

    private String senderAccountNumber;  // Account number of sender
    private String recipientAccountNumber; // Account number of recipient

    private String senderBank = "Bank1"; // Default bank for sender
    private String receiverBank; // Receiver's bank
    private String remarks;

    private UserDTO senderUserData;  // Added to include sender user data only

    public double getAmount() {
        return amount;
    }
}
