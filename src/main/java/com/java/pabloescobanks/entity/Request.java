package com.java.pabloescobanks.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rId")
    private Long rId;

    @ManyToOne
    @JoinColumn(name = "requester_acc", nullable = false)
    private Account requesterAccount;  // User who requests money

    @ManyToOne
    @JoinColumn(name = "receiver_acc", nullable = false)
    private Account receiverAccount;   // User who receives the request

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    @Column(nullable = false, length = 10)
    private String status; // "Pending", "Confirmed", "Canceled"

    public Request(Account requesterAccount, Account receiverAccount, Double amount, String status) {
        this.requesterAccount = requesterAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.requestDate = new Date();
        this.status = status;
    }
}
