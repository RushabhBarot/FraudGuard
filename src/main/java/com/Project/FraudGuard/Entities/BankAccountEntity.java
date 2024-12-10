package com.Project.FraudGuard.Entities;

import com.Project.FraudGuard.Entities.Enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "bank_account_entity")//, indexes = @Index(name = "idx_account_number", columnList = "accountNumber"))
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber; // Unique account number

    @Enumerated(EnumType.STRING)
    private AccountType accountType; // "Personal" or "Business"

    private double balance;

    // Fields specific to Business Accounts
    private String businessRegistrationNumber; // GST or Company Registration Number
    private String authorizedRepresentativeName; // Authorized representative name
    private String businessAddress; // Address of the business

    @CreationTimestamp
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; // Account holder reference

    @ManyToMany
    @JoinTable(
            name = "account_nominees",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "nominee_id")
    )
    private List<UserEntity> nominees; // Nominee information

    @OneToMany(mappedBy = "senderAccount", cascade = CascadeType.ALL)
    private List<TransactionEntity> sentTransactions; // Transactions sent by this account

    @OneToMany(mappedBy = "recipientAccount", cascade = CascadeType.ALL)
    private List<TransactionEntity> receivedTransactions; // Transactions received by this account

    @Override
    public String toString() {
        return "BankAccountEntity{id=" + id + ", accountNumber='" + accountNumber + "', ...}";
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
