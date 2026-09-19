package com.example.demo.adapter;

import com.example.demo.dto.CreateCustomerRequest;
import com.example.demo.dto.ui.CustomerDashboardView;

import java.util.List;

public interface BankingUiAdapter {
    List<CustomerDashboardView> getDashboardData();
    CustomerDashboardView handleOnboarding(CreateCustomerRequest request);
    CustomerDashboardView handleUpdate(String externalRefId, CreateCustomerRequest request);
    void handleTermination(String externalRefId);
}