package com.example.emtesttask.controller;

import com.example.emtesttask.model.Account;
import com.example.emtesttask.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService bankAccountService;

    @Operation(summary = "Transfer money between accounts", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/transfer")
    public void transferMoney(@RequestParam Long fromAccountId, @RequestParam Long toAccountId, @RequestParam BigDecimal amount) {
        Account fromAccount = bankAccountService.findById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        Account toAccount = bankAccountService.findById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        bankAccountService.transferMoney(fromAccount, toAccount, amount);
    }
}
