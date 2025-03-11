package com.java.pabloescobanks.repository;

import com.java.pabloescobanks.entity.Transaction;
import com.java.pabloescobanks.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySendingAccount(Account account);
    List<Transaction> findByReceiverAccount(Account account);
}
