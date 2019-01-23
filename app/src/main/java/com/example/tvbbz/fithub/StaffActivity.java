package com.example.tvbbz.fithub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
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


    private Button signoutbutton;
    private TextView mcapacity;
    private ListView mupdates;
    private Button delupbutton;

    private ArrayList<String>allupdates = new ArrayList<>();

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseupdates;

    //For side nav menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        getSupportActionBar().setTitle("Staff/Home");


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


        //For Action Bar Nav Window
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mtoggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView mNavigationView = (NavigationView)findViewById(R.id.navmenu);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case(R.id.navhome): Intent intent = new Intent(getApplicationContext(),StaffActivity.class);
                        startActivity(intent);
                        break;

                    case(R.id.navhealthandwelness):Intent intent1 = new Intent(getApplicationContext(), StaffHealthWellnessActivity.class);
                        startActivity(intent1);
                        break;

                    case(R.id.navupdateinfo):Intent intent2 = new Intent(getApplicationContext(),UpdateStaffActivity.class);
                    startActivity(intent2);
                    break;


                }

                return true;

            }
        });


    }

    private void deleteupdate() {
        DatabaseReference delup = FirebaseDatabase.getInstance().getReference("updates");
        delup.removeValue();
        Toast.makeText(this, "Update Deleted", Toast.LENGTH_SHORT).show();
    }

    private void signout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //For Action Bar Button Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





}
