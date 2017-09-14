package com.example.drake.stamploadproject.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drake.stamploadproject.Class.ClientRegistrationDetails;
import com.example.drake.stamploadproject.R;
import com.example.drake.stamploadproject.helper.DatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Date;

public class RegisterClientActivity extends Activity implements View.OnClickListener {
    DatabaseHelper databasehelper;
    private ClientRegistrationDetails user;
private Button btnGoBackToGoLogin;
    private FirebaseAuth firebaseAuth;

    private EditText Username;
    private EditText Email;
    private EditText password;
    private EditText confPassword;
    private EditText DateOfBirth;
    private EditText Phone;
    private Button registerBtn;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //get instance of Database Helper
        databasehelper = new DatabaseHelper(this);

firebaseAuth = FirebaseAuth.getInstance();

       btnGoBackToGoLogin = (Button)findViewById(R.id.GoToLoginPage);
        Username = (EditText)findViewById(R.id.client_name) ;
        Email = (EditText)findViewById(R.id.client_email);
        password = (EditText)findViewById(R.id.client_password) ;
        confPassword = (EditText)findViewById(R.id.client_password_confirm);
        registerBtn = (Button)findViewById(R.id.btn_register);
        DateOfBirth = (EditText)findViewById(R.id.client_dateOfBirth);
        Phone = (EditText)findViewById(R.id.mobile_number);
        progressDialog = new ProgressDialog(this);

        registerBtn.setOnClickListener(this);

        btnGoBackToGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginClientActitivy.class);
                startActivity(intent);


            }
        });

    }
    private void RegisterFirebase(){
        String userName = Username.getText().toString().trim();
        String userEmail = Email.getText().toString().trim();
        String userPass = password.getText().toString().trim();
        String userConfPass = confPassword.getText().toString().trim();
        String userDoB = DateOfBirth.getText().toString().trim();
        String userPhone = Phone.getText().toString().trim();
        SharedPreferences sharedPreferences = this.getSharedPreferences("KEY", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name", userName);
        editor.putString("user_email", userEmail);
        editor.putString("user_pass", userPass);
        editor.putString("user_conf_pass", userConfPass);
        editor.putString("user_dob", userDoB);
        editor.putString("user_phone", userPhone);
        editor.commit();
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Please Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
         if(TextUtils.isEmpty(userPass)){
             Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
             return;

         }
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterClientActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterClientActivity.this, "Could not register. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void RegisterSQLite(){
        String userName = Username.getText().toString();
        String userEmail = Email.getText().toString();
        String userPass = password.getText().toString();
        String userConfPass = confPassword.getText().toString();
        String userDoB = DateOfBirth.getText().toString();
        String userPhone = Phone.getText().toString();


        if(userName.equals("") || (userPass.equals("") || userConfPass.equals("") || userEmail.equals("")
                || userDoB.equals("") || userPhone.equals(""))) {
            Toast.makeText(RegisterClientActivity.this, "Please Field up details", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!userPass.equals(userConfPass)){
            Toast.makeText(RegisterClientActivity.this, "password doesn't match", Toast.LENGTH_SHORT).show();
            return;
        }else {
            databasehelper.InsertUser(userName,userEmail, userPass, userDoB, userPhone);
            Toast.makeText(RegisterClientActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginClientActitivy.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View view) {

        if (view == registerBtn) {
            RegisterSQLite();
            RegisterFirebase();

        }
    }
}
