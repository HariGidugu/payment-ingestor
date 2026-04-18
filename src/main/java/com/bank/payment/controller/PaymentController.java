package com.bank.payment.controller;

import com.bank.payment.dto.PaymentRequest;
import com.bank.payment.dto.PaymentResponse;
import com.bank.payment.entity.Account;
import com.bank.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Payment and Account API", description = "Endpoints for payment ingestion and account lookup")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Accept a payment request", description = "Validates accounts and publishes a valid payment event to Kafka")
    @PostMapping("/payments")
    public ResponseEntity<PaymentResponse> submitPayment(@Valid @RequestBody List<PaymentRequest> requests) {
        List<String> paymentIds = paymentService.processPayments(requests);

        PaymentResponse response = PaymentResponse.builder()
                .paymentIds(paymentIds)
                .status("ACCEPTED")
                .message("Payments submitted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(summary = "Fetch account details", description = "Retrieves account information by account ID")
    @GetMapping("/accounts")
    public ResponseEntity<Account> getAccount(@RequestParam(name = "accountId") String accountId) {
        Account account = paymentService.getAccountDetails(accountId);
        return ResponseEntity.ok(account);
    }
}
