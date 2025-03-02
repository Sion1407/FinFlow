package com.FinFlow.app.service;

import com.FinFlow.app.model.Account;
import com.FinFlow.app.model.Transaction;
import com.FinFlow.app.model.User;
import com.FinFlow.app.repository.AccountRepository;
import com.FinFlow.app.repository.TransactionRepository;
import com.FinFlow.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;

    private final ReentrantLock lock = new ReentrantLock();

    @Async
    public CompletableFuture<Account> getAccount(String accountNumber) {
        return CompletableFuture.completedFuture(accountRepository.findByAccountNumber(accountNumber));
    }

    @Async
    public CompletableFuture<Account> createAccount(Account account, Long userId) {
        // Fetch the user from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the user in the account
        account.setUser(user);

        // Save the account
        return CompletableFuture.completedFuture(accountRepository.save(account));
    }

    @Async
    public CompletableFuture<Void> transferFunds(String fromAccountNumber, String toAccountNumber, Double amount) {
        lock.lock();
        try {
            Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
            Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);

            if (fromAccount.getBalance() < amount) {
                throw new RuntimeException("Insufficient balance");
            }

            // Deduct from sender's account
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            accountRepository.save(fromAccount);

            // Add to receiver's account
            toAccount.setBalance(toAccount.getBalance() + amount);
            accountRepository.save(toAccount);

            // Record the transaction
            Transaction transaction = new Transaction();
            transaction.setFromAccountNumber(fromAccountNumber);
            transaction.setToAccountNumber(toAccountNumber);
            transaction.setAmount(amount);
            transactionRepository.save(transaction);
        } finally {
            lock.unlock();
        }
        return CompletableFuture.completedFuture(null);
    }
}