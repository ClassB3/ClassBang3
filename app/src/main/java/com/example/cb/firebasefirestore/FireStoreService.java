package com.example.cb.firebasefirestore;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cb.LoginActivity;
import com.example.cb.MainScreenActivity;
import com.example.cb.SignUpActivity;
import com.example.cb.StudentCodeActivity;
import com.example.cb.account.Account;
import com.example.cb.account.AccountLog;
import com.example.cb.info.ClassInfo;
import com.example.cb.info.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class FireStoreService
{
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static ClassInfo classInfo = ClassInfo.getInstance();
    private static Student student = Student.getInstance();

    public static class Auth
    {
        public static void checkStudentCode(Context context, String studentCode, String password)
        {
            if (studentCode.length()<=6)
            {
                Toast.makeText(context,"Invalid Student Code, Please re-enter",Toast.LENGTH_SHORT).show();
            }
            else
            {
                String classCode = studentCode.substring(0,6);
                db.collection("IntegratedManagement")
                        .document(classCode)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task)
                            {
                                DocumentSnapshot document_classCode = task.getResult();

                                if (document_classCode.exists())
                                {
                                    student.setClassCode(classCode);

                                    db.collection("IntegratedManagement/"+classCode+"StudentList")
                                            .document(studentCode)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                                            {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task)
                                                {
                                                    DocumentSnapshot document_studentCode = task.getResult();
                                                    if(document_studentCode.exists())
                                                    {
                                                        student.setStudentCode(document_studentCode.get("StudentCode").toString());
                                                        student.setRegion(document_studentCode.get("Region").toString());
                                                        student.setSchool(document_studentCode.get("School").toString());
                                                        student.setGrade(document_studentCode.get("Grade").toString());
                                                        student.setName(document_studentCode.get("Name").toString());
                                                        student.setNumber(document_studentCode.get("Number").toString());
                                                        student.setJob("무직");
                                                        student.setCreditScore(400);
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(context,"Not Student Code Found , Please re-enter",Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });
                                }
                                else
                                {
                                    Toast.makeText(context,"Not Class Code Found , Please re-enter",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        }

        private static void checkEmail(Context context, String classCode, String studentCode, String password)
        {
            db.collection("IntegratedManagement/"+classCode+"StudentList")
                    .document(studentCode)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            String email= task.getResult().get("Email").toString();

                            if (context instanceof LoginActivity)
                            {

                               if (email != "" && password.length() >=6)
                               {
                                   getJobInfo();
                                   signIn(context, email, password);
                               }
                                else if (password.length() <=6)
                               {
                                   Toast.makeText(context,"Please enter password!",Toast.LENGTH_SHORT).show();
                               }
                                else
                               {
                                   Toast.makeText(context,"Not Class Code Found , Please sign-up",Toast.LENGTH_SHORT).show();
                               }

                            }
                            else if (context instanceof StudentCodeActivity)
                            {
                                if ( email=="")
                                {
                                    context.startActivity(new Intent(context, SignUpActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(context,"StudentCode already enrolled, Please Log-in ",Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, LoginActivity.class));
                                }

                            }

                        }
                    });
        }

        private static void signIn(Context context, String email, String password)
        {
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Log.d("TAG", "signInWithEmail:success");

                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user.isEmailVerified())
                                {
                                    student.setEmail(user.getEmail());
                                    context.startActivity(new Intent(context, MainScreenActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(context,"Email-Auth needed",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                            }
                        }
                    });
        }

        private static void signUp(Context context, String email, String password)
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(context,"Sign-Up completed!",Toast.LENGTH_SHORT).show();

                                registerEmail(email);

                                FirebaseUser user = mAuth.getCurrentUser();

                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                Log.d("TAG", "Email sent");
                                            }
                                        });

                                enrollStudent();
                                context.startActivity(new Intent(context, LoginActivity.class));

                            }
                            else
                            {

                                Toast.makeText(context,"Sign-Up faild!",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


        }

        private static void registerEmail(String email)
        {
            db.collection("IntegratedManagement/"+student.getClassCode()+"StudentList")
                    .document(student.getStudentCode())
                    .update("Email", email);
        }

        private static void enrollStudent()
        {
            db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/"+"INFO/Student/StudentList/")
                    .document(student.getNumber()+student.getName())
                    .set(student);
        }

        private static void getJobInfo()
        {
            db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/"+"INFO/Student/StudentList/")
                    .document(student.getNumber()+student.getName())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (task.isSuccessful())
                                student.setJob(task.getResult().get("Job").toString());
                            else
                            {

                            }
                        }
                    });

        }

    }

    public static class Class
    {
        public static void getClassInfo()
        {
            db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/")
                    .document(student.getClassCode())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                HashMap<String,String> map = (HashMap<String, String>) task.getResult().get("StudentMap");
                                classInfo.setStudentMap(map);
                                classInfo.setTheNumberOfStudent(Integer.valueOf(task.getResult().get("TheNumberOfStudent").toString()));
                            }
                            else
                            {

                            }
                        }
                    });
        }
    }

    public static class Bank
    {
        private static List<String> list;

        public static void openAccount(Account account)
        {
            switch (account.getAccountType())
            {
                case "OrdinaryAccount":
                    db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/"+"INFO/Banking/OrdinaryAccount/")
                            .document(student.getNumber()+student.getName())
                            .set(account);

                    addAccountLog(account, new AccountLog(true,0), student.getNumber(), student.getName());
                    break;

                default:
            }
        }

        public static void addAccountLog(Account account, AccountLog log, String number, String name)
        {
            switch (account.getAccountType())
            {
                case "OrdinaryAccount":
                    db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+
                            "/"+"INFO/Banking/OrdinaryAccount/"+number+name+"/Logs")
                            .document(log.getDateOfTransaction()+"_"+log.getTimeOfTransaction())
                            .set(log);

                    break;
                case "SavingsAccount":
                    db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+
                            "/"+"INFO/Banking/SavingsAccount/"+number+name+"/Logs")
                            .document(log.getDateOfTransaction()+"_"+log.getTimeOfTransaction())
                            .set(log);

                    break;

                default:
            }
        }

        public static void deposit(Account account, double amount, String number, String name)
        {
            switch (account.getAccountType())
            {
                case "OrdinaryAccount":

                    db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+
                            "/"+"INFO/Banking/OrdinaryAccount")
                            .document(number+name)
                            .update("balance", FieldValue.increment(amount));

                    break;
                default:


            }
        }

        public static void withdraw(Account account, double amount, String number, String name)
        {
            switch (account.getAccountType())
            {
                case "OrdinaryAccount":

                    db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+
                            "/"+"INFO/Banking/OrdinaryAccount")
                            .document(number+name)
                            .update("balance", FieldValue.increment(-amount));

                    break;
                default:


            }

        }

    }

    public static class Investment
    {

    }

    public static class CreditRating
    {

    }
}
