package com.Project.FraudGuard.Controllers;

import com.Project.FraudGuard.DTOs.BankAccountDTO;
import com.Project.FraudGuard.DTOs.UserDTO;
import com.Project.FraudGuard.Services.UserService;
import com.Project.FraudGuard.Services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BankAccountService bankAccountService;

    // Endpoint to create or update a user
    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Endpoint to get a user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        Optional<UserDTO> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to get all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Endpoint to add a nominee to a bank account
    @PostMapping("/accounts/{accountNumber}/nominees/{nomineeId}")
    public ResponseEntity<BankAccountDTO> addNominee(@PathVariable String accountNumber, @PathVariable Long nomineeId) {
        BankAccountDTO updatedAccount = bankAccountService.addNominee(accountNumber, nomineeId);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    // Endpoint to remove a nominee from a bank account
    @DeleteMapping("/accounts/{accountNumber}/nominees/{nomineeId}")
    public ResponseEntity<BankAccountDTO> removeNominee(@PathVariable String accountNumber, @PathVariable Long nomineeId) {
        BankAccountDTO updatedAccount = bankAccountService.removeNominee(accountNumber, nomineeId);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

}
