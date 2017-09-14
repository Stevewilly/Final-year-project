package com.example.drake.stamploadproject.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drake.stamploadproject.FeedBackInfo;
import com.example.drake.stamploadproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class feedbackActivityOne extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private Button saveFeedback;
     private EditText msgFeedback;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_one);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Feedback");

getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        saveFeedback = (Button)findViewById(R.id.btnSave);
        msgFeedback = (EditText) findViewById(R.id.feedbackMessage);

      databaseReference = FirebaseDatabase.getInstance().getReference();


        saveFeedback.setOnClickListener(this);


    }
    private void saveUserInformation(){
String Msg = msgFeedback.getText().toString().trim();
        FeedBackInfo userInfo = new FeedBackInfo(Msg);
      FirebaseUser user1 = firebaseAuth.getCurrentUser();

        databaseReference.child(user1.getUid()).setValue(userInfo);





        Toast.makeText(this, "Feedback sent", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == saveFeedback){
saveUserInformation();
        }
    }
}
