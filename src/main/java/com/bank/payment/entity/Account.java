package com.bank.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    private String accountId;
    private String accountName;
    private String accountType; // SAVINGS / CURRENT
    private String status; // ACTIVE / INACTIVE
    private String currency;
    private LocalDate openedDate;
}
