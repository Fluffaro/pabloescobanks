package com.java.pabloescobanks.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal mobile;


    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_joined;



    @Column(nullable = false, length = 45)
    private String role; // Role stored directly in User table

    // Optional One-to-One relationship with Account
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Account account;


    public User(String name, String username, String email, String password, Date birthday, BigDecimal mobile, Date date_joined, String role) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.mobile = mobile;
        this.date_joined = date_joined;
        this.role = role;
    }

    public Long getUId() {
        return uId;
    }

    public void setUId(Long uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getMobile() {
        return mobile;
    }

    public void setMobile(BigDecimal mobile) {
        this.mobile = mobile;
    }

    public Date getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(Date date_joined) {
        this.date_joined = date_joined;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
