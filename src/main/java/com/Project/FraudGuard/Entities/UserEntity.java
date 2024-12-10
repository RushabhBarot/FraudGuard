package com.Project.FraudGuard.Entities;

import com.Project.FraudGuard.Entities.Enums.GovernmentId;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "user_entity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;

    private String governmentId; // PAN or Aadhaar
    @Enumerated(EnumType.STRING)
    private GovernmentId governmentIdType; // "PAN" or "Aadhaar"

    private String address;

    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BankAccountEntity> accounts; // Accounts owned by this user

    @Override
    public String toString() {
        return "UserEntity{id=" + id + ", name='" + name + "', ...}";
    }
}

