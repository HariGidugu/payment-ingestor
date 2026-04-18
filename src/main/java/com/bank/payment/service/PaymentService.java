package com.bank.payment.service;

import com.bank.payment.dto.PaymentEvent;
import com.bank.payment.dto.PaymentRequest;
import com.bank.payment.entity.Account;
import com.bank.payment.exception.AccountInactiveException;
import com.bank.payment.exception.AccountNotFoundException;
import com.bank.payment.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "payments.submitted";
    private static final String ACTIVE_STATUS = "ACTIVE";

    @Transactional(readOnly = true)
    public String processPayment(PaymentRequest request) {
        // Validate debit account
        Account debitAccount = accountRepository.findById(request.getDebitAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Debit account not found: " + request.getDebitAccountId()));

        if (!ACTIVE_STATUS.equals(debitAccount.getStatus())) {
            throw new AccountInactiveException("Debit account is not ACTIVE: " + request.getDebitAccountId());
        }

        // Validate credit account
        Account creditAccount = accountRepository.findById(request.getCreditAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Credit account not found: " + request.getCreditAccountId()));

        if (!ACTIVE_STATUS.equals(creditAccount.getStatus())) {
            throw new AccountInactiveException("Credit account is not ACTIVE: " + request.getCreditAccountId());
        }

        // Both accounts are ACTIVE. Generate Payment ID
        String paymentId = UUID.randomUUID().toString();

        // Create Payment Event
        PaymentEvent event = PaymentEvent.builder()
                .paymentId(paymentId)
                .debitAccountId(request.getDebitAccountId())
                .creditAccountId(request.getCreditAccountId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .timestamp(System.currentTimeMillis())
                .build();

        // Publish to Kafka
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC, paymentId, event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent payment event with id=[{}] to partition=[{}] with offset=[{}]", 
                        paymentId, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send payment event with id=[{}] due to: {}", paymentId, ex.getMessage());
            }
        });

        return paymentId;
    }

    @Transactional(readOnly = true)
    public Account getAccountDetails(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountId));
    }
}
