package com.java.pabloescobanks.service;

import com.java.pabloescobanks.entity.Account;
import com.java.pabloescobanks.entity.Transaction;
import com.java.pabloescobanks.entity.User;
import com.java.pabloescobanks.exception.AuthException;
import com.java.pabloescobanks.exception.DepositException;
import com.java.pabloescobanks.exception.WithdrawException;
import com.java.pabloescobanks.repository.AccountRepository;
import com.java.pabloescobanks.repository.TransactionRepository;
import com.java.pabloescobanks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // ✅ Create a new account for a user
    public Account createAccount(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new AuthException("User not found");
        }

        User user = userOpt.get();

        if (accountRepository.findByUser_uId(userId).isPresent()) {
            throw new AuthException("User already has an account");
        }

        Account account = new Account();
        account.setUser(user);
        account.setBalance(0.0);
        account.setDateCreated(new Date());

        return accountRepository.save(account);
    }

    // ✅ Fetch an account by user ID
    public Account getAccountByUserId(Long userId) {
        return accountRepository.findByUser_uId(userId)
                .orElseThrow(() -> new AuthException("Account not found for this user"));
    }

    // ✅ Deposit money into an account
    public Account deposit(Long userId, Double amount) {
        Account account = getAccountByUserId(userId);

        if (amount <= 0) {
            throw new DepositException("Deposit amount must be greater than zero");
        }

        account.setBalance(account.getBalance() + amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDate(new Date());
        transaction.setType("Deposit");
        transaction.setReceiverAccount(null);
        transaction.setSendingAccount(account);

        transactionRepository.save(transaction);


        return accountRepository.save(account);
    }

    // ✅ Withdraw money from an account
    public Account withdraw(Long userId, Double amount) {
        Account account = getAccountByUserId(userId);

        if (amount <= 0) {
            throw new WithdrawException("Withdrawal amount must be greater than zero");
        }

        if (account.getBalance() < amount) {
            throw new WithdrawException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDate(new Date());
        transaction.setType("Withdraw");
        transaction.setReceiverAccount(null);
        transaction.setSendingAccount(account);

        transactionRepository.save(transaction);
        return accountRepository.save(account);
    }

    // ✅ Close an account
    public void closeAccount(Long userId) {
        Account account = getAccountByUserId(userId);
        account.setDateClosed(new Date());
        accountRepository.save(account);
    }

    // In UserService
    public User findUserByUsernameOrEmail(String identifier) {
        if (identifier.contains("@")) {
            return userRepository.findByEmail(identifier)
                    .orElseThrow(() -> new AuthException("User not found"));
        } else {
            return userRepository.findByUsername(identifier)
                    .orElseThrow(() -> new AuthException("User not found"));
        }
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AuthException("Account not found with id " + accountId));
    }


    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }


}
