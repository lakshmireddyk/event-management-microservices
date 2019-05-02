package com.lk.eventservice.model;

import javax.persistence.*;

@Entity(name = "customer")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(name="full_name")
    private String name;
    @Column(name="phone")
    private String contact;
    @Column(name="customer_type")
    private String role;
    private String email;
    @Column(name="pwd")
    private String password;

    public Account() {

    }

    public Account(long userId, String name, String contact, String role, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.contact = contact;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
