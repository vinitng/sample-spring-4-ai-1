package com.example.demo.controller;

import com.example.demo.dto.AccountResponse;
import com.example.demo.dto.CreateCustomerRequest;
import com.example.demo.dto.CustomerResponse;
import com.example.demo.facade.BankingFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/banking")
public class BankingController {

    private final BankingFacade bankingFacade;

    public BankingController(BankingFacade bankingFacade) {
        this.bankingFacade = bankingFacade;
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bankingFacade.onboardCustomer(request));
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(bankingFacade.listAllCustomers());
    }

    @GetMapping("/customers/{externalRefId}/accounts")
    public ResponseEntity<List<AccountResponse>> getCustomerAccounts(@PathVariable String externalRefId) {
        return ResponseEntity.ok(bankingFacade.fetchCustomerAccounts(externalRefId));
    }

    @PutMapping("/customers/{externalRefId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable String externalRefId,
            @RequestBody CreateCustomerRequest request) {
        return ResponseEntity.ok(bankingFacade.modifyCustomer(externalRefId, request));
    }

    @DeleteMapping("/customers/{externalRefId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String externalRefId) {
        bankingFacade.removeCustomer(externalRefId);
        return ResponseEntity.noContent().build();
    }
}