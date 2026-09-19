package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface BankingService {
    Customer registerCustomerWithAccount(String externalRefId, String fullName, String email, String accountNumber, BigDecimal initialBalance);
    List<Account> getAccountsForCustomer(String externalRefId);
    List<Customer> getAllCustomers();
    Customer updateCustomer(String externalRefId, String fullName, String email, String status);
    void deleteCustomer(String externalRefId);
}