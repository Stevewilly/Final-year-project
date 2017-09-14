package com.example.drake.stamploadproject.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drake.stamploadproject.Class.ClientRegistrationDetails;
import com.example.drake.stamploadproject.R;
import com.example.drake.stamploadproject.helper.DatabaseHelper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginClientActitivy extends FragmentActivity implements View.OnClickListener {
    private ProgressDialog progressDialog;
    DatabaseHelper databaseHelper;
    ClientRegistrationDetails user;
    private Button GoBacktoRegisterPage;
    private Button LoginButton;
    private EditText LoginEmail;
    private EditText LoginPassword;
    private Button forgetPassword;
    private EditText getEmail;
    private EditText getPass;
    private Button ok;
    private Button cancel;
    private Button LogOut;
  com.facebook.login.widget.LoginButton facebookLoginBtn;
    CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private static final String TAG = "GoogleActivity";
private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button GoogleSignButton;

    SQLiteDatabase db;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
       FirebaseApp.initializeApp(this);
        setContentView(R.layout.login);
      //  mAuth = FirebaseAuth.getInstance();
      //  GoogleSignButton = (Button)findViewById(R.id.google_sign);
        // firebase auth
progressDialog = new ProgressDialog(this);


   /*  mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(getApplicationContext(), "Already Log In",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


//configure google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this  , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }

                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


      *//*  mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                   //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //Intent intent = new Intent (getApplicationContext(),MainActivity.class);
                   // startActivity(intent);
                    //Toast.makeText(getApplicationContext(), "You have already Signed In", Toast.LENGTH_SHORT).show();

                    if(user.getDisplayName() != null)
                    {

                    }
                }
                else{
                    //User sign out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(getApplicationContext(), "You have Sign Out", Toast.LENGTH_SHORT).show();
                }
            }
        };
*//*GoogleSignButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       // signIn();
    }
});*/
        //create adapter instance
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();
        //view implementation
        GoBacktoRegisterPage = (Button)findViewById(R.id.goBackRegister);
        LoginButton = (Button)findViewById(R.id.btn_login);
        LoginEmail = (EditText) findViewById(R.id.login_email);
        LoginPassword = (EditText)findViewById(R.id.login_password);
        forgetPassword = (Button)findViewById(R.id.forget_password);
        facebookLoginBtn = (com.facebook.login.widget.LoginButton) findViewById(R.id.login_button_facebook);
/*
if(mAuth.getCurrentUser() != null){
    //get profileActivity

    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    startActivity(intent);

}*/
       callbackManager = CallbackManager.Factory.create();
        facebookLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
Toast.makeText(getApplicationContext(), "Login Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        LoginButton.setOnClickListener(this);



        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// define dialog
                final Dialog dialog = new Dialog(LoginClientActitivy.this);
                dialog.setContentView(R.layout.forget_search);

                dialog.setTitle("Forget Password?");

                dialog.show();
                getEmail = (EditText)dialog.findViewById(R.id.email_recovery);
                getPass = (EditText)dialog.findViewById(R.id.password_recovery);
                ok = (Button)dialog.findViewById(R.id.ok);
                cancel = (Button)dialog.findViewById(R.id.cancel);
              ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    String email = getEmail.getText().toString();
                        if(email.equals("")){
                            Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
                        }else {
                           final Dialog dialog2 = new Dialog(getApplicationContext());

                            dialog2.setContentView(R.layout.get_password);
                            dialog2.setTitle("password");
                          String storedData1 = databaseHelper.getSingleEntry(email);
                            if(storedData1 == null){
                                Toast.makeText(getApplicationContext(), "Please enter correct email", Toast.LENGTH_SHORT).show();
                            }else{
                                Log.d("Get Password", storedData1);
                                getPass.setText(storedData1);
                            }
                        }

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        GoBacktoRegisterPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterClientActivity.class);
                startActivity(intent);
            }
        });
    }


  //  @Override
   /* protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       callbackManager.onActivityResult(requestCode, resultCode, data);

         //
          if(requestCode == RC_SIGN_IN){
              GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
               result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
              if(result.isSuccess()){
                  GoogleSignInAccount account = result.getSignInAccount();
                  firebaseAuthWithGoogle(account);
              } else{

              }
          }

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Intent intent = new Intent (getApplicationContext(), LoginClientActitivy.class);
                startActivity(intent);

            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        }else {

                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }*/

  /*  @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }*/
   private void userLogin(){
        String Login_email = LoginEmail.getText().toString().trim();
        String Login_password = LoginPassword.getText().toString().trim();
        if(TextUtils.isEmpty(Login_email)){
            Toast.makeText(this, "Please Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Login_password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;

        }
        progressDialog.setMessage("Login user...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(Login_email, Login_password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //finish();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginClientActitivy.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private void SharePreferenceLogin(){
        String Login_email = LoginEmail.getText().toString();
        String Login_password = LoginPassword.getText().toString();
        SharedPreferences sharedPreferences = this.getSharedPreferences("KEY", MODE_PRIVATE);
        String email = sharedPreferences.getString("user_email", null);
        String pass = sharedPreferences.getString("user_pass", null);
        if(Login_email.equals(email) && Login_password.equals(pass)){
            Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(getApplicationContext(), "Failed Login", Toast.LENGTH_SHORT).show();
        }
    }

private void UserLogin() {

    String Login_email = LoginEmail.getText().toString();
    String Login_password = LoginPassword.getText().toString();

    String storedData = databaseHelper.getSingleEntry(Login_email);


    if (Login_password.equals(storedData)) {
        Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);


        Cursor cursor = db.rawQuery("SELECT *FROM " + databaseHelper.TABLE_NAME + " WHERE " + databaseHelper.COLUMN_CLIENT_EMAIL + "=? AND " + databaseHelper.COLUMN_CLIENT_PASSWORD + "=?", new String[]{Login_email, Login_password});


        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();


                String email = cursor.getString(cursor.getColumnIndex(databaseHelper.COLUMN_CLIENT_EMAIL));
                String name = cursor.getString(cursor.getColumnIndex(databaseHelper.COLUMN_CLIENT_NAME));
                String DOB = cursor.getString(cursor.getColumnIndex(databaseHelper.COLUMN_CLIENT_DOB));
                String phone = cursor.getString(cursor.getColumnIndex(databaseHelper.COLUMN_CLIENT_PHONE));
                SharedPreferences sharedPreferences1 = this.getSharedPreferences("KEY", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("Key_LoginEmail", email);
                editor.putString("Key_LoginName", name);
                editor.putString("Key_LoginDOB", DOB);
                editor.putString("Key_LoginPhone", phone);
                editor.commit();
                Intent intent1 = new Intent(getApplicationContext(), AccountDetails.class);
                intent1.putExtra("KeyName", name);
                intent1.putExtra("KeyEmail", email);
                intent1.putExtra("KeyDOB", DOB);
                intent1.putExtra("KeyPhone", phone);
                startActivity(intent1);
                finish();
            }
        }
        startActivity(intent);
    } else
        Toast.makeText(getApplicationContext(), "Please provide correct email and password", Toast.LENGTH_SHORT).show();



}

    @Override
    public void onClick(View view) {
        if( view == LoginButton){
         //  userLogin();
          UserLogin();
            SharePreferenceLogin();
        }
    }
}
