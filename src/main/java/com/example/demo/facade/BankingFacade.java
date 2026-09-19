package com.example.demo.facade;

import com.example.demo.dto.AccountResponse;
import com.example.demo.dto.CreateCustomerRequest;
import com.example.demo.dto.CustomerResponse;
import com.example.demo.model.Customer;
import com.example.demo.service.BankingService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankingFacade {

    private final BankingService bankingService;

    public BankingFacade(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    public CustomerResponse onboardCustomer(CreateCustomerRequest request) {
        Customer customer = bankingService.registerCustomerWithAccount(
                request.externalRefId(),
                request.fullName(),
                request.email(),
                request.accountNumber(),
                request.initialBalance()
        );
        return CustomerResponse.fromEntity(customer);
    }

    public List<CustomerResponse> listAllCustomers() {
        return bankingService.getAllCustomers().stream()
                .map(CustomerResponse::fromEntity)
                .toList();
    }

    public List<AccountResponse> fetchCustomerAccounts(String externalRefId) {
        return bankingService.getAccountsForCustomer(externalRefId).stream()
                .map(AccountResponse::fromEntity)
                .toList();
    }

    public CustomerResponse modifyCustomer(String externalRefId, CreateCustomerRequest request) {
        Customer updated = bankingService.updateCustomer(
                externalRefId,
                request.fullName(),
                request.email(),
                "ACTIVE"
        );
        return CustomerResponse.fromEntity(updated);
    }

    public void removeCustomer(String externalRefId) {
        bankingService.deleteCustomer(externalRefId);
    }
}