package com.example.tvbbz.fithub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tvbbz.fithub.data.model.GeneralUpdate;
import com.example.tvbbz.fithub.data.model.GymCapacity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UpdateStaffActivity extends AppCompatActivity{

    private Button genupdatebutton;
    private EditText genupdate;
    private EditText capacity;
    private Button capacitybutton;


    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseupdate;

    //For side nav menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);
        getSupportActionBar().setTitle("Update Information");


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

                    case(R.id.navsignout):
                        FirebaseAuth.getInstance().signOut();
                        Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent3);
                        break;



                }

                return true;

            }
        });

    }

    //For Action Bar Button Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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
