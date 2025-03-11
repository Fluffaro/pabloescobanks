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

    @Column(nullable = false, length = 45)
    private String type; // Example: "Deposit", "Withdrawal", "Transfer"

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    // Many-to-One relationship with Account for the sender
    @ManyToOne
    @JoinColumn(name = "sending_acc", nullable = false)
    @JsonBackReference("account-sentTransactions") // ✅ Prevent recursion
    private Account sendingAccount;

    // Many-to-One relationship with Account for the receiver
    @ManyToOne
    @JoinColumn(name = "receiver_acc", nullable = false)
    @JsonBackReference("account-receivedTransactions") // ✅ Prevent recursion
    private Account receiverAccount;
}
