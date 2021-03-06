package com.example.cb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cb.account.AccountActivity;
import com.example.cb.account.AccountLog;
import com.example.cb.account.Saving;
import com.example.cb.firebasefirestore.FireStoreGetCallback;
import com.example.cb.firebasefirestore.FireStoreService;
import com.example.cb.info.ClassInfo;
import com.example.cb.info.Notice;
import com.example.cb.info.Student;
import com.example.cb.work.NoticeAdapter;
import com.example.cb.work.WorkActivity_BankTeller;
import com.example.cb.work.WorkActivity_CreditRatingStaff;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener
{

    private Context context;

    private ClassInfo classInfo;
    private Student student;

    private View IDCard_Layout;
    private ImageView profile_ImageView;
    private TextView number_TextView;
    private TextView name_TextView;
    private TextView school_TextView;
    private TextView job_TextView;
    private TextView creditScore_TextView;

    private Button logout_Btn;

    private Button work_Btn;

    private ImageView jobIcon_ImageView;
    private TextView jobWork_TextView;

    private TextView balance_TextView;
    private Button account_Btn;

    private TextView taxBalance;
    private TextView taxFormerBalance;


    private ArrayList<Notice> noticeList;
    private NoticeAdapter noticeAdapter;

    private ArrayList<AccountLog> logList;
    private ArrayList<Saving> savingList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        context=this;

        noticeList =(ArrayList<Notice>) getIntent().getSerializableExtra("noticeList");
        noticeAdapter = new NoticeAdapter(noticeList);

        logList= new ArrayList<>();
        savingList= new ArrayList<>();


        RecyclerView recyclerView = findViewById(R.id.MainScreenActivity_Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(noticeAdapter);


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

        profile_ImageView=findViewById(R.id.MainScreenActivity_Image_ProfileImage);
        number_TextView=findViewById(R.id.MainScreenActivity_Text_GradeClassNumber);
        number_TextView.setText(student.getGrade()+" "+student.getGroup()+" "+student.getNumber()+"???");
        name_TextView=findViewById(R.id.MainScreenActivity_Text_Name);
        name_TextView.setText(student.getName());
        job_TextView=findViewById(R.id.MainScreenActivity_Text_Job);
        job_TextView.setText(student.getJob());
        school_TextView=findViewById(R.id.MainScreenActivity_Text_School);
        school_TextView.setText(student.getSchool());
        creditScore_TextView=findViewById(R.id.MainScreenActivity_Text_CreditScore);
        creditScore_TextView.setText("????????????:"+String.valueOf(student.getCreditScore()));

        logout_Btn=findViewById(R.id.MainScreenActivity_Btn_Logout);
        logout_Btn.setOnClickListener(this);
        ////////////////////////////////////////////////////////////////////////////////
        jobIcon_ImageView= findViewById(R.id.MainScreenActivity_Image_JobIcon);
        jobWork_TextView=findViewById(R.id.MainScreenActivity_Text_JobInWork);
        if(student.getJob()=="??????")
            jobWork_TextView.setText("????????? ????????????...");
        else
            jobWork_TextView.setText(student.getJob()+" ????????????");


        work_Btn=findViewById(R.id.MainScreenActivity_Btn_Work);
        work_Btn.setOnClickListener(this);
        ////////////////////////////////////////////////////////////////////////////////
        balance_TextView=findViewById(R.id.MainScreenActivity_Text_AccountAmount);
        balance_TextView.setText("?????? ??????: "+String.valueOf(student.getBalance())+" ??????");
        account_Btn = findViewById(R.id.MainScreenActivity_Btn_Account);
        account_Btn.setOnClickListener(this);
        ////////////////////////////////////////////////////////////////////////////////
        taxBalance= findViewById(R.id.MainScreenActivity_Text_TodayTaxAmount);
        taxBalance.setText(classInfo.getTaxBalance()+" ??????");

        taxFormerBalance=findViewById(R.id.MainScreenActivity_Text_TaxAmountChange);

        double difference = classInfo.getTaxBalance()-classInfo.getFormerTaxBalance();
        double result= difference/classInfo.getFormerTaxBalance();

        if (result>=0)
            taxFormerBalance.setText("?????? ?????? +"+result*100+"% ??????");
        else
            taxFormerBalance.setText("?????? ?????? "+result*100+"% ??????");



        switch (student.getStudentCode())
        {
            case "1912020025":
                profile_ImageView.setImageResource(R.drawable.profile01);
                break;
            case "1912020026":
                profile_ImageView.setImageResource(R.drawable.profile02);
                break;
            case"1912020027":
                profile_ImageView.setImageResource(R.drawable.profile03);
                break;
            case "1912020028":
                profile_ImageView.setImageResource(R.drawable.profile04);
                break;
            case "1912020029":
                profile_ImageView.setImageResource(R.drawable.profile05);
                break;
            default:
        }


        switch (student.getJob())
        {
            case "?????????":
                jobIcon_ImageView.setImageResource(R.drawable.bankicon);
                break;
            case "??????????????????":
                break;
            case"??????????????????":
                jobIcon_ImageView.setImageResource(R.drawable.creditscoreicon);
                break;
            case "??????":
                jobIcon_ImageView.setImageResource(R.drawable.joblessicon);
                break;
            default:
        }

        getLogHistory();
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.MainScreenActivity_Btn_Work:

                switch (student.getJob())
                {
                    case "?????????":
                        Log.d("??", "?????????");
                        startActivity(new Intent(context, WorkActivity_BankTeller.class));
                        break;
                    case "??????????????????":
                        break;
                    case"??????????????????":
                        ArrayList<String> list=new ArrayList<>(classInfo.getStudentMap().values());
                        startActivity(new Intent(context, WorkActivity_CreditRatingStaff.class).putExtra("studentList",list));
                        break;
                    case "??????":
                        Toast.makeText(context,"???????????????...!",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
                break;

            case R.id.MainScreenActivity_Btn_Account:

                //FireStoreService.Bank.getBalance();
                Intent intent = new Intent(context, AccountActivity.class);
                intent.putExtra("logList", logList);
                intent.putExtra("savingList", savingList);
                startActivity(intent);
                break;

            case R.id.MainScreenActivity_Btn_Logout:
                FireStoreService.Auth.signOut();
                startActivity(new Intent(context,LoginActivity.class));
            default:
        }
    }


    void getLogHistory()
    {
        FireStoreService.Bank.getLogHistory(new FireStoreGetCallback<AccountLog>()
        {
            @Override
            public void callback(AccountLog object) throws ParseException
            {
                logList.add(object);
            }
        },"OrdinaryAccount");

        FireStoreService.Bank.seeUserSavingState(new FireStoreGetCallback<Saving>()
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

                savingList.add(object);
            }
        }, student.getNumber(), student.getName());
    }

}

