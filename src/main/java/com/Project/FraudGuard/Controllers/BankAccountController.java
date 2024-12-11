package com.Project.FraudGuard.Controllers;

import com.Project.FraudGuard.DTOs.BankAccountDTO;
import com.Project.FraudGuard.DTOs.TransactionDTO;
import com.Project.FraudGuard.Services.BankAccountService;
import com.Project.FraudGuard.Services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/account")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    // Endpoint to create or update a bank account
    @PostMapping("/post")
    public ResponseEntity<BankAccountDTO> createBankAccount(@RequestBody BankAccountDTO bankAccountDTO) {
        BankAccountDTO savedBankAccount = bankAccountService.saveBankAccount(bankAccountDTO);
        return new ResponseEntity<>(savedBankAccount, HttpStatus.CREATED);
    }

    // Endpoint to get a bank account by account number
    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountDTO> getBankAccount(@PathVariable String accountNumber) {
        Optional<BankAccountDTO> bankAccount = bankAccountService.getBankAccountByNumber(accountNumber);
        return bankAccount.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to get all sent transactions for a bank account
    @GetMapping("/{accountNumber}/sent-transactions")
    public ResponseEntity<List<TransactionDTO>> getSentTransactions(@PathVariable String accountNumber) {
        List<TransactionDTO> sentTransactions = transactionService.getSentTransactions(accountNumber);
        return ResponseEntity.ok(sentTransactions);
    }

    // Endpoint to get all received transactions for a bank account
    @GetMapping("/{accountNumber}/received-transactions")
    public ResponseEntity<List<TransactionDTO>> getReceivedTransactions(@PathVariable String accountNumber) {
        List<TransactionDTO> receivedTransactions = transactionService.getReceivedTransactions(accountNumber);
        return ResponseEntity.ok(receivedTransactions);
    }
}
