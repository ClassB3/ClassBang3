package com.example.cb.account;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class AccountLog implements Serializable
{
    private boolean depositOrNot;
    private double amount;
    private String dateOfTransaction;
    private String timeOfTransaction;

    public AccountLog() {}
    public AccountLog(boolean depositOrNot, double amount)
    {
        this.depositOrNot = depositOrNot;
        this.amount = amount;
        this.dateOfTransaction= LocalDate.now().toString();
        this.timeOfTransaction= LocalTime.now().toString();
    }

    public String getDateOfTransaction() { return dateOfTransaction; }
    public String getTimeOfTransaction() { return timeOfTransaction; }

    public boolean isDepositOrNot() { return depositOrNot; }
    public double getAmount() { return amount; }
}
