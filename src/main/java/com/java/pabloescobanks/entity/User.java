package com.java.pabloescobanks.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")  // Use correct table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uId")  // Match database column name
    private Long uId;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, unique = true, length = 45)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false)
    private Date birthday;

    @Column(nullable = false)
    private Integer mobile;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_joined;

    @Column(nullable = false, length = 45)
    private String role; // Role stored directly in User table

    // Optional One-to-One relationship with Account
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Account account;

    public User(String name, String username, String email, String password, Date birthday, Integer mobile, Date date_joined, String role) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.mobile = mobile;
        this.date_joined = date_joined;
        this.role = role;
    }
}
