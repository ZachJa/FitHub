package com.example.tvbbz.fithub;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tvbbz.fithub.data.model.GeneralUpdate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateStaffActivity extends AppCompatActivity{

    private Button genupdatebutton;
    private EditText genupdate;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);
        getSupportActionBar().hide();


// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();

        genupdatebutton = findViewById(R.id.genupdatebutton);
        genupdate = findViewById(R.id.genupdate);

        genupdatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               generalupdate(genupdate.getText().toString());
            }
        });


    }

    protected void generalupdate(String updatetext)
    {
        GeneralUpdate update = new GeneralUpdate();
        update.setUpdate(updatetext);

        mDatabase.child("updates").setValue(update);    }


}
