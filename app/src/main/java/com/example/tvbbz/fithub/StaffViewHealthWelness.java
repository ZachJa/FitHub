package com.example.tvbbz.fithub;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class StaffViewHealthWelness extends AppCompatActivity {

    private FloatingActionButton add;

    //For side nav menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;


    RecyclerView hwrecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_view_health_welness);
        getSupportActionBar().setTitle("Staff Health/Wellness");


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String filename = dataSnapshot.getKey();
                String url = dataSnapshot.getValue(String.class);
                ((adapterHW)hwrecyclerView.getAdapter()).update(filename,url);
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



        hwrecyclerView = findViewById(R.id.hwrecyclerview);
        hwrecyclerView.setLayoutManager(new LinearLayoutManager(StaffViewHealthWelness.this));
        adapterHW adapterhw = new adapterHW(hwrecyclerView,StaffViewHealthWelness.this,new ArrayList<String>(),new ArrayList<String>());
        hwrecyclerView.setAdapter(adapterhw);





//Open Adding Page
        add = (FloatingActionButton) findViewById(R.id.openaddfile);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffViewHealthWelness.this,StaffHealthWellnessActivity.class);
                startActivity(intent);
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

                    case(R.id.navhealthandwelness):Intent intent1 = new Intent(getApplicationContext(), StaffViewHealthWelness.class);
                        startActivity(intent1);
                        break;

                    case(R.id.navupdateinfo):Intent intent2 = new Intent(getApplicationContext(),UpdateStaffActivity.class);
                        startActivity(intent2);
                        break;

                    case(R.id.addequip):Intent intent4 = new Intent(getApplicationContext(),StaffEquipment.class);
                        startActivity(intent4);
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
}
