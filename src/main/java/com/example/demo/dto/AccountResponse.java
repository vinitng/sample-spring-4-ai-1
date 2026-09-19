package com.example.demo.dto;

import com.example.demo.model.Account;
import java.math.BigDecimal;

public record AccountResponse(
        Long accountId,
        String accountNumber,
        String accountType,
        String currency,
        BigDecimal balance,
        BigDecimal holdBalance,
        String status
) {
    public static AccountResponse fromEntity(Account account) {
        return new AccountResponse(
                account.getAccountId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getCurrency(),
                account.getBalance(),
                account.getHoldBalance(),
                account.getStatus()
        );
    }
}