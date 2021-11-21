package com.example.cb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity
{

    private Context context;

    private EditText email_EditText;
    private EditText password_EditText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getIntent();

        context=this;

        email_EditText=findViewById(R.id.signupactivity_edittext_email);
        password_EditText=findViewById(R.id.signupactivity_edittext_password);
        button=findViewById(R.id.signupactivity_button_signup);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
    }
}