package com.example.demo.dto;

import com.example.demo.model.Customer;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record CustomerResponse(
        UUID customerId,
        String externalRefId,
        String fullName,
        String email,
        String status,
        List<AccountResponse> accounts
) {
    public static CustomerResponse fromEntity(Customer customer) {
        List<AccountResponse> accountDtos = Collections.emptyList();

        if (customer.getAccounts() != null) {
            accountDtos = customer.getAccounts()
                    .stream()
                    .map(AccountResponse::fromEntity)
                    .toList();
        }

        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getExternalRefId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getStatus(),
                accountDtos
        );
    }
}