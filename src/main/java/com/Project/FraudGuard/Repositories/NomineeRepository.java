package com.Project.FraudGuard.Repositories;

import com.Project.FraudGuard.Entities.NomineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomineeRepository extends JpaRepository<NomineeEntity,Long> {
}
