package com.FinFlow.app.controller;

import com.FinFlow.app.model.Account;
import com.FinFlow.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public CompletableFuture<Account> createAccount(@RequestBody Account account,@RequestParam Long userId) {
        return accountService.createAccount(account,userId);
    }

    @GetMapping("/{accountNumber}")
    public CompletableFuture<Account> getAccount(@PathVariable String accountNumber) {
        return accountService.getAccount(accountNumber);
    }

    @PostMapping("/transfer")
    public CompletableFuture<Void> transferFunds(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam Double amount) {
        return accountService.transferFunds(fromAccountNumber, toAccountNumber, amount);
    }
}