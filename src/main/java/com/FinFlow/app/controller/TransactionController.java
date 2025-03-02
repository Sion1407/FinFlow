package com.FinFlow.app.controller;

import com.FinFlow.app.model.Transaction;
import com.FinFlow.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public CompletableFuture<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @GetMapping("/{accountNumber}")
    public CompletableFuture<List<Transaction>> getTransactions(@PathVariable String accountNumber) {
        return transactionService.getTransactions(accountNumber);
    }
}
