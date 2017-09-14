package com.example.drake.stamploadproject.CloudMessagingService;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by drake on 6/21/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String REG_TOKEN = "REG_TOKEN";
String app_server_url = "";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
       String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
    //    sendRegistrationToServer(refreshedToken);

       String token = FirebaseInstanceId.getInstance().getToken();
      // SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
     // SharedPreferences.Editor editor = sharedPreferences.edit();
      // editor.putString(getString(R.string.FCM_TOKEN), recent_token);
      //  editor.commit();
        registerToken(token);

     Toast.makeText(getApplicationContext(), token, Toast.LENGTH_LONG).show();
        Log.d(REG_TOKEN, "Refreshed token: " + token);
    }

    private void registerToken(String token) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", token)
                .build();

        Request request = new Request.Builder()
                .url("https://stampload1.000webhostapp.com/API/register.php")
                .post(body)
                 .build();
        try{
            client.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

