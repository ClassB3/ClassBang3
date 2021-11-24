package com.example.cb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cb.firebasefirestore.FireStoreService;
import com.example.cb.info.Student;

public class StudentCodeActivity extends AppCompatActivity
{

    private Context context;

    EditText studentCode_EditText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_code);

        getIntent();

        context=this;
        Student.getInstance().initializeInfo();

        studentCode_EditText=findViewById(R.id.studentcodeactivity_edittext_studentcode);
        button=findViewById(R.id.studentcodeactivity_button_ok);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FireStoreService.Auth.checkStudentCode(context,studentCode_EditText.getText().toString(),null);
            }
        });
    }
}