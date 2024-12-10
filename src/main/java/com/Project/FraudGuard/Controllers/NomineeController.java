package com.Project.FraudGuard.Controllers;

import com.Project.FraudGuard.DTOs.NomineeDTO;
import com.Project.FraudGuard.Services.NomineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nominee")
@RequiredArgsConstructor
public class NomineeController {

    private final NomineeService nomineeService;

    @PostMapping("/{accountNumber}/add")
    public ResponseEntity<NomineeDTO> addNominee(@PathVariable String accountNumber,
                                                 @RequestBody NomineeDTO nomineeDTO) {
        NomineeDTO savedNominee = nomineeService.addNominee(accountNumber, nomineeDTO);
        return new ResponseEntity<>(savedNominee, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<NomineeDTO>> getNominees(@PathVariable String accountNumber) {
        List<NomineeDTO> nominees = nomineeService.getNominees(accountNumber);
        return new ResponseEntity<>(nominees, HttpStatus.OK);
    }
}
