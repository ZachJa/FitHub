package com.example.tvbbz.fithub;

import android.content.Intent;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class StaffEquipment extends AppCompatActivity implements ImageAdapter.OnItemClickListenerimage{

    private FloatingActionButton addequip;

    private RecyclerView mrecyclerview;
    private ImageAdapter imageAdapter;

    private FirebaseStorage mstorage;
    private DatabaseReference mdatabaseref;
    private ValueEventListener mdblistener;
    private List<Upload> muploads;



    //For side nav menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_equipment);
        getSupportActionBar().setTitle("Equipment");

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

        mrecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        muploads = new ArrayList<>();
        imageAdapter = new ImageAdapter(StaffEquipment.this,muploads);
        mrecyclerview.setAdapter(imageAdapter);
        imageAdapter.setOnItemCLickListenerimage(StaffEquipment.this);

        mdatabaseref = FirebaseDatabase.getInstance().getReference("equipment");
        mstorage = FirebaseStorage.getInstance();

        mdblistener = mdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                muploads.clear();

                for (DataSnapshot postsnapshot: dataSnapshot.getChildren()){
                    Upload upload = postsnapshot.getValue(Upload.class);
                    upload.setMkey(postsnapshot.getKey());
                    muploads.add(upload);
                }

                imageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(StaffEquipment.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        addequip = (FloatingActionButton) findViewById(R.id.addequipmentbutton);
        addequip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffEquipment.this, StaffAddEquipment.class);
                startActivity(intent);
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

    //For Delete
    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onDeleteCLick(int position) {
        Upload selitem = muploads.get(position);
        final String selkey = selitem.getMkey();

        StorageReference imageref  = mstorage.getReferenceFromUrl(selitem.getMimageurl());
        imageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mdatabaseref.child(selkey).removeValue();
                Toast.makeText(StaffEquipment.this, "Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mdatabaseref.removeEventListener(mdblistener);
    }
}
