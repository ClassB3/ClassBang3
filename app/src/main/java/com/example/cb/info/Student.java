package com.example.cb.info;

public class Student
{
    private static Student instance = new Student();

    private String region;
    private String school;
    private String grade;
    private String group;

    private String classCode;
    private String studentCode;
    private String email;
    private String name;
    private String number;

    private String job;
    private int creditScore;

    private double balance;

    private Student() {}

    public static Student getInstance() { return instance; }

    public void initializeInfo()
    {
        instance.classCode=null;
        instance.studentCode=null;
        instance.email=null;
        instance.name=null;
        instance.number=null;
        instance.job=null;
        instance.creditScore=0;
    }

    public String getRegion() { return instance.region; }
    public String getSchool() { return instance.school; }
    public String getGrade() { return instance.grade; }
    public String getGroup() { return group; }
    public String getClassCode() { return instance.classCode; }
    public String getStudentCode() { return instance.studentCode; }
    public String getEmail() { return instance.email; }
    public String getName() { return instance.name; }
    public String getNumber() { return instance.number; }
    public String getJob() { return instance.job; }
    public int getCreditScore() { return instance.creditScore; }
    public double getBalance() { return  instance.balance ; }

    public void setRegion(String region) {
        instance.region = region;
    }

    public void setSchool(String school) {
        instance.school = school;
    }

    public void setGrade(String grade) {
        instance.grade = grade;
    }

    public void setGroup(String group) { instance.group = group; }

    public void setClassCode(String classCode) {
        instance.classCode = classCode;
    }

    public void setStudentCode(String studentCode) {
        instance.studentCode = studentCode;
    }

    public void setEmail(String email) {
        instance.email = email;
    }

    public void setName(String name) {
        instance.name = name;
    }

    public void setNumber(String number) {
        instance.number = number;
    }

    public void setJob(String job) {
        instance.job = job;
    }

    public void setCreditScore(int creditScore) {
        instance.creditScore = creditScore;
    }

    public void setBalance(double balance) { instance.balance = balance; }
}
