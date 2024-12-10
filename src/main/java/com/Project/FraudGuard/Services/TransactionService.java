package com.Project.FraudGuard.Services;

import com.Project.FraudGuard.DTOs.TransactionDTO;
import com.Project.FraudGuard.Entities.BankAccountEntity;
import com.Project.FraudGuard.Entities.TransactionEntity;
import com.Project.FraudGuard.Repositories.BankAccountRepository;
import com.Project.FraudGuard.Repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final ModelMapper mapper;

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        BankAccountEntity sender = bankAccountRepository.findByAccountNumber(transactionDTO.getSenderAccountId())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        BankAccountEntity recipient = bankAccountRepository.findByAccountNumber(transactionDTO.getRecipientAccountId())
                .orElseThrow(() -> new RuntimeException("Recipient account not found"));

        TransactionEntity transactionEntity = mapper.map(transactionDTO, TransactionEntity.class);
        transactionEntity.setSenderAccount(sender);
        transactionEntity.setRecipientAccount(recipient);
        transactionEntity.setTimestamp(new Date());

        // Adjust balances
        sender.setBalance(sender.getBalance() - transactionEntity.getAmount());
        recipient.setBalance(recipient.getBalance() + transactionEntity.getAmount());

        transactionRepository.save(transactionEntity);
        bankAccountRepository.save(sender);
        bankAccountRepository.save(recipient);

        return mapper.map(transactionEntity, TransactionDTO.class);
    }

    public List<TransactionDTO> getTransactions(String accountNumber) {
        BankAccountEntity account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        return account.getSentTransactions().stream()
                .map(transaction -> mapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());
    }
}
