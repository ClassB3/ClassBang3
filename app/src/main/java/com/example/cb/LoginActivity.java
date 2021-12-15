package com.example.cb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cb.firebasefirestore.FireStoreService;
import com.example.cb.info.Student;

public class LoginActivity extends AppCompatActivity
{
    private Context context;

    private Student student;

    private EditText studentCode_EditText;
    private EditText password_EditText;

    private Button login_Btn;
    private Button signUp_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getIntent();

        context=this;
        student = Student.getInstance();
        student.initializeInfo();

        studentCode_EditText=findViewById(R.id.loginactivity_edittext_studentcode);
        password_EditText=findViewById(R.id.loginactivity_edittext_password);


        login_Btn=findViewById(R.id.loginactivity_button_login);
        signUp_Btn=findViewById(R.id.loginactivity_button_signup);

        login_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FireStoreService.Auth.checkStudentCode(context,studentCode_EditText.getText().toString(),password_EditText.getText().toString());
            }
        });

        signUp_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(context, StudentCodeActivity.class));
            }
        });

    }
}