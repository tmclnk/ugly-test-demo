package com.prairiegrade.ugly.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.Expose;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Expose
    private Long id;

    @Column
    @Expose
    private BigDecimal ledgerBalance;
    
    @ManyToOne
    @Expose
    private Person owner;

    @Column
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

    public BigDecimal getLedgerBalance() {
        return ledgerBalance;
    }

    public void setLedgerBalance(BigDecimal ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }

    public AccountType getAccountType(){
        return this.accountType;
    }

    public void setAccountType(AccountType accountType){
        this.accountType = accountType;
    }
}
