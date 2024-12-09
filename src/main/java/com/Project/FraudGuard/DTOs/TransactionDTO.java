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
    private TransactionType transactionType;  // "TRANSFER", "DEPOSIT", "WITHDRAWAL"
    private String senderAccountId;  // Account number of the sender
    private String recipientAccountId;  // Account number of the recipient

}
