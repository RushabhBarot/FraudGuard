package com.Project.FraudGuard.Repositories;

import com.Project.FraudGuard.Entities.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity,Long> {
    // Find a BankAccountEntity by account number
    Optional<BankAccountEntity> findByAccountNumber(String accountNumber);
    //Optional<BankAccountEntity> findBySenderAccountId(Long senderID);
    //Optional<BankAccountEntity> findByRecipientAccountId(Long receiveID);
    Optional<BankAccountEntity> findById(Long accountId);
}
