package com.Project.FraudGuard.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private Date timestamp;
    private String transactionType;  // "TRANSFER", "DEPOSIT", "WITHDRAWAL"
    private String senderAccountId;  // Sender's account number
    private String recipientAccountId;  // Recipient's account number

    @ManyToOne
    @JoinColumn(name = "sender_account_id", referencedColumnName = "accountNumber", insertable = false, updatable = false)
    private BankAccountEntity senderAccount;  // Sender's bank account

    @ManyToOne
    @JoinColumn(name = "recipient_account_id", referencedColumnName = "accountNumber", insertable = false, updatable = false)
    private BankAccountEntity recipientAccount;  // Recipient's bank account
}
