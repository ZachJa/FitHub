package com.example.tvbbz.fithub;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText password, email;
    private Button registerbutton;
    private TextView userlogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");
        setupUIviews();


        firebaseAuth = FirebaseAuth.getInstance();

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //Upload Data to Database once all is true
                    String useremail = email.getText().toString().trim();
                    String userpassword = password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registration Sucessful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }else{
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }
        });

        //Navigate from Register Page back to Login Page
        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });



    }

    /* Function to Assign Variables */

    private void setupUIviews(){

        password = (EditText) findViewById(R.id.passwordinput);
        email = (EditText) findViewById(R.id.emailinput);
        registerbutton = (Button) findViewById(R.id.signupbutton);
        userlogin = (TextView) findViewById(R.id.loginfromregister);

    }

    /* Validate Registration Info */

    private Boolean validate (){

        Boolean result = false;

        //String namecheck = name.getText().toString();
        String passwordcheck = password.getText().toString();
        String emailcheck = email.getText().toString();


        if(passwordcheck.isEmpty() || emailcheck.isEmpty()){

            Toast.makeText(this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
        }
       else{
            result = true;
        }
        return result;
    }




}
