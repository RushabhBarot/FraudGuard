package com.Project.FraudGuard.Services;

import com.Project.FraudGuard.DTOs.NomineeDTO;
import com.Project.FraudGuard.Entities.BankAccountEntity;
import com.Project.FraudGuard.Entities.NomineeEntity;
import com.Project.FraudGuard.Repositories.BankAccountRepository;
import com.Project.FraudGuard.Repositories.NomineeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NomineeService {

    private final NomineeRepository nomineeRepository;
    private final BankAccountRepository bankAccountRepository;
    private final ModelMapper mapper;

    public NomineeDTO addNominee(String accountNumber, NomineeDTO nomineeDTO) {
        BankAccountEntity account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        NomineeEntity nomineeEntity = mapper.map(nomineeDTO, NomineeEntity.class);
        nomineeEntity.setBankAccount(account);

        NomineeEntity savedNominee = nomineeRepository.save(nomineeEntity);
        return mapper.map(savedNominee, NomineeDTO.class);
    }

    public List<NomineeDTO> getNominees(String accountNumber) {
        BankAccountEntity account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        return account.getNominees().stream()
                .map(nominee -> mapper.map(nominee, NomineeDTO.class))
                .collect(Collectors.toList());
    }
}
