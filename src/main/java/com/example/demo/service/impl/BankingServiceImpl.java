package com.example.demo.service.impl;

import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.BankingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankingServiceImpl implements BankingService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public BankingServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Customer registerCustomerWithAccount(String externalRefId, String fullName, String email, String accountNumber, BigDecimal initialBalance) {
        if (customerRepository.findByExternalRefId(externalRefId).isPresent()) {
            throw new IllegalArgumentException("Customer reference ID '" + externalRefId + "' already exists.");
        }
        if (accountRepository.findByAccountNumber(accountNumber).isPresent()) {
            throw new IllegalArgumentException("Account number '" + accountNumber + "' already exists.");
        }

        Customer customer = new Customer();
        customer.setExternalRefId(externalRefId);
        customer.setFullName(fullName);
        customer.setEmail(email);
        customer.setStatus("ACTIVE");

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setAccountType("SAVINGS");
        account.setCurrency("INR");
        account.setBalance(initialBalance != null ? initialBalance : BigDecimal.ZERO);
        account.setCustomer(customer);

        customer.getAccounts().add(account);

        return customerRepository.save(customer);
    }



    @Override
    @Transactional(readOnly = true)
    public List<Account> getAccountsForCustomer(String externalRefId) {
        Customer customer = customerRepository.findByExternalRefIdWithAccounts(externalRefId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + externalRefId));
        return customer.getAccounts();
    }
    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAllWithAccounts(); // <--- Uses JOIN FETCH
    }

    @Override
    @Transactional
    public Customer updateCustomer(String externalRefId, String fullName, String email, String status) {
        // Eagerly fetch accounts alongside the customer record
        Customer customer = customerRepository.findByExternalRefIdWithAccounts(externalRefId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + externalRefId));

        if (fullName != null && !fullName.isBlank()) customer.setFullName(fullName);
        if (email != null && !email.isBlank()) customer.setEmail(email);
        if (status != null && !status.isBlank()) customer.setStatus(status);

        return customerRepository.save(customer);
    }
    @Override
    @Transactional
    public void deleteCustomer(String externalRefId) {
        Customer customer = customerRepository.findByExternalRefId(externalRefId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + externalRefId));

        accountRepository.deleteAll(customer.getAccounts());
        customerRepository.delete(customer);
    }
/// NEW


    ///New
}