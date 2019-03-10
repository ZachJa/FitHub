package com.example.tvbbz.fithub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class CalorieActivity extends AppCompatActivity {

    //For side nav menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;


    private EditText height,weight,gen, age;
    private int genint;
    private String showresult;

    private int finalvalue1;
    private int finalweight;
    private int finalage;
    private int calorie ;


    private Button calculate;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);
        getSupportActionBar().setTitle("Calorie Counter");

        //For Action Bar Nav Window
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mtoggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView mNavigationView = (NavigationView)findViewById(R.id.navmenu2);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case(R.id.navhome): Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        break;

                    case(R.id.navhealthandwelness):Intent intent1 = new Intent(getApplicationContext(), HealthWellnessActivity.class);
                        startActivity(intent1);
                        break;

                    case(R.id.equipment):Intent intent3 = new Intent(getApplicationContext(), UserEquiptment.class);
                        startActivity(intent3);
                        break;

                    case(R.id.location):Intent intent5 = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(intent5);
                        break;

                    case(R.id.about):Intent intent6 = new Intent(getApplicationContext(), AboutUs.class);
                        startActivity(intent6);
                        break;

                    case(R.id.contact):Intent intent4 = new Intent(getApplicationContext(), ContactActivity.class);
                        startActivity(intent4);
                        break;

                    case(R.id.navsignout):
                        FirebaseAuth.getInstance().signOut();
                        Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent2);
                        break;


                }

                return true;

            }
        });





        calculate = (Button) findViewById(R.id.calccal);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showresult = caloriecalculate();
                result.setText(showresult);
            }
        });



    }

    private String caloriecalculate() {

        String output;

        height = (EditText) findViewById(R.id.heightcalorie);
        weight = (EditText) findViewById(R.id.weightcalorie);
        gen = (EditText) findViewById(R.id.gendersel);
        genint = gen.getText().toString().length();
        age = (EditText) findViewById(R.id.calage);


        finalvalue1 = Integer.parseInt(height.getText().toString());


          //  calorie = (int) (((finalweight *4.35)+(finalvalue1*4.7)-(finalage*4.7))+655);

        output = "Calories: "+finalvalue1;
        return output;
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
