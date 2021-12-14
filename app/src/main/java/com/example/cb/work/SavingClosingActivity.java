package com.example.cb.work;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.cb.R;
import com.example.cb.account.Saving;

import java.util.ArrayList;

public class SavingClosingActivity extends AppCompatActivity
{
    private Context context;
    private ArrayList<Saving> savingClosingList;
    private Button closing_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_closing);


    }
}