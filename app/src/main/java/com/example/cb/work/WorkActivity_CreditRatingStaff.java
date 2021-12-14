package com.example.cb.work;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.cb.R;
import com.example.cb.firebasefirestore.FireStoreService;

import java.util.ArrayList;

public class WorkActivity_CreditRatingStaff extends AppCompatActivity
{
    private Context context;
    private ArrayList<String> studentList;
    private String[] array;

    private NumberPicker.OnValueChangeListener valueChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_credit_rating_staff);

        context=this;

        array = new String[19];
        int count=0;

        for (int i=-9;i<=9;i++)
        {
            array[count++]=String.valueOf(i);
        }

        studentList=(ArrayList<String>) getIntent().getSerializableExtra("studentList");

        Log.d("???",studentList.toString());

        CreditScoreAdapter adapter = new CreditScoreAdapter(studentList);

        RecyclerView recyclerView = findViewById(R.id.WorkActivity_CreditRatingStaff_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CreditScoreAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                enterCreditScore(position,studentList.get(position));
            }
        });


    }

    public void enterCreditScore(int number, String name)
    {



        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        View view = this.getLayoutInflater().inflate(R.layout.activity_work_credit_rating_staff_dialog_score,null);

        NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.WorkActivity_CreditRatingStaff_Dialog_picker_Score);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(18);
        numberPicker.setValue(9);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDisplayedValues(array);



        builder.setView(view)
                .setTitle(name+"의 신용점수 입력!")
                .setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        int score=numberPicker.getValue()-9;
                        FireStoreService.CreditRating.rateCreditScore(score,String.valueOf(number+1),name);
                    }
                })
                .setNeutralButton("다음", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        int score=numberPicker.getValue()-9;
                        FireStoreService.CreditRating.rateCreditScore(score,String.valueOf(number+1),name);

                        if (number+1<studentList.size())
                            enterCreditScore(number+1,studentList.get(number+1));
                        else
                            Toast.makeText(context, "입력 끝", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

        builder.create();
        builder.show();

    }
}