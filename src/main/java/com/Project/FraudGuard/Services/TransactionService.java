package com.Project.FraudGuard.Services;

import com.Project.FraudGuard.DTOs.TransactionDTO;
import com.Project.FraudGuard.DTOs.UserDTO;
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

    // Create a transaction using account numbers
    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        try {
            // Fetch the sender and recipient accounts using account numbers
            BankAccountEntity sender = bankAccountRepository.findByAccountNumber(transactionDTO.getSenderAccountNumber())
                    .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));
            BankAccountEntity recipient = bankAccountRepository.findByAccountNumber(transactionDTO.getRecipientAccountNumber())
                    .orElseThrow(() -> new IllegalArgumentException("Recipient account not found"));

            // Ensure the sender has enough balance
            if (sender.getBalance() < transactionDTO.getAmount()) {
                throw new IllegalArgumentException("Insufficient funds in sender's account");
            }

            // Update the balances
            sender.setBalance(sender.getBalance() - transactionDTO.getAmount());
            recipient.setBalance(recipient.getBalance() + transactionDTO.getAmount());
            bankAccountRepository.save(sender);
            bankAccountRepository.save(recipient);

            // Create the transaction entity
            TransactionEntity transaction = mapper.map(transactionDTO, TransactionEntity.class);
            transaction.setSenderAccount(sender);
            transaction.setRecipientAccount(recipient);

            // Save the transaction
            TransactionEntity savedTransaction = transactionRepository.save(transaction);

            // Map to TransactionDTO and include sender details
            TransactionDTO transactionResponse = mapper.map(savedTransaction, TransactionDTO.class);
            transactionResponse.setSenderUserData(mapper.map(sender.getUser(), UserDTO.class)); // Include sender user data

            return transactionResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Get all transactions globally
    public List<TransactionDTO> getAllTransactions() {
        // Fetch all transactions
        List<TransactionEntity> transactions = transactionRepository.findAll();

        // Map to TransactionDTOs and include sender's user data
        return transactions.stream()
                .map(transaction -> {
                    TransactionDTO transactionDTO = mapper.map(transaction, TransactionDTO.class);
                    transactionDTO.setSenderUserData(mapper.map(transaction.getSenderAccount().getUser(), UserDTO.class)); // Include sender user data
                    return transactionDTO;
                })
                .collect(Collectors.toList());
    }

    // Get sent transactions for a specific account by account number
    public List<TransactionDTO> getSentTransactions(String accountNumber) {
        BankAccountEntity account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        return account.getSentTransactions()
                .stream()
                .map(transaction -> {
                    TransactionDTO transactionDTO = mapper.map(transaction, TransactionDTO.class);
                    transactionDTO.setSenderUserData(mapper.map(transaction.getSenderAccount().getUser(), UserDTO.class)); // Include sender user data
                    return transactionDTO;
                })
                .collect(Collectors.toList());
    }

    // Get received transactions for a specific account by account number
    public List<TransactionDTO> getReceivedTransactions(String accountNumber) {
        BankAccountEntity account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        return account.getReceivedTransactions()
                .stream()
                .map(transaction -> {
                    TransactionDTO transactionDTO = mapper.map(transaction, TransactionDTO.class);
                    transactionDTO.setSenderUserData(mapper.map(transaction.getSenderAccount().getUser(), UserDTO.class)); // Include sender user data
                    return transactionDTO;
                })
                .collect(Collectors.toList());
    }

    // Get a specific transaction by ID
    public Optional<TransactionDTO> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transaction -> {
                    TransactionDTO transactionDTO = mapper.map(transaction, TransactionDTO.class);
                    transactionDTO.setSenderUserData(mapper.map(transaction.getSenderAccount().getUser(), UserDTO.class)); // Include sender user data
                    return transactionDTO;
                });
    }
}
