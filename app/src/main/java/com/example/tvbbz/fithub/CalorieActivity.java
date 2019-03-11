package com.example.tvbbz.fithub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class CalorieActivity extends AppCompatActivity {

    //For side nav menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;


    private EditText height,weight,gen, age;

    private int finalvalue1;
    private int finalweight;
    private int finalage;
    private String finalgen;
    private int calorie ;

    private CheckBox lose, maintain,gain;

    private Button calculate;
    private TextView result,carbs,protein,fats;

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


        result = (TextView) findViewById(R.id.calorieresult);
        height = (EditText) findViewById(R.id.heightcalorie);
        weight = (EditText) findViewById(R.id.weightcalorie);
        age = (EditText) findViewById(R.id.calage);
        gen = (EditText) findViewById(R.id.gendersel);
        lose = (CheckBox) findViewById(R.id.lose);
        maintain = (CheckBox) findViewById(R.id.maintain);
        gain = (CheckBox) findViewById(R.id.gain);
        carbs = (TextView) findViewById(R.id.carbs);
        protein = (TextView) findViewById(R.id.protein);
        fats = (TextView) findViewById(R.id.fats);

        calculate = (Button) findViewById(R.id.calccal);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (weight.getText().toString().trim().length() != 0 && height.getText().toString().trim().length() != 0 && age.getText().toString().trim().length() != 0) {

                    finalvalue1 = Integer.parseInt(String.valueOf(height.getText()));
                    finalweight = Integer.parseInt(String.valueOf(weight.getText()));
                    finalage = Integer.parseInt(String.valueOf(age.getText()));
                    finalgen = gen.getText().toString();

                    if (finalgen.length() == 6) {


                        if (lose.isChecked()) {
                            calorie = (int) (((finalweight * 4.35) + (finalvalue1 * 4.7) - (finalage * 4.7)) + 655 - 300);
                            String value = "Calories Recommended: " + (calorie);
                            result.setText(value);

                            //Protein Calculation
                            int proteinvalue = ((calorie / 100) * 40) / 4;
                            protein.setText("Protein: " + proteinvalue + "g");

                            //Carb Calculation
                            int carbvalue = ((calorie / 100) * 30) / 4;
                            carbs.setText("Carbohydrates: " + carbvalue + "g");

                            //Fat Calculation
                            int fatvalue = ((calorie / 100) * 30) / 9;
                            fats.setText("Fats: " + fatvalue + "g");

                        } else if (maintain.isChecked()) {
                            calorie = (int) (((finalweight * 4.35) + (finalvalue1 * 4.7) - (finalage * 4.7)) + 655);
                            String value = "Calories Recommended: " + (calorie);
                            result.setText(value);

                            //Protein Calculation
                            int proteinvalue = ((calorie / 100) * 30) / 4;
                            protein.setText("Protein: " + proteinvalue + "g");

                            //Carb Calculation
                            int carbvalue = ((calorie / 100) * 40) / 4;
                            carbs.setText("Carbohydrates: " + carbvalue + "g");

                            //Fat Calculation
                            int fatvalue = ((calorie / 100) * 30) / 9;
                            fats.setText("Fats: " + fatvalue + "g");

                        } else if (gain.isChecked()) {
                            calorie = (int) (((finalweight * 4.35) + (finalvalue1 * 4.7) - (finalage * 4.7)) + 655 + 300);
                            String value = "Calories Recommended: " + (calorie);
                            result.setText(value);

                            //Protein Calculation
                            int proteinvalue = ((calorie / 100) * 30) / 4;
                            protein.setText("Protein: " + proteinvalue + "g");

                            //Carb Calculation
                            int carbvalue = ((calorie / 100) * 50) / 4;
                            carbs.setText("Carbohydrates: " + carbvalue + "g");

                            //Fat Calculation
                            int fatvalue = ((calorie / 100) * 20) / 9;
                            fats.setText("Fats: " + fatvalue + "g");

                        }

                    } else if (finalgen.length() == 4) {
                        if (lose.isChecked()) {
                            calorie = (int) (((finalweight * 6.23) + (finalvalue1 * 12.7) - (finalage * 6.8)) + 66 - 300);
                            String value = "Calories Recommended: " + (calorie);
                            result.setText(value);

                            //Protein Calculation
                            int proteinvalue = ((calorie / 100) * 50) / 4;
                            protein.setText("Protein: " + proteinvalue + "g");

                            //Carb Calculation
                            int carbvalue = ((calorie / 100) * 30) / 4;
                            carbs.setText("Carbohydrates: " + carbvalue + "g");

                            //Fat Calculation
                            int fatvalue = ((calorie / 100) * 20) / 9;
                            fats.setText("Fats: " + fatvalue + "g");

                        } else if (maintain.isChecked()) {
                            calorie = (int) (((finalweight * 6.23) + (finalvalue1 * 12.7) - (finalage * 6.8)) + 66);
                            String value = "Calories Recommended: " + (calorie);
                            result.setText(value);

                            //Protein Calculation
                            int proteinvalue = ((calorie / 100) * 40) / 4;
                            protein.setText("Protein: " + proteinvalue + "g");

                            //Carb Calculation
                            int carbvalue = ((calorie / 100) * 40) / 4;
                            carbs.setText("Carbohydrates: " + carbvalue + "g");

                            //Fat Calculation
                            int fatvalue = ((calorie / 100) * 20) / 9;
                            fats.setText("Fats: " + fatvalue + "g");

                        } else if (gain.isChecked()) {
                            calorie = (int) (((finalweight * 6.23) + (finalvalue1 * 12.7) - (finalage * 6.8)) + 66 + 300);
                            String value = "Calories Recommended: " + (calorie);
                            result.setText(value);

                            //Protein Calculation
                            int proteinvalue = ((calorie / 100) * 35) / 4;
                            protein.setText("Protein: " + proteinvalue + "g");

                            //Carb Calculation
                            int carbvalue = ((calorie / 100) * 50) / 4;
                            carbs.setText("Carbohydrates: " + carbvalue + "g");

                            //Fat Calculation
                            int fatvalue = ((calorie / 100) * 15) / 9;
                            fats.setText("Fats: " + fatvalue + "g");

                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Enter Correct Gender", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Enter Values in All Fields", Toast.LENGTH_LONG).show();
                }
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
