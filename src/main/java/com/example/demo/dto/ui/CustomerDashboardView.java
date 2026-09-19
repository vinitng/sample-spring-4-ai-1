package com.example.demo.dto.ui;

import com.example.demo.dto.CustomerResponse;
import java.math.BigDecimal;
import java.util.List;

public record CustomerDashboardView(
        String externalRefId,
        String displayName,
        String email,
        String status,
        int totalAccounts,
        String formattedTotalBalance,
        List<AccountRowView> accounts
) {
    public record AccountRowView(
            String accountNumber,
            String type,
            String displayBalance
    ) {}

    public static CustomerDashboardView fromDto(CustomerResponse dto) {
        BigDecimal total = dto.accounts().stream()
                .map(a -> a.balance() != null ? a.balance() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<AccountRowView> accountRows = dto.accounts().stream()
                .map(a -> new AccountRowView(
                        a.accountNumber(),
                        a.accountType(),
                        a.currency() + " " + a.balance().toPlainString()
                ))
                .toList();

        return new CustomerDashboardView(
                dto.externalRefId(),
                dto.fullName(),
                dto.email(),
                dto.status(),
                dto.accounts().size(),
                "INR " + total.toPlainString(),
                accountRows
        );
    }
}