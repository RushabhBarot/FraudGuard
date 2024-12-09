package com.Project.FraudGuard.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {

    private String accountHolderName;
    private String accountType;
    private double balance;


    // Personal Account fields (only filled for Personal accounts)
    private String governmentId; // e.g., PAN or Aadhaar (For Personal accounts only)
    private String governmentIdType; // "PAN" or "Aadhaar" (For Personal accounts only)

    // Business Account fields (only filled for Business accounts)
    private String businessRegistrationNumber; // GST or Company Registration Number (For Business accounts)
    private String authorizedRepresentativeName; // Name of the authorized representative (For Business accounts)
    private String authorizedRepresentativeId; // PAN or Aadhaar of the representative (For Business accounts)
    private String businessType; // LLC, Corporation, etc. (For Business accounts)
    private String businessAddress; // Address of the business (For Business accounts)
}
