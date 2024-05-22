package com.example.emtesttask.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyTransferRequest {
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
}
