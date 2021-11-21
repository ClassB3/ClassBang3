package com.example.cb.account;

import java.io.Serializable;

public class Account implements Serializable
{
    private static final int DEFAULT_BALANCE= 0;
    private String accountType;
    private double balance;

    public Account(String accountType)
    {
        this.accountType = accountType;
        this.balance = DEFAULT_BALANCE;
    }

    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }
}
