package com.example.cb.info;

import com.example.cb.good.InvestmentGoods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassInfo
{
    private static ClassInfo instance = new ClassInfo();
    private int theNumberOfStudent;

    private int taxBalance;
    private int formerTaxBalance;

    private List<Notice> listOfNotice = new ArrayList<>();

    private List<String> listOfSavingProduct= new ArrayList<>();
    private List<InvestmentGoods> listOfInvestmentGoods = new ArrayList<>();
    private HashMap<String,String> studentMap = new HashMap<>();

    private ClassInfo() {}

    public static ClassInfo getInstance() { return instance; }

    public int getTheNumberOfStudent() { return instance.theNumberOfStudent; }


    public int getTaxBalance() { return instance.taxBalance; }
    public int getFormerTaxBalance() { return instance.formerTaxBalance; }

    public List<Notice> getListOfNotice() { return listOfNotice; }

    public List<String> getListOfSavingProduct() { return instance.listOfSavingProduct; }
    public List<InvestmentGoods> getListOfInvestmentGoods() { return instance.listOfInvestmentGoods; }
    public HashMap<String, String> getStudentMap() { return instance.studentMap; }

    public void setTheNumberOfStudent(int theNumberOfStudent) {
        instance.theNumberOfStudent = theNumberOfStudent;
    }

    public void setTaxBalance(int taxBalance) {
        instance.taxBalance = taxBalance;
    }

    public void setFormerTaxBalance(int formerTaxBalance) {
        instance.formerTaxBalance = formerTaxBalance;
    }

    public void setListOfNotice(List<Notice> listOfNotice) {
        this.listOfNotice = listOfNotice;
    }

    public void setListOfSavingProduct(List<String> listOfSavingProduct) {
        instance.listOfSavingProduct = listOfSavingProduct;
    }

    public void setListOfInvestmentGoods(List<InvestmentGoods> listOfInvestmentGoods) {
        instance.listOfInvestmentGoods = listOfInvestmentGoods;
    }

    public void setStudentMap(HashMap<String, String> studentMap) {
        instance.studentMap = studentMap;
    }
}
