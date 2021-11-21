package com.example.cb.account;

import java.io.Serializable;

public class Saving extends Account implements Serializable
{
    private String type;
    private double rate;
    private int amount;

    private boolean closeOrNot;

    private String registrationDate;
    private String dueDate;
    private String dDay;
    private int totalTerm;
    private int period; // 15, 30 etc...

    private String name;
    private String number;

    public Saving(String accountType)
    {
        super(accountType);
    }

    public String getType() { return type; }
    public double getRate() { return rate; }
    public int getAmount() { return amount; }
    public boolean isCloseOrNot() { return closeOrNot; }
    public String getRegistrationDate() { return registrationDate; }
    public String getDueDate() { return dueDate; }
    public String getdDay() { return dDay; }
    public int getTotalTerm() { return totalTerm; }
    public int getPeriod() { return period; }
    public String getName() { return name; }
    public String getNumber() { return number; }

    public void setType(String type) {
        this.type = type;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCloseOrNot(boolean closeOrNot) {
        this.closeOrNot = closeOrNot;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setdDay(String dDay) {
        this.dDay = dDay;
    }

    public void setTotalTerm(int totalTerm) {
        this.totalTerm = totalTerm;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
