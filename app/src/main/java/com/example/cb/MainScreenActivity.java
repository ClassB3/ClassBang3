package com.example.cb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cb.firebasefirestore.FireStoreService;
import com.example.cb.info.ClassInfo;
import com.example.cb.info.Student;
import com.example.cb.work.TestActivity;
import com.example.cb.work.WorkActivity_BankTeller;

public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener
{

    private Context context;

    private ClassInfo classInfo;
    private Student student;

    private TextView job_TextView;
    private Button work_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        getIntent();

        context=this;

        classInfo=ClassInfo.getInstance();
        student=Student.getInstance();

        FireStoreService.Class.getClassInfo();

        Log.d("??", student.getClassCode());
        Log.d("??", student.getStudentCode());
        Log.d("??", student.getRegion());
        Log.d("??", student.getSchool());
        Log.d("??", student.getGrade());
        Log.d("??", student.getNumber());
        Log.d("??", student.getName());
        Log.d("??", student.getJob());
        Log.d("??", student.getEmail());



        job_TextView=findViewById(R.id.MainScreenActivity_Text_Job);
        job_TextView.setText(student.getJob());
        work_Btn=findViewById(R.id.MainScreenActivity_Btn_Work);
        work_Btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.MainScreenActivity_Btn_Work:

                switch (student.getJob())
                {
                    case "은행원":
                        Log.d("??", "은행원");
                        startActivity(new Intent(context, WorkActivity_BankTeller.class));
                        break;
                    case "투자회사직원":
                        break;
                    case "무직":
                        Toast.makeText(context,"No Job",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
                break;
            default:
        }
    }
}