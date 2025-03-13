package com.java.pabloescobanks.service;

import com.java.pabloescobanks.entity.Account;
import com.java.pabloescobanks.entity.Transaction;
import com.java.pabloescobanks.exception.AuthException;
import com.java.pabloescobanks.exception.TransferFundsException;
import com.java.pabloescobanks.repository.AccountRepository;
import com.java.pabloescobanks.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    // ✅ Transfer money between two accounts
    @Transactional
    public Transaction transferFunds(Long senderId, Long receiverId, Double amount) {
        if (amount <= 0) {
            throw new TransferFundsException("Transfer amount must be greater than zero.");
        }

        Optional<Account> senderOpt = accountRepository.findById(senderId);
        Optional<Account> receiverOpt = accountRepository.findById(receiverId);

        if (senderOpt.isEmpty() || receiverOpt.isEmpty()) {
            throw new AuthException("Sender or receiver account not found.");
        }

        Account sender = senderOpt.get();
        Account receiver = receiverOpt.get();

        // Ensure sender has enough balance
        if (sender.getBalance() < amount) {
            throw new TransferFundsException("Insufficient balance.");
        }

        // Deduct from sender and add to receiver
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        // Record transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType("Transfer");
        transaction.setDate(new Date());
        transaction.setSendingAccount(sender);
        transaction.setReceiverAccount(receiver);

        return transactionRepository.save(transaction);
    }

    /*
    @Transactional
    public Transaction depositFunds(){}

     */

    // ✅ Fetch transaction history for an account
    public List<Transaction> getTransactionHistory(Long accountId) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);

        if (accountOpt.isEmpty()) {
            throw new AuthException("Account not found.");
        }

        Account account = accountOpt.get();

        List<Transaction> sentTransactions = transactionRepository.findAll();



        return sentTransactions;
    }

    // ✅ Fetch transaction history for a user
    public List<Transaction> getUserTransactionHistory(Long userId) {
        Optional<Account> accountOpt = accountRepository.findByUser_uId(userId);

        if (accountOpt.isEmpty()) {
            throw new AuthException("User does not have an account.");
        }
        Optional<Account> account = accountRepository.findById(userId);


        return transactionRepository.findBySendingAccount(account);
    }

    public List<Transaction> getAllTransactionHistory(){
        return transactionRepository.findAll();
    }
}
