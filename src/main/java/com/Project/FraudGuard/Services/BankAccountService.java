package com.Project.FraudGuard.Services;

import com.Project.FraudGuard.DTOs.BankAccountDTO;
import com.Project.FraudGuard.Entities.BankAccountEntity;
import com.Project.FraudGuard.Entities.UserEntity;
import com.Project.FraudGuard.Repositories.BankAccountRepository;
import com.Project.FraudGuard.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountService {

    private final ModelMapper mapper;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    public BankAccountDTO saveBankAccount(BankAccountDTO bankAccountDTO) {
        Optional<UserEntity> accountHolder = userRepository.findById(bankAccountDTO.getUserId());
        UserEntity user;

        if (accountHolder.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + bankAccountDTO.getUserId());
        } else {
            user = accountHolder.get();
        }

        // Map DTO to Entity
        BankAccountEntity entity = mapper.map(bankAccountDTO, BankAccountEntity.class);
        entity.setUser(user);

        // Save BankAccountEntity
        BankAccountEntity savedEntity = bankAccountRepository.save(entity);

        // Update UserEntity's accountIds
        if (user.getAccountId() == null) {
            user.setAccountId(new ArrayList<>());
        }
        user.getAccountId().add(savedEntity.getId());
        userRepository.save(user);

        return mapper.map(savedEntity, BankAccountDTO.class);
    }

    // Get bank account by account number
    public Optional<BankAccountDTO> getBankAccountByNumber(String accountNumber) {
        Optional<BankAccountEntity> account = bankAccountRepository.findByAccountNumber(accountNumber);
        return account.map(entity -> mapper.map(entity, BankAccountDTO.class));
    }

    // Add a nominee to a bank account
    public BankAccountDTO addNominee(String accountNumber, Long nomineeId) {
        BankAccountEntity bankAccount = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));

        UserEntity nominee = userRepository.findById(nomineeId)
                .orElseThrow(() -> new IllegalArgumentException("Nominee not found"));

        if (!bankAccount.getNominees().contains(nominee)) {
            bankAccount.getNominees().add(nominee);
        }

        BankAccountEntity updatedAccount = bankAccountRepository.save(bankAccount);

        // Map nominees to their IDs manually
        List<Long> nomineeIds = updatedAccount.getNominees().stream()
                .map(UserEntity::getId)
                .collect(Collectors.toList());

        // Create a DTO and set the nominee IDs manually
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(updatedAccount.getId());
        bankAccountDTO.setAccountNumber(updatedAccount.getAccountNumber());
        bankAccountDTO.setAccountType(updatedAccount.getAccountType());
        bankAccountDTO.setBalance(updatedAccount.getBalance());
        bankAccountDTO.setCreationDate(updatedAccount.getCreationDate());
        bankAccountDTO.setUserId(updatedAccount.getUser().getId()); // Assuming the account holder's user ID is needed
        bankAccountDTO.setNominees(nomineeIds);  // Manually set the nominee IDs list


        return bankAccountDTO;
    }

    // Remove a nominee from a bank account
    public BankAccountDTO removeNominee(String accountNumber, Long nomineeId) {
        BankAccountEntity bankAccount = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));

        bankAccount.getNominees().removeIf(nominee -> nominee.getId().equals(nomineeId));

        BankAccountEntity updatedAccount = bankAccountRepository.save(bankAccount);
        return mapper.map(updatedAccount, BankAccountDTO.class);
    }

    // Get all nominees for a bank account
    public List<UserEntity> getNominees(String accountNumber) {
        BankAccountEntity bankAccount = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));

        return bankAccount.getNominees();
    }
}
