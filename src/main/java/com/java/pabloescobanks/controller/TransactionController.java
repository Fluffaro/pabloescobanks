package com.java.pabloescobanks.controller;

import com.java.pabloescobanks.entity.Transaction;
import com.java.pabloescobanks.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // ✅ Transfer funds between accounts
    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transferFunds(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestParam Double amount) {
        return ResponseEntity.ok(transactionService.transferFunds(senderId, receiverId, amount));
    }

    // ✅ Fetch transaction history for an account
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getTransactionHistory(accountId));
    }

    // ✅ Fetch transaction history for a user (by user ID)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getUserTransactionHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getUserTransactionHistory(userId));
    }
}
