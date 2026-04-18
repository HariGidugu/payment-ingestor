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

            List<Account> accounts = List.of(

                    Account.builder()
                            .accountId("20-15-88/43917265")
                            .accountName("Marcus T. Oyelaran")
                            .accountType("PERSONAL")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2018, 3, 15))
                            .build(),

                    Account.builder()
                            .accountId("20-15-88/61082934")
                            .accountName("Oyelaran Family Savings")
                            .accountType("SAVINGS")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2019, 7, 22))
                            .build(),

                    Account.builder()
                            .accountId("30-91-44/87654321")
                            .accountName("Meridian Ventures Ltd")
                            .accountType("BUSINESS")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2015, 11, 8))
                            .build(),

                    Account.builder()
                            .accountId("30-91-44/12309876")
                            .accountName("Diane K. Ashworth")
                            .accountType("PERSONAL")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2020, 7, 22))
                            .build(),

                    Account.builder()
                            .accountId("40-47-84/55501234")
                            .accountName("Northbridge Advisory Partners Ltd")
                            .accountType("BUSINESS")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2017, 4, 14))
                            .build(),

                    Account.builder()
                            .accountId("40-47-84/99887766")
                            .accountName("Patrick J. Nkemdirim")
                            .accountType("PERSONAL")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2021, 1, 9))
                            .build(),

                    Account.builder()
                            .accountId("60-83-71/34561234")
                            .accountName("Elara Professional Services Ltd")
                            .accountType("BUSINESS")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2016, 9, 30))
                            .build(),

                    // ✅ Suspended account (important for negative test cases)
                    Account.builder()
                            .accountId("60-83-71/78900001")
                            .accountName("Sandra L. Whitmore")
                            .accountType("PERSONAL")
                            .status("SUSPENDED")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2017, 6, 1))
                            .build(),

                    Account.builder()
                            .accountId("09-01-28/11223344")
                            .accountName("Oluwaseun B. Adeyemi")
                            .accountType("PERSONAL")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2019, 11, 25))
                            .build(),

                    Account.builder()
                            .accountId("09-01-28/55667788")
                            .accountName("Adeyemi Property Holdings Ltd")
                            .accountType("BUSINESS")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2022, 3, 1))
                            .build(),

                    Account.builder()
                            .accountId("77-22-55/24681357")
                            .accountName("Claire M. Buchanan")
                            .accountType("PERSONAL")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2021, 8, 17))
                            .build(),

                    Account.builder()
                            .accountId("77-22-55/13579246")
                            .accountName("Buchanan & Sons Trading Ltd")
                            .accountType("BUSINESS")
                            .status("ACTIVE")
                            .currency("EUR")
                            .openedDate(LocalDate.of(2023, 2, 14))
                            .build(),

                    Account.builder()
                            .accountId("11-44-77/66778899")
                            .accountName("Fatima N. Al-Hassan")
                            .accountType("PERSONAL")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2022, 5, 12))
                            .build(),

                    Account.builder()
                            .accountId("11-44-77/33445566")
                            .accountName("Al-Hassan International Consultancy")
                            .accountType("BUSINESS")
                            .status("ACTIVE")
                            .currency("USD")
                            .openedDate(LocalDate.of(2020, 10, 28))
                            .build(),

                    Account.builder()
                            .accountId("55-66-33/10293847")
                            .accountName("Yusuf K. Bergmann")
                            .accountType("PERSONAL")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2020, 3, 8))
                            .build(),

                    Account.builder()
                            .accountId("55-66-33/98765432")
                            .accountName("Bergmann Engineering GmbH UK")
                            .accountType("BUSINESS")
                            .status("ACTIVE")
                            .currency("EUR")
                            .openedDate(LocalDate.of(2018, 7, 19))
                            .build(),

                    Account.builder()
                            .accountId("22-33-44/47382910")
                            .accountName("Priya R. Sharma")
                            .accountType("PERSONAL")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2023, 1, 19))
                            .build(),

                    Account.builder()
                            .accountId("22-33-44/82736450")
                            .accountName("Sharma Tech Ventures Ltd")
                            .accountType("BUSINESS")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2023, 6, 5))
                            .build(),

                    Account.builder()
                            .accountId("88-99-11/63819274")
                            .accountName("Robert M. Kowalski")
                            .accountType("PERSONAL")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2018, 8, 30))
                            .build(),

                    Account.builder()
                            .accountId("88-99-11/91827364")
                            .accountName("Kowalski Logistics Ltd")
                            .accountType("BUSINESS")
                            .status("ACTIVE")
                            .currency("GBP")
                            .openedDate(LocalDate.of(2021, 4, 12))
                            .build());
            accountRepository.saveAll(accounts);
            log.info("Initialized 3 accounts in H2 database.");
        }
    }
}
