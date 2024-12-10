package com.Project.FraudGuard.Controllers;

import com.Project.FraudGuard.DTOs.TransactionDTO;
import com.Project.FraudGuard.Services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionDTO savedTransaction = transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> getTransactions(@PathVariable String accountNumber) {
        List<TransactionDTO> transactions = transactionService.getTransactions(accountNumber);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
