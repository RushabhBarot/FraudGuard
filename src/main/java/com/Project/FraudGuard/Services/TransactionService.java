package com.Project.FraudGuard.Services;

import com.Project.FraudGuard.DTOs.TransactionDTO;
import com.Project.FraudGuard.Entities.BankAccountEntity;
import com.Project.FraudGuard.Entities.TransactionEntity;
import com.Project.FraudGuard.Repositories.BankAccountRepository;
import com.Project.FraudGuard.Repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final ModelMapper mapper;

    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
      try {  // Log incoming transaction details
          System.out.println("Creating transaction: " + transactionDTO);

          BankAccountEntity sender = bankAccountRepository.findById(transactionDTO.getSenderAccountId())
                  .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));
          System.out.println("Sender account found: " + sender);

          BankAccountEntity recipient = bankAccountRepository.findById(transactionDTO.getRecipientAccountId())
                  .orElseThrow(() -> new IllegalArgumentException("Recipient account not found"));
          System.out.println("Recipient account found: " + recipient);

          if (sender.getBalance() < transactionDTO.getAmount()) {
              throw new IllegalArgumentException("Insufficient funds in sender's account");
          }

          sender.setBalance(sender.getBalance() - transactionDTO.getAmount());
          recipient.setBalance(recipient.getBalance() + transactionDTO.getAmount());
          System.out.println("Sender's balance is..." + sender.getBalance());
          bankAccountRepository.save(sender);
          bankAccountRepository.save(recipient);

          TransactionEntity transaction = mapper.map(transactionDTO, TransactionEntity.class);
          transaction.setSenderAccount(sender);
          transaction.setRecipientAccount(recipient);

          TransactionEntity savedTransaction = transactionRepository.save(transaction);
          System.out.println("Transaction saved: " + savedTransaction);

          return mapper.map(savedTransaction, TransactionDTO.class);
      } catch (Exception e) {
          System.out.println(e);
          throw new RuntimeException(e);
      }
    }

    // Get all sent transactions for an account
    public List<TransactionDTO> getSentTransactions(Long accountId) {
        BankAccountEntity account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        return account.getSentTransactions()
                .stream()
                .map(transaction -> mapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());
    }

    // Get all received transactions for an account
    public List<TransactionDTO> getReceivedTransactions(Long accountId) {
        BankAccountEntity account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        return account.getReceivedTransactions()
                .stream()
                .map(transaction -> mapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());
    }

    // Get all transactions globally
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transaction -> mapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());
    }

    // Get a transaction by ID
    public Optional<TransactionDTO> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transaction -> mapper.map(transaction, TransactionDTO.class));
    }
}
