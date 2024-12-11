package com.Project.FraudGuard.DTOs;

import com.Project.FraudGuard.Entities.Enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDTO {

    private Long id; // Bank account ID (for referencing)

    private String accountNumber; // Unique account number

    private AccountType accountType; // "Personal" or "Business"

    private double balance; // Account balance

    // Fields specific to Business Accounts (if applicable)
    private String businessRegistrationNumber; // GST or Company Registration Number
    private String authorizedRepresentativeName; // Authorized representative name
    private String businessAddress; // Address of the business

    private Date creationDate; // Date when the account was created

    private Long userId; // ID of the account holder (UserEntity)

    private List<Long> nominees; // List of nominee IDs (instead of full user objects for better performance)

//    private List<Long> sentTransactionIds; // List of transaction IDs sent by this account
//
//    private List<Long> receivedTransactionIds; // List of transaction IDs received by this account
}
