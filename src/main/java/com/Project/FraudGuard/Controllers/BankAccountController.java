package com.Project.FraudGuard.Controllers;

import com.Project.FraudGuard.DTOs.BankAccountDTO;
import com.Project.FraudGuard.DTOs.NomineeDTO;
import com.Project.FraudGuard.DTOs.TransactionDTO;
import com.Project.FraudGuard.Services.BankAccountService;
import com.Project.FraudGuard.Services.NomineeService;
import com.Project.FraudGuard.Services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/account")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;
    private final NomineeService nomineeService;


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


//    // Endpoint to get all transactions for a bank account
//    @GetMapping("/{accountNumber}/transactions")
//    public ResponseEntity<List<TransactionDTO>> getTransactions(@PathVariable String accountNumber) {
//        List<TransactionDTO> transactions = transactionService.getTransactions(accountNumber);
//        return new ResponseEntity<>(transactions, HttpStatus.OK);
//    }
//
//    // Endpoint to add a nominee to a bank account
//    @PostMapping("/{accountNumber}/nominees")
//    public ResponseEntity<NomineeDTO> addNominee(@PathVariable String accountNumber, @RequestBody NomineeDTO nomineeDTO) {
//        NomineeDTO savedNominee = nomineeService.addNominee(nomineeDTO);
//        return new ResponseEntity<>(savedNominee, HttpStatus.CREATED);
//    }
}
