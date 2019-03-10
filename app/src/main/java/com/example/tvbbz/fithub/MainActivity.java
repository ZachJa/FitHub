package com.example.tvbbz.fithub;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Design Intialization
    private TextView mcapacity,mcapacity2,mcapacity3;
    private ListView updateshome;


    //Array reference
    private ArrayList<String> allupdates = new ArrayList<>();

    //Database References
    private DatabaseReference mdatabase;
    private DatabaseReference mdatabaseupdates;

    //For side nav menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;

    //Location Button
    private Button locationbutton;
    private Button mfp;

    //Images
    private ImageView express;
    private ImageView myfitpal;

    //Refresh
    private FloatingActionButton refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Home");

        //View Gym Capacity
        mcapacity = (TextView) findViewById(R.id.gymcapacity);
        mcapacity2 = (TextView) findViewById(R.id.gymcapacity2);
        mcapacity3 = (TextView) findViewById(R.id.gymcapacity3);
        mdatabase = FirebaseDatabase.getInstance().getReference("capacity/capacity");

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.length() == 5)
                {
                    //value.setTextColor(getResources().getColor(R.color.colorAccent));
                    mcapacity.setText(value);

                }else if(value.length() == 8)
                {
                    mcapacity2.setText(value);

                }else if(value.length()==4){

                    mcapacity3.setText(value);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //View Gym Updates
        updateshome = (ListView) findViewById(R.id.genupdatehome);
        updateshome.setFastScrollAlwaysVisible(true);
        mdatabaseupdates = FirebaseDatabase.getInstance().getReference("updates");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,allupdates);
        updateshome.setAdapter(arrayAdapter);
        mdatabaseupdates.addChildEventListener(new ChildEventListener() {
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

        //Refresh
        refresh = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

       //open myfitnesspal download
        mfp = (Button) findViewById(R.id.myfitnesspal);
        mfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://play.google.com/store/apps/details?id=com.myfitnesspal.android&hl=en";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        express = (ImageView)findViewById(R.id.expresspromo);
        myfitpal = (ImageView) findViewById(R.id.myfitpalpromo);

        express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://expressfitnessja.com/"));
                startActivity(intent);
            }
        });

        myfitpal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.myfitnesspal.com/"));
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

   /* //For Refresh Button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.refresh_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    */
}
