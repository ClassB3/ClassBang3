package com.example.cb.info;

import com.example.cb.good.InvestmentGoods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassInfo
{
    private static ClassInfo instance = new ClassInfo();
    private int theNumberOfStudent;

    private List<String> listOfSavingProduct= new ArrayList<>();
    private List<InvestmentGoods> listOfInvestmentGoods = new ArrayList<>();
    private HashMap<String,String> studentMap = new HashMap<>();

    private ClassInfo() {}

    public static ClassInfo getInstance() { return instance; }

    public int getTheNumberOfStudent() { return instance.theNumberOfStudent; }
    public List<String> getListOfSavingProduct() { return instance.listOfSavingProduct; }
    public List<InvestmentGoods> getListOfInvestmentGoods() { return instance.listOfInvestmentGoods; }
    public HashMap<String, String> getStudentMap() { return instance.studentMap; }

    public void setTheNumberOfStudent(int theNumberOfStudent) {
        instance.theNumberOfStudent = theNumberOfStudent;
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
