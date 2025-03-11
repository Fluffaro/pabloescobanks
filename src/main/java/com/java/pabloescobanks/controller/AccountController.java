package com.java.pabloescobanks.controller;

import com.java.pabloescobanks.entity.Account;
import com.java.pabloescobanks.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // ✅ Create a new account for a user
    @PostMapping("/create/{userId}")
    public ResponseEntity<Account> createAccount(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.createAccount(userId));
    }

    // ✅ Fetch an account by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Account> getAccountByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getAccountByUserId(userId));
    }

    // ✅ Deposit money into an account
    @PutMapping("/deposit/{userId}")
    public ResponseEntity<Account> deposit(@PathVariable Long userId, @RequestParam Double amount) {
        return ResponseEntity.ok(accountService.deposit(userId, amount));
    }

    // ✅ Withdraw money from an account
    @PutMapping("/withdraw/{userId}")
    public ResponseEntity<Account> withdraw(@PathVariable Long userId, @RequestParam Double amount) {
        return ResponseEntity.ok(accountService.withdraw(userId, amount));
    }

    // ✅ Close an account
    @PutMapping("/close/{userId}")
    public ResponseEntity<String> closeAccount(@PathVariable Long userId) {
        accountService.closeAccount(userId);
        return ResponseEntity.ok("Account closed successfully.");
    }
}
