package com.Project.FraudGuard.Services;

import com.Project.FraudGuard.DTOs.BankAccountDTO;
import com.Project.FraudGuard.DTOs.NomineeDTO;
import com.Project.FraudGuard.DTOs.TransactionDTO;
import com.Project.FraudGuard.Entities.BankAccountEntity;
import com.Project.FraudGuard.Repositories.BankAccountRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public class BankAccountService {

    private final ModelMapper mapper;
    private final BankAccountRepository bankAccountRepository;
    private final NomineeService nomineeService;
    private final TransactionService transactionService;

    public BankAccountDTO saveBankAccount(BankAccountDTO bankAccountDTO) {
        BankAccountEntity entity = mapper.map(bankAccountDTO, BankAccountEntity.class);

        BankAccountEntity savedEntity = bankAccountRepository.save(entity);

        return mapper.map(savedEntity, BankAccountDTO.class);
    }

    public Optional<BankAccountDTO> getBankAccountByNumber(String accountNumber) {
        Optional<BankAccountEntity> account = bankAccountRepository.findByAccountNumber(accountNumber);
        if (account.isPresent()) {
            BankAccountEntity entity = account.get();
           return Optional.of(mapper.map(entity, BankAccountDTO.class));
             }
        return Optional.empty();
    }

//    // Get all transactions for a bank account
    //TODO CREATE TRANSACTION SERVICE
//    public List<TransactionDTO> getTransactions(String accountNumber) {
//        return transactionService.getTransactions(accountNumber);
//    }

//    // Add a nominee
//    public NomineeDTO addNominee(String accountNumber, NomineeDTO nomineeDTO) {
//        return nomineeService.addNominee(nomineeDTO);
//    }


}
