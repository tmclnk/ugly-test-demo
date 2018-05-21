package com.prairiegrade.ugly.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="Acct")
public class Account {
    @Id
    @Column(name="id")
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Expose
    private Long id;

    @Column
    @Expose
    private long balance;
    
    @ManyToOne
    @Expose
    private Person owner;

    @Column
    @Enumerated(EnumType.STRING)
    @Expose
    private AccountType accountType;

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance= balance;
    }

    public AccountType getAccountType(){
        return this.accountType;
    }

    public void setAccountType(AccountType accountType){
        this.accountType = accountType;
    }
}
