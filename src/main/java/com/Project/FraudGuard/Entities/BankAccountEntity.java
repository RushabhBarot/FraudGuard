package com.Project.FraudGuard.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;  // Unique account number (e.g., "ACC123456")

    private String accountHolderName;
    private String accountType; // "Personal" or "Business"
    private double balance;

    // Personal Account fields (will be null for Business accounts)
    private String governmentId; // PAN or Aadhaar (For Personal accounts only)
    private String governmentIdType; // "PAN" or "Aadhaar" (For Personal accounts only)

    // Business Account fields (filled for Business accounts)
    private String businessRegistrationNumber; // GST or Company Registration Number (For Business accounts)
    private String authorizedRepresentativeName; // Name of the authorized representative (For Business accounts)
    private String authorizedRepresentativeId; // PAN or Aadhaar of authorized representative (For Business accounts)
    private String businessType; // LLC, Corporation, etc. (For Business accounts)
    private String businessAddress; // Address of the business (For Business accounts)

    private Date creationDate;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<NomineeEntity> nominees;

    @OneToMany(mappedBy = "senderAccount", cascade = CascadeType.ALL)
    private List<TransactionEntity> sentTransactions;  // List of transactions initiated by this account

    @OneToMany(mappedBy = "recipientAccount", cascade = CascadeType.ALL)
    private List<TransactionEntity> receivedTransactions;  // List of transactions received by this account
}
