package com.example.tvbbz.fithub;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class StaffHealthWellnessActivity extends AppCompatActivity{

    //For side nav menu
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;

    private Button sel,upload;
    private TextView notif;
    ProgressDialog progressDialog;
    private EditText filename;

    Uri pdfUri;

    private StorageReference mStorageRef;
    private DatabaseReference mdatabaseref;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_health_wellness);
        getSupportActionBar().setTitle("Staff Health/Wellness");

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mdatabaseref = FirebaseDatabase.getInstance().getReference();

        sel = (Button) findViewById(R.id.selectFile);
        upload = (Button) findViewById(R.id.Upload);
        notif = (TextView) findViewById(R.id.notification);
        filename = (EditText) findViewById(R.id.filename);


        sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(StaffHealthWellnessActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

                    selectPDF();

                }else {
                    ActivityCompat.requestPermissions(StaffHealthWellnessActivity.this, new String []{Manifest.permission.READ_EXTERNAL_STORAGE},9);

                }

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfUri!=null) {
                    uploadFile(pdfUri);
                }else {
                    Toast.makeText(StaffHealthWellnessActivity.this,"Select a File to Upload",Toast.LENGTH_SHORT).show();
                }
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

    private void uploadFile(final Uri pdfUri) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File ..");
        progressDialog.setProgress(0);
        progressDialog.show();



       // final String fileNAME = filename.getText().toString()+".pdf";
        //final String fileNAME1 = filename.getText().toString();

        final String fileNAME =  System.currentTimeMillis()+"";
        final String fileNAME1 = filename.getText().toString();

        //pdfUri.getLastPathSegment().toString()

        final StorageReference storageReference = mStorageRef.getRoot();
        storageReference.child("uploads").child(fileNAME).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

              Task<Uri> url = taskSnapshot.getMetadata().getReference().getDownloadUrl();
              url.addOnSuccessListener(new OnSuccessListener<Uri>() {
                  @Override
                  public void onSuccess(Uri uri) {

                      String fileurl = uri.toString();

                      DatabaseReference databaseReference = mdatabaseref.getRef();
                      databaseReference.child("uploads").child(fileNAME1).setValue(fileurl).addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              if(task.isSuccessful()){
                                  Toast.makeText(StaffHealthWellnessActivity.this,"File Sucessfully Uploaded", Toast.LENGTH_SHORT).show();
                                  Intent intent = new Intent(StaffHealthWellnessActivity.this,StaffViewHealthWelness.class);
                                  startActivity(intent);
                              }else {

                                  Toast.makeText(StaffHealthWellnessActivity.this,"File NOT Sucessfully Uploaded", Toast.LENGTH_SHORT).show();

                              }
                          }
                      });
                  }
              });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StaffHealthWellnessActivity.this,"File NOT Sucessfully Uploaded", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentprogress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());

                progressDialog.setProgress(currentprogress);

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectPDF();
        }else {
            Toast.makeText(StaffHealthWellnessActivity.this,"Please provide permission",Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPDF() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      if(requestCode== 86 && resultCode == RESULT_OK && data!=null){
            pdfUri = data.getData();
            notif.setText("File Selected : ");
        }else {
            Toast.makeText(StaffHealthWellnessActivity.this,"Select File",Toast.LENGTH_SHORT).show();
        }


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
