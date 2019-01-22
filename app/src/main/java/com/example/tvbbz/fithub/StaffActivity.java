package com.example.tvbbz.fithub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvbbz.fithub.data.model.GymCapacity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class StaffActivity extends AppCompatActivity{

    private Button update;
    private Button signoutbutton;
    private TextView mcapacity;
    private ListView mupdates;
    private Button delupbutton;

    private ArrayList<String>allupdates = new ArrayList<>();

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseupdates;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        getSupportActionBar().setTitle("Staff Home Screen");


        //View Gym Capacity
        mcapacity = (TextView) findViewById(R.id.GymCapacity);
        mDatabase = FirebaseDatabase.getInstance().getReference("capacity/capacity");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);
                mcapacity.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //View Gym Updates
        mupdates = (ListView) findViewById(R.id.updatedisplay);
        mDatabaseupdates = FirebaseDatabase.getInstance().getReference("updates");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allupdates);
        mupdates.setAdapter(arrayAdapter);

        mDatabaseupdates.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String value = dataSnapshot.getValue(String.class);
                allupdates.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Go to Update Page

        update = findViewById(R.id.updatepagebutton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openupdate();
            }
        });

        //Sign out

        signoutbutton = findViewById(R.id.signoutbuttonstaff);
        signoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });

        //Deleting
        delupbutton = findViewById(R.id.deleteupdatebutton);
        delupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteupdate();
                startActivity(new Intent(StaffActivity.this, StaffActivity.class));
            }
        });

    }

    private void deleteupdate() {
        DatabaseReference delup = FirebaseDatabase.getInstance().getReference("updates");
        delup.removeValue();
        Toast.makeText(this, "Update Deleted", Toast.LENGTH_SHORT).show();
    }

    private void openupdate()
    {
        Intent intent = new Intent(this,UpdateStaffActivity.class);
        startActivity(intent);
    }

    private void signout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }




}
