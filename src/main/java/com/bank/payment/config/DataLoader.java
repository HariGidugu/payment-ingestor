package com.bank.payment.config;

import com.bank.payment.entity.Account;
import com.bank.payment.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {
        if (accountRepository.count() == 0) {
            log.info("Loading initial account data...");
            
            Account account1 = Account.builder()
                    .accountId("ACC-1001")
                    .accountName("Alice Smith")
                    .accountType("SAVINGS")
                    .status("ACTIVE")
                    .currency("USD")
                    .openedDate(LocalDate.of(2023, 1, 15))
                    .build();

            Account account2 = Account.builder()
                    .accountId("ACC-1002")
                    .accountName("Bob Jones")
                    .accountType("CURRENT")
                    .status("ACTIVE")
                    .currency("USD")
                    .openedDate(LocalDate.of(2022, 5, 20))
                    .build();

            Account account3 = Account.builder()
                    .accountId("ACC-1003")
                    .accountName("Charlie Davis")
                    .accountType("SAVINGS")
                    .status("INACTIVE")
                    .currency("EUR")
                    .openedDate(LocalDate.of(2021, 10, 10))
                    .build();

            accountRepository.saveAll(List.of(account1, account2, account3));
            log.info("Initialized 3 accounts in H2 database.");
        }
    }
}
