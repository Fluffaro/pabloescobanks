package com.java.pabloescobanks.repository;

import com.java.pabloescobanks.entity.Transaction;
import com.java.pabloescobanks.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySendingAccount(Optional<Account> sendingAccount);
    List<Transaction> findByReceiverAccount(Account receiverAccount);


}

/*
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySendingAccount(Account account);
    List<Transaction> findByReceiverAccount(Account account);
    List<Transaction> findByUserId(Account account);
}

 */
