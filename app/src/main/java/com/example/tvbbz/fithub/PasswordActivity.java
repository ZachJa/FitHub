package com.example.tvbbz.fithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private EditText epasswordchange;
    private Button resetpassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        epasswordchange = (EditText) findViewById(R.id.etpasswordemail);
        resetpassword = (Button) findViewById(R.id.resetpasswordbutton);
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String useremail = epasswordchange.getText().toString().trim();

                if (useremail.equals(null)){
                    Toast.makeText(PasswordActivity.this,"Enter your Email",Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordActivity.this, "Password Reset Sent to Email", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
                            }else
                            {
                                Toast.makeText(PasswordActivity.this, "Error in Sending Password Reset Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

    }
}
