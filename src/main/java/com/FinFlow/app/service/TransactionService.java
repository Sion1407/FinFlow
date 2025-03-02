package com.FinFlow.app.service;

import com.FinFlow.app.model.Transaction;
import com.FinFlow.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    private final ReentrantLock lock = new ReentrantLock();

    @Async
    public CompletableFuture<Transaction> createTransaction(Transaction transaction) {
        return CompletableFuture.completedFuture(transactionRepository.save(transaction));
    }

    @Async
    public CompletableFuture<List<Transaction>> getTransactions(String accountNumber) {
        return CompletableFuture.completedFuture(
                transactionRepository.findByFromAccountNumberOrToAccountNumber(accountNumber, accountNumber)
        );
    }
}