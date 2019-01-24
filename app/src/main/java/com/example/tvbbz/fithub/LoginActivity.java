package com.example.tvbbz.fithub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvbbz.fithub.data.StaffProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private TextView signup;
    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth firebaseAuth;
    private ProgressBar mprogress;

    private TextView forgotpassword;
    private DatabaseReference mdatabase;

    /* Email: zacharyparch@yahoo.com  Password: fithubadmin */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        email = (EditText) findViewById(R.id.emaillogin);
        password = (EditText) findViewById(R.id.passwordlogin);
        login = (Button) findViewById(R.id.loginbutton);

        firebaseAuth = FirebaseAuth.getInstance();

        //Progress Bar
        mprogress = (ProgressBar)findViewById(R.id.progressBar);
        mprogress.setVisibility(View.GONE);


        //Checking if user already logged in
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        //Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprogress.setVisibility(View.VISIBLE);
                validate(email.getText().toString(), password.getText().toString());
            }
        });

        //Open sign up page
        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensignup();
            }
        });

        //Forgotten Password
        forgotpassword = (TextView) findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openresetpassword();
            }
        });




    }

    private void openresetpassword(){
        Intent intent = new Intent(this,PasswordActivity.class);
        startActivity(intent);
    }


    private void opensignup(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //Validation of User Login

    private void validate(final String email, String password)
    {

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    if(StaffProvider.isStaffMember(email)){
                        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, StaffActivity.class));

                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }

                }
                else{
                    task.getException();
                    Log.e("Error","Error",task.getException());
                    Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }



            }
        });

    }



    }






