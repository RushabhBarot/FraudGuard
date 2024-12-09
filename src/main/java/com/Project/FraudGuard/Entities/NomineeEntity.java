package com.Project.FraudGuard.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NomineeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomineeName;
    private String nomineeRelationship; // e.g., "Spouse", "Parent"
    private String nomineeGovernmentId; // e.g., Aadhar, PAN
    private String nomineeGovernmentIdType; // e.g., "Aadhar", "PAN"
    private String nomineePhoneNumber;

    // Foreign Key relationship to BankAccount
    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccountEntity bankAccount;
}
