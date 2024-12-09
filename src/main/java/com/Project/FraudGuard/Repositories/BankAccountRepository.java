package com.Project.FraudGuard.Repositories;

import com.Project.FraudGuard.Entities.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity,Long> {
}
