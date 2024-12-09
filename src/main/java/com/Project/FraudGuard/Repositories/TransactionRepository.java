package com.Project.FraudGuard.Repositories;

import com.Project.FraudGuard.Entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {
}
