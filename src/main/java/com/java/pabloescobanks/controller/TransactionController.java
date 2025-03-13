package com.java.pabloescobanks.controller;

import com.java.pabloescobanks.entity.Account;
import com.java.pabloescobanks.entity.Transaction;
import com.java.pabloescobanks.entity.User;
import com.java.pabloescobanks.service.AccountService;
import com.java.pabloescobanks.service.TransactionService;
import com.java.pabloescobanks.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;
    private final AccountService accountService;
    public TransactionController(TransactionService transactionService, UserService userService, AccountService accountService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.accountService = accountService;
    }

    // ✅ Transfer funds between accounts
    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transferFunds(
            @RequestParam Long senderId,
            @RequestParam Long receiverAccountId,
            @RequestParam Double amount) {
        Account senderAccount = accountService.getAccountByUserId(senderId);
        Account receiverAccount = accountService.getAccountById(receiverAccountId); // New method in AccountService
        Transaction transaction = transactionService.transferFunds(senderAccount.getaId(), receiverAccount.getaId(), amount);
        return ResponseEntity.ok(transaction);
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

    @GetMapping("/transaction")
    public ResponseEntity<List<Transaction>> getAllTransactionHistory() {
        return ResponseEntity.ok(transactionService.getAllTransactionHistory());
    }
}
