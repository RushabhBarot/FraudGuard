package com.Project.FraudGuard.DTOs;

import com.Project.FraudGuard.Entities.Enums.GovernmentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String governmentId; // PAN or Aadhaar
    private GovernmentId governmentIdType; // Enum representing the type (PAN, Aadhaar)
    private String address;

    // List of IDs representing bank accounts associated with this user
    private List<Long> accountId;
}
