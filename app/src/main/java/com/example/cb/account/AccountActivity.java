package com.example.cb.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.cb.R;
import com.example.cb.info.ClassInfo;
import com.example.cb.info.Student;
import com.example.cb.work.LogAdapter;
import com.example.cb.work.SavingAdapter;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity
{
    private ClassInfo classInfo;
    private Student student;

    private TextView name_TextView;
    private Button savingAccount_Btn;
    private Button ordinaryAccount_Btn;

    private TextView balance01_TextView;

    private Context context;

    private ArrayList<AccountLog> ordinaryAccountLog_list;
    private ArrayList<Saving> savingAccountLog_list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        context=this;
        classInfo=ClassInfo.getInstance();
        student=Student.getInstance();

        ordinaryAccountLog_list=(ArrayList<AccountLog>) getIntent().getSerializableExtra("logList");
        savingAccountLog_list=(ArrayList<Saving>) getIntent().getSerializableExtra("savingList");
        LogAdapter adapter01 = new LogAdapter(ordinaryAccountLog_list);

        Log.d("?????", ordinaryAccountLog_list.toString());
        Log.d("?????", savingAccountLog_list.toString());
        RecyclerView recyclerView01 = findViewById(R.id.AccountActivity_Recyclerview01);
        recyclerView01.setLayoutManager(new LinearLayoutManager(context));

        recyclerView01.setAdapter(adapter01);

        SavingAdapter adapter02 = new SavingAdapter(context ,savingAccountLog_list);

        RecyclerView recyclerView02 = findViewById(R.id.AccountActivity_Recyclerview02);
        recyclerView02.setLayoutManager(new LinearLayoutManager(context));

        recyclerView02.setAdapter(adapter02);

        name_TextView=findViewById(R.id.AccountActivity_Text_Name);
        name_TextView.setText(student.getName()+"의 계좌 내역");
        balance01_TextView=findViewById(R.id.AccountActivity_Text_Balance01);
        balance01_TextView.setText("잔액: "+student.getBalance());
    }
}