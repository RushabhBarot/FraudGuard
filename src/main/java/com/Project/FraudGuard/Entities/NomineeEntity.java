package com.Project.FraudGuard.Entities;

import com.Project.FraudGuard.Entities.Enums.GovernmentId;
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
    private String nomineeGovernmentId;// e.g., Aadhaar, PAN

    @Enumerated(EnumType.STRING)
    private GovernmentId nomineeGovernmentIdType; // e.g., "Aadhaar", "PAN"
    private String nomineePhoneNumber;

    // Foreign Key relationship to BankAccount
    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccountEntity bankAccount;
}
