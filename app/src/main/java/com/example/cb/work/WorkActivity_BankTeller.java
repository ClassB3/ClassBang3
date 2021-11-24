package com.example.cb.work;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cb.R;
import com.example.cb.account.Saving;
import com.example.cb.firebasefirestore.FireStoreGetCallback;
import com.example.cb.firebasefirestore.FireStoreService;
import com.example.cb.info.ClassInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WorkActivity_BankTeller extends AppCompatActivity implements View.OnClickListener
{

    private Context context;

    private ClassInfo classInfo;
    private Saving saving;

    private Button savingRegistration_Btn;
    private Button savingList_Btn;
    private Button savingClosing_Btn;

    private EditText savingAmount_Dialog_Edit;

    private Spinner numberSpinner;

    private ArrayAdapter<Integer> numberAdapter;

    private Integer[] numberArray;

    private ArrayList<Saving> savingList;
    private ArrayList<Saving> savingClosingList;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_bank_teller);

        getIntent();

        context =this;

        classInfo= ClassInfo.getInstance();
        calendar=Calendar.getInstance();

        savingRegistration_Btn= findViewById(R.id.WorkActivity_BankTeller_Btn_SavingRegistration);
        savingList_Btn= findViewById(R.id.WorkActivity_BankTeller_Btn_SavingList);
        savingClosing_Btn=findViewById(R.id.WorkActivity_BankTeller_Btn_SavingClosing);

        savingRegistration_Btn.setOnClickListener(this);
        savingList_Btn.setOnClickListener(this);
        savingClosing_Btn.setOnClickListener(this);

        savingList=new ArrayList<>();
        savingClosingList=new ArrayList<>();

        FireStoreService.Bank.getListOfSavingProduct();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        savingList.clear();
        savingClosingList.clear();
    }


    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        seeSavingState();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        savingList.clear();
        savingClosingList.clear();
        classInfo.getListOfSavingProduct().clear();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.WorkActivity_BankTeller_Btn_SavingRegistration:
                    saving=new Saving();
                    selectSavingDialog();
                    break;
            case R.id.WorkActivity_BankTeller_Btn_SavingList:
                    startActivity(new Intent(context,SavingStateActivity.class).putExtra("savingList",savingList));
                break;
            case R.id.WorkActivity_BankTeller_Btn_SavingClosing:
                //startActivity(new Intent(context,));
                break;
        }
    }

    private void seeSavingState()
    {
        FireStoreService.Bank.seeSavingState(new FireStoreGetCallback<Saving>()
        {
            @Override
            public void callback(Saving object) throws ParseException
            {
                Calendar today = Calendar.getInstance();
                today.setTime(new Date());

                Calendar dueDate = Calendar.getInstance();
                Date date_due = new SimpleDateFormat("yyyy-MM-dd").parse(object.getDueDate());
                dueDate.setTime(date_due);

                long dSec = (dueDate.getTimeInMillis()-today.getTimeInMillis())/1000;
                long dDay = dSec/(24*60*60);
                object.setdDay(String.valueOf(dDay));
                Log.d("??",object.toString() );


                if (dDay>0)
                    savingList.add(object);
                if (dDay<=0)
                    savingClosingList.add(object);
            }
        });
    }

    private void selectSavingDialog()
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(context);

        int size = classInfo.getListOfSavingProduct().size();
        String[] arrayForSaving= classInfo.getListOfSavingProduct().toArray(new String[size]);

        builder.
                setTitle("예금 상품 선택")
                .setSingleChoiceItems(arrayForSaving, -1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        saving.setType(arrayForSaving[which]);
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (saving.getType()==null)
                            Toast.makeText(context,"예금 상품을 선택하세요!",Toast.LENGTH_SHORT).show();
                        else
                        {
                            FireStoreService.Bank.getSavingProduct(new FireStoreGetCallback<Double>()
                            {
                                @Override
                                public void callback(Double object) throws ParseException
                                {
                                    saving.setRate(object);
                                }
                            },saving.getType());

                            enterAmountDialog();
                        }
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(context,"예금 상품 선택 취소",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
    }

    private void enterAmountDialog()
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(context);

        LayoutInflater inflater= this.getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_work_bank_teller_dialog_enter_amount,null);

        savingAmount_Dialog_Edit=view.findViewById(R.id.WorkActivity_BankTeller_Dialog_EnterAmount_Edit_Amount);

        builder
                .setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        int amount = Integer.valueOf(savingAmount_Dialog_Edit.getText().toString());
                        saving.setAmount(amount);

                        enterStudentNumberDialog();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(context,"예금 금액 입력 취소",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
    }

    private void enterStudentNumberDialog()
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(context);

        LayoutInflater inflater= this.getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_work_bank_teller_dialog_enter_number,null);
        numberArray = new Integer[classInfo.getTheNumberOfStudent()];

        for (int i=1; i<=classInfo.getTheNumberOfStudent();i++)
        {
            numberArray[i-1] =i;
        }

        numberSpinner=(Spinner) view.findViewById(R.id.WorkActivity_BankTeller_Dialog_EnterInfo_Spinner_Number);
        numberAdapter =new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,numberArray);
        numberSpinner.setAdapter(numberAdapter);
        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                saving.setNumber(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(context,"출번을 선택하세요!",Toast.LENGTH_SHORT).show();
            }
        });

        builder.
                setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        calendar.setTime(new Date());
                        calendar.add(calendar.DATE,90);

                        saving.setCloseOrNot(false);
                        saving.setRegistrationDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        saving.setDueDate(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                        saving.setBalance(0);
                        saving.setdDay("90");
                        saving.setPeriod(30);
                        saving.setTotalTerm(90);
                        saving.setName(classInfo.getStudentMap().get(saving.getNumber()));

                        checkDialog();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(context,"입력 취소",Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create().show();
    }

    private void checkDialog()
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(context);

        builder
                .setTitle("등록하시겠습니까?")
                .setTitle(saving.getName()+" 학생으로 가입을 진행하시겠습니까?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        savingList.clear();
                        savingClosingList.clear();
                        FireStoreService.Bank.enrollSaving(context,saving);
                        seeSavingState();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(context,"등록 취소",Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create().show();
    }
}