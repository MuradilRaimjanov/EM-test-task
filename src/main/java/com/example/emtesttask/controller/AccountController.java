package com.example.emtesttask.controller;

import com.example.emtesttask.dto.TransferRequest;
import com.example.emtesttask.model.Account;
import com.example.emtesttask.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Operation(summary = "Transfer money between accounts", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/transfer")
    public void transferMoney(@RequestBody TransferRequest transferRequest)  {
        Account fromAccount = accountService.findById(transferRequest.getFromAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        Account toAccount = accountService.findById(transferRequest.getToAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        accountService.transferMoney(fromAccount, toAccount, transferRequest.getAmount());
    }
}
