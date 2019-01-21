package com.example.tvbbz.fithub;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tvbbz.fithub.data.model.GeneralUpdate;
import com.example.tvbbz.fithub.data.model.GymCapacity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateStaffActivity extends AppCompatActivity{

    private Button genupdatebutton;
    private EditText genupdate;
    private EditText capacity;
    private Button capacitybutton;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseupdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);
        getSupportActionBar().hide();


// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseupdate = FirebaseDatabase.getInstance().getReference("updates");


        //Updating Gym Information

        genupdatebutton = findViewById(R.id.genupdatebutton);
        genupdate = findViewById(R.id.genupdate);

        genupdatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               generalupdate(genupdate.getText().toString());
                Toast.makeText(UpdateStaffActivity.this,"Update Successful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateStaffActivity.this, StaffActivity.class));

            }
        });

        //Updating Gym Capacity
        capacity = findViewById(R.id.gymcapacity);
        capacitybutton = findViewById(R.id.gymcapacitybutton);

        capacitybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gymcapacity(capacity.getText().toString());
                Toast.makeText(UpdateStaffActivity.this,"Update Successful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateStaffActivity.this, StaffActivity.class));
            }
        });


    }



    protected void gymcapacity(String updatecapacity)
    {
        GymCapacity capacity = new GymCapacity();
        capacity.setCapacity(updatecapacity);

        mDatabase.child("capacity").setValue(capacity);
    }

    protected void generalupdate(String updatetext)
    {
        /*GeneralUpdate update = new GeneralUpdate();
        update.setUpdate(updatetext);*/

        mDatabaseupdate.push().setValue(updatetext);

      /*mDatabaseupdate.child("update").setValue(update);*/
    }


}
