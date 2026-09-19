package com.example.demo.adapter;

import com.example.demo.dto.CreateCustomerRequest;
import com.example.demo.dto.CustomerResponse;
import com.example.demo.dto.ui.CustomerDashboardView;
import com.example.demo.facade.BankingFacade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankingUiAdapterImpl implements BankingUiAdapter {

    private final BankingFacade bankingFacade;

    public BankingUiAdapterImpl(BankingFacade bankingFacade) {
        this.bankingFacade = bankingFacade;
    }

    @Override
    public List<CustomerDashboardView> getDashboardData() {
        return bankingFacade.listAllCustomers().stream()
                .map(CustomerDashboardView::fromDto)
                .toList();
    }

    @Override
    public CustomerDashboardView handleOnboarding(CreateCustomerRequest request) {
        CustomerResponse customerResponse = bankingFacade.onboardCustomer(request);
        return CustomerDashboardView.fromDto(customerResponse);
    }

    @Override
    public CustomerDashboardView handleUpdate(String externalRefId, CreateCustomerRequest request) {
        CustomerResponse customerResponse = bankingFacade.modifyCustomer(externalRefId, request);
        return CustomerDashboardView.fromDto(customerResponse);
    }

    @Override
    public void handleTermination(String externalRefId) {
        bankingFacade.removeCustomer(externalRefId);
    }
}