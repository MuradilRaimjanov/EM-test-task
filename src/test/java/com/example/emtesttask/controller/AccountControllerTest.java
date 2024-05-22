package com.example.emtesttask.controller;

import com.example.emtesttask.dto.TransferRequest;
import com.example.emtesttask.model.Account;
import com.example.emtesttask.service.AccountService;
import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
class AccountControllerTest {


    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransferMoney_SuccessfulTransfer() {
        Account fromAccount = new Account();
        fromAccount.setId(1L);
        fromAccount.setBalance(BigDecimal.valueOf(100));

        Account toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setBalance(BigDecimal.valueOf(50));

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromAccountId(1L);
        transferRequest.setToAccountId(2L);
        transferRequest.setAmount(BigDecimal.valueOf(30));

        when(accountService.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountService.findById(2L)).thenReturn(Optional.of(toAccount));

        accountController.transferMoney(transferRequest);

        verify(accountService, times(1)).transferMoney(fromAccount, toAccount, BigDecimal.valueOf(30));
    }

    @Test
    public void testTransferMoney_AccountNotFound() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromAccountId(1L);
        transferRequest.setToAccountId(2L);
        transferRequest.setAmount(BigDecimal.valueOf(30));

        when(accountService.findById(1L)).thenReturn(Optional.empty());
        when(accountService.findById(2L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> accountController.transferMoney(transferRequest));

        verify(accountService, never()).transferMoney(any(), any(), any());
    }

    @Test
    public void testTransferMoney_InsufficientFunds() {
        Account fromAccount = new Account();
        fromAccount.setId(1L);
        fromAccount.setBalance(BigDecimal.valueOf(100));

        Account toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setBalance(BigDecimal.valueOf(50));

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromAccountId(1L);
        transferRequest.setToAccountId(2L);
        transferRequest.setAmount(BigDecimal.valueOf(200)); // Эта сумма превышает баланс отправителя

        when(accountService.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountService.findById(2L)).thenReturn(Optional.of(toAccount));

        assertThrows(IllegalArgumentException.class, () -> accountController.transferMoney(transferRequest));

        verify(accountService, never()).transferMoney(any(), any(), any());
    }
}