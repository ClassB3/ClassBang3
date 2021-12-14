package com.example.cb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.cb.firebasefirestore.FireStoreGetCallback;
import com.example.cb.firebasefirestore.FireStoreService;
import com.example.cb.info.ClassInfo;
import com.example.cb.info.Notice;
import com.example.cb.info.Student;

import java.text.ParseException;
import java.util.ArrayList;

public class MainLoadingActivity extends AppCompatActivity
{

    ClassInfo classInfo=ClassInfo.getInstance();
    Student student = Student.getInstance();

    ArrayList<Notice> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_loading);

        FireStoreService.CreditRating.getCreditScore();
        FireStoreService.Bank.getBalance();
        FireStoreService.Class.getTaxInfo();
        FireStoreService.Class.getNoticeList(new FireStoreGetCallback<Notice>()
        {
            @Override
            public void callback(Notice object) throws ParseException
            {
                list.add(object);
            }
        });

        start();
    }

    void start()
    {
        Handler handler =new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(new Intent(getApplicationContext(), MainScreenActivity.class).putExtra("noticeList",list));
            }
        },1250);
    }
}