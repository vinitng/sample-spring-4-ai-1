package com.example.demo.controller;

import com.example.demo.adapter.BankingUiAdapter;
import com.example.demo.dto.CreateCustomerRequest;
import com.example.demo.dto.ui.CustomerDashboardView;
import com.example.demo.dto.ui.UiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ui/banking")
public class BankingUiController {

    private final BankingUiAdapter uiAdapter;

    public BankingUiController(BankingUiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<UiResponse<List<CustomerDashboardView>>> getDashboard() {
        List<CustomerDashboardView> data = uiAdapter.getDashboardData();
        return ResponseEntity.ok(UiResponse.ok("Dashboard data loaded", data));
    }

    @PostMapping("/customers")
    public ResponseEntity<UiResponse<CustomerDashboardView>> createCustomer(@RequestBody CreateCustomerRequest request) {
        CustomerDashboardView view = uiAdapter.handleOnboarding(request);
        return ResponseEntity.ok(UiResponse.ok("Customer onboarded successfully", view));
    }

    @PutMapping("/customers/{externalRefId}")
    public ResponseEntity<UiResponse<CustomerDashboardView>> updateCustomer(
            @PathVariable String externalRefId,
            @RequestBody CreateCustomerRequest request) {
        CustomerDashboardView view = uiAdapter.handleUpdate(externalRefId, request);
        return ResponseEntity.ok(UiResponse.ok("Customer updated successfully", view));
    }

    @DeleteMapping("/customers/{externalRefId}")
    public ResponseEntity<UiResponse<Void>> deleteCustomer(@PathVariable String externalRefId) {
        uiAdapter.handleTermination(externalRefId);
        return ResponseEntity.ok(UiResponse.ok("Customer deleted successfully", null));
    }
}