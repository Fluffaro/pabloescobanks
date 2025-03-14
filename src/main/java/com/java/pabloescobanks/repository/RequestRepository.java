package com.java.pabloescobanks.repository;

import com.java.pabloescobanks.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByReceiverAccount_aId(Long receiverAccountId);
    List<Request> findByRequesterAccount_aId(Long requesterAccountId);
}
