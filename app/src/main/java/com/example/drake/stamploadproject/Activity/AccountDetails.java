package com.example.drake.stamploadproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.drake.stamploadproject.Class.ClientRegistrationDetails;
import com.example.drake.stamploadproject.helper.DatabaseHelper;
import com.example.drake.stamploadproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountDetails extends AppCompatActivity {
private TextView NameUser;
    private TextView emailUser;
    private TextView  DOB;
    private DatabaseHelper databaseHelper;
    private TextView Phone;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

databaseHelper = new DatabaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NameUser = (TextView)findViewById(R.id.Name);
        emailUser = (TextView)findViewById(R.id.Email);
        DOB = (TextView)findViewById(R.id.dob);
        Phone =(TextView)findViewById(R.id.phone);
Intent intent = getIntent();
        String userName = intent.getStringExtra("KeyName");
        String userEmail = intent.getStringExtra("KeyEmail");
        String userDOB = intent.getStringExtra("KeyPass");
        String userPhone = intent.getStringExtra("KeyPhone");


      //  NameUser.setText("UserName: " + userName);
      //  emailUser.setText("Email: " + userEmail);
      //  DOB.setText("Date of Birth: " + userDOB);
      //  Phone.setText("Phone Number: " + userPhone);

        SharedPreferences sharedPreferences1 = this.getSharedPreferences("KEY", Context.MODE_PRIVATE);

String LoginName = sharedPreferences1.getString("Key_LoginName", "");
        String LoginEmail = sharedPreferences1.getString("Key_LoginEmail", "");
        String LoginDOB = sharedPreferences1.getString("Key_LoginDOB", "");
        String LoginPhone = sharedPreferences1.getString("Key_LoginPhone", "");

          NameUser.setText("NAME: " + LoginName);
         emailUser.setText("Email: " + LoginEmail);
          DOB.setText("Date of Birth: " + LoginDOB);
         Phone.setText("Phone Number: " + LoginPhone);
       /* List<ClientRegistrationDetails> storedData = databaseHelper.getAllUser();

        NameUser.setText("welcome" + storedData);*/

        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            String phone = user.getProviderId();
            // Uri photoUrl = user.getPhotoUrl();
            emailUser.setText("Email:" + email);
           // NameUser.setText("Email:" + name);
        }*/
    //    String storedData = databaseHelper.getSingleEntry(StoredEmail);

        ClientRegistrationDetails info = new ClientRegistrationDetails();


//databaseReference.addChildEventListener(new ChildEventListener() {
//    @Override
//    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(dataSnapshot.exists() || user!= null){
//            ClientRegistrationDetails info = dataSnapshot.getValue(ClientRegistrationDetails.class);
//            String name = info.getClient_username();
//            String email = info.getClient_email();
//            String DOB = info.getClient_dob();
//            String phone = info.getClient_phone();
//
//            NameUser.setText("Name: " + name);
//            emailUser.setText("Email: " + email);
//
//        }
//    }
//
//    @Override
//    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//    }
//
//    @Override
//    public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//    }
//
//    @Override
//    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//    }
//
//    @Override
//    public void onCancelled(DatabaseError databaseError) {
//
//    }
//});


       // Name.setText("UserName: " + info.getClient_username());
      // String storeEmail = intent.getStringExtra("EmailKey");
        //emailUser.setText("User Email: " + storeEmail);

       // DOB.setText("Date of Birth: " + info.getClient_dob());

    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
