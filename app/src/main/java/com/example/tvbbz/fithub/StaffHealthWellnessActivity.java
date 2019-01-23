package com.example.tvbbz.fithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class StaffHealthWellnessActivity extends AppCompatActivity{

    //For side nav menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_health_wellness);
        getSupportActionBar().setTitle("Staff Health/Wellness");


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

    //For Action Bar Button Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
