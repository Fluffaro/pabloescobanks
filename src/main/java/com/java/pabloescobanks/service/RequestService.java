package com.java.pabloescobanks.service;

import com.java.pabloescobanks.entity.Request;
import com.java.pabloescobanks.entity.Transaction;
import com.java.pabloescobanks.entity.Account;
import com.java.pabloescobanks.repository.RequestRepository;
import com.java.pabloescobanks.repository.TransactionRepository;
import com.java.pabloescobanks.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public RequestService(RequestRepository requestRepository, TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.requestRepository = requestRepository;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    // 🟢 Creates a request from one user to another for money
    public Request createRequest(Long requesterAccId, Long receiverAccId, Double amount) {
        Account requester = accountRepository.findById(requesterAccId)
                .orElseThrow(() -> new IllegalArgumentException("Requester account not found"));
        Account receiver = accountRepository.findById(receiverAccId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));

        Request request = new Request(requester, receiver, amount, "Pending");
        return requestRepository.save(request);
    }

    // 🟢 Fetch all requests for a given receiver (requests they need to confirm/cancel)
    public List<Request> getRequestsForReceiver(Long receiverAccId) {
        return requestRepository.findByReceiverAccount_aId(receiverAccId);
    }

    // 🟢 Fetch all requests made by a requester (requests they have sent)
    public List<Request> getRequestsForRequester(Long requesterAccId) {
        return requestRepository.findByRequesterAccount_aId(requesterAccId);
    }

    // ✅ Confirm or Cancel a money request
    @Transactional
    public String updateRequestStatus(Long requestId, String statusRequest) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found."));

        // Ensure the request is still pending before processing
        if (!request.getStatus().equalsIgnoreCase("Pending")) {
            return "Request is already processed.";
        }

        // The receiver is the one who was asked for money (sender)
        Account sender = request.getReceiverAccount();
        Account receiver = request.getRequesterAccount();

        // Create a new transaction record
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setDate(new java.util.Date());
        transaction.setSendingAccount(sender);
        transaction.setReceiverAccount(receiver);

        if (statusRequest.equalsIgnoreCase("confirm")) {
            // Check if sender has enough funds
            if (sender.getBalance() < request.getAmount()) {
                return "Insufficient funds in sender's account.";
            }

            // Perform the transaction (deduct from sender, add to receiver)
            sender.setBalance(sender.getBalance() - request.getAmount());
            receiver.setBalance(receiver.getBalance() + request.getAmount());

            // Save updated balances
            accountRepository.save(sender);
            accountRepository.save(receiver);

            // Log the confirmation transaction
            transaction.setType("Money Request Accepted");
            transactionRepository.save(transaction);

            // Update request status
            request.setStatus("Confirmed");
            requestRepository.save(request);

            return "Request confirmed successfully.";

        } else if (statusRequest.equalsIgnoreCase("cancel") || statusRequest.equalsIgnoreCase("deny")) {
            // Update the request status accordingly
            String finalStatus = statusRequest.equalsIgnoreCase("cancel") ? "Canceled" : "Denied";
            request.setStatus(finalStatus);
            requestRepository.save(request);

            // Log the cancellation/denial transaction
            transaction.setType(finalStatus.equals("Canceled") ? "Money Request Canceled" : "Money Request Denied");
            transactionRepository.save(transaction);

            return "Request " + finalStatus + " successfully.";
        } else {
            return "Invalid status provided.";
        }
    }


}
