package com.java.pabloescobanks.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aId")
    private Long aId;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateClosed;

    @Column
    @Temporal(TemporalType.DATE)
    private Date dateExp;

    // One-to-One relationship with User (optional)
    @OneToOne
    @JoinColumn(name = "user_uId", unique = true)
    @JsonBackReference
    private User user;

    // One-to-Many relationship with transactions where this account is the sender
    @OneToMany(mappedBy = "sendingAccount", cascade = CascadeType.ALL)
    @JsonManagedReference("account-sentTransactions") // ✅ Prevent recursion
    private List<Transaction> sentTransactions;

    // One-to-Many relationship with transactions where this account is the receiver
    @OneToMany(mappedBy = "receiverAccount", cascade = CascadeType.ALL)
    @JsonManagedReference("account-receivedTransactions") // ✅ Prevent recursion
    private List<Transaction> receivedTransactions;
}
