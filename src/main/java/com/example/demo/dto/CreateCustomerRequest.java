package com.example.demo.dto;

import java.math.BigDecimal;

public record CreateCustomerRequest(
        String externalRefId,
        String fullName,
        String email,
        String accountNumber,
        BigDecimal initialBalance
) {}