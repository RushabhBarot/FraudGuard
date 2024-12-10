package com.Project.FraudGuard.Entities;

import com.Project.FraudGuard.Entities.Enums.TransactionType;
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

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    private BankAccountEntity senderAccount;

    @ManyToOne
    @JoinColumn(name = "recipient_account_id")
    private BankAccountEntity recipientAccount;

    @Column(nullable = false)
    private String senderBank = "Bank1"; // Default bank for sender

    @Column(nullable = false)
    private String receiverBank;

    private String remarks;
}
