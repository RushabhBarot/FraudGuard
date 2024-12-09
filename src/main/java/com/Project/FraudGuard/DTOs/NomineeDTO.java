package com.Project.FraudGuard.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NomineeDTO {
    private Long id;
    private String nomineeName;
    private String nomineeRelationship; // e.g., "Spouse", "Parent"
    private String nomineeGovernmentId; // e.g., Aadhaar, PAN
    private String nomineeGovernmentIdType; // e.g., "Aadhaar", "PAN"
    private String nomineePhoneNumber;
}
