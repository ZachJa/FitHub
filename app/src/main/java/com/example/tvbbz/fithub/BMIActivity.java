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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class BMIActivity extends AppCompatActivity{

    //For side nav menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;

    //For Calculation
    private EditText weightinput;
    private EditText heightinput;
    private Button bmicalc;
    private TextView bmiresult;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        getSupportActionBar().setTitle("BMI Calculator");

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


        //Calculations
        weightinput = (EditText) findViewById(R.id.weightinput);
        heightinput = (EditText) findViewById(R.id.heightinput);
        bmicalc = (Button) findViewById(R.id.bmibutton);
        bmicalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();

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

    //Calculate BMI
    public void calculateBMI(){

        String heightstring = heightinput.getText().toString();
        String weightstring = weightinput.getText().toString();

        if(heightstring != null && !"".equals(heightstring) &&
                weightstring != null && !"".equals(weightstring)){

            float weightvalue = Float.parseFloat(weightstring) * 703;
            float heightvalue = Float.parseFloat(heightstring);

            float bmi = weightvalue /(heightvalue * heightvalue);

            displayBMI(bmi);
        }
    }

    //Compare Results
    private void displayBMI(float bmi){
        String bmilabel = "";
        bmiresult = (TextView) findViewById(R.id.bmiresult);
        String label ="Contact Me";

        if(Float.compare(bmi, 15f)<=0){
            bmilabel = getString(R.string.very_severely_under_weight);
        }else if(Float.compare(bmi, 15f)>0 && Float.compare(bmi, 16f)<=0){
            bmilabel = getString(R.string.severely_under_weight);
        }else if(Float.compare(bmi, 16f)>0 && Float.compare(bmi, 18.5f)<=0){
            bmilabel = getString(R.string.under_weight);
        }else if(Float.compare(bmi, 18.5f)>0 && Float.compare(bmi, 25f)<=0){
            bmilabel = getString(R.string.normal);
        }else if(Float.compare(bmi, 25f)>0 && Float.compare(bmi, 30f)<=0){
            bmilabel = getString(R.string.overweight);
        }else if(Float.compare(bmi, 30f)>0 && Float.compare(bmi, 35f)<=0){
            bmilabel = getString(R.string.obese);
        }else if(Float.compare(bmi, 35f)>0 && Float.compare(bmi, 40f)<=0){
            bmilabel = getString(R.string.obese2);
        }else{
            bmilabel = getString(R.string.obese3);
        }

        bmilabel = bmi + " : " + bmilabel;
        bmiresult.setText(bmilabel);
    }






}
