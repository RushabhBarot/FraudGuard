package com.Project.FraudGuard.Controllers;

import com.Project.FraudGuard.DTOs.TransactionDTO;
import com.Project.FraudGuard.Services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // Endpoint to create a transaction
    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            TransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);
            return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Return error message for invalid data
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    // Endpoint to get all sent transactions for a specific account
    @GetMapping("/account/{accountNumber}/sent")
    public ResponseEntity<?> getSentTransactions(@PathVariable String accountNumber) {
        try {
            List<TransactionDTO> transactions = transactionService.getSentTransactions(accountNumber);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    // Endpoint to get all received transactions for a specific account
    @GetMapping("/account/{accountNumber}/received")
    public ResponseEntity<?> getReceivedTransactions(@PathVariable String accountNumber) {
        try {
            List<TransactionDTO> transactions = transactionService.getReceivedTransactions(accountNumber);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    // Endpoint to get all transactions globally
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        try {
            List<TransactionDTO> transactions = transactionService.getAllTransactions();
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint to get a specific transaction by ID
    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long transactionId) {
        try {
            return transactionService.getTransactionById(transactionId)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
