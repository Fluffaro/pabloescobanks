package com.java.pabloescobanks.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tId")
    private Long tId;

    @Column(nullable = false)
    private Double amount;

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getSendingAccount() {
        return sendingAccount;
    }

    public void setSendingAccount(Account sendingAccount) {
        this.sendingAccount = sendingAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    @Column(nullable = false, length = 45)
    private String type; // Example: "Deposit", "Withdrawal", "Transfer"

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    // Many-to-One relationship with Account for the sender
    @ManyToOne
    @JoinColumn(name = "sending_acc")
    @JsonBackReference("account-sentTransactions") // ✅ Prevent recursion
    private Account sendingAccount;

    // Many-to-One relationship with Account for the receiver
    @ManyToOne
    @JoinColumn(name = "receiver_acc")
    @JsonBackReference("account-receivedTransactions") // ✅ Prevent recursion
    private Account receiverAccount;
}
