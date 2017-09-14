package com.example.drake.stamploadproject.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.drake.stamploadproject.Adapter.CardAdapter;
import com.example.drake.stamploadproject.Class.Badges;
import com.example.drake.stamploadproject.Class.Config;
import com.example.drake.stamploadproject.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pink_house_Card extends AppCompatActivity implements View.OnClickListener {
    public static final String CardPrefDetails = "MyPrefs";
    private EditText validationCode;
    List<Badges> badgesList = new ArrayList<>();
    private Button GainStamp;
    private SharedPreferences sharedPref;
    private TextView Merchant_name;
    private TextView Reward_details;
    private TextView End_Date;
    private Button QRCodeScanner;
    private ImageView photo;
    private Button Cancel;
    private IntentIntegrator qrScan;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        sharedPref = this.getPreferences(MODE_PRIVATE);
        validationCode = (EditText) findViewById(R.id.code_number1);
        GainStamp = (Button) findViewById(R.id.gain_stamp1);
        Merchant_name = (TextView) findViewById(R.id.MerchantName);
        Reward_details = (TextView) findViewById(R.id.rewards_details);
        End_Date = (TextView) findViewById(R.id.End_Date);
        Cancel = (Button) findViewById(R.id.cancel_card1);
        photo = (ImageView) findViewById(R.id.logoCard);
        QRCodeScanner = (Button)findViewById(R.id.scan_qr1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //intializing scan object


        badgesList = getAllItemList(sharedPref.getInt("BudgesCount", 0));
        GridLayoutManager gridlayout = new GridLayoutManager(Pink_house_Card.this, 4);

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view3);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gridlayout);
        CardAdapter rcAdapt = new CardAdapter(Pink_house_Card.this, badgesList);
        rView.setAdapter(rcAdapt);
/*
        QRCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });*/

        GainStamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sharedPref.getInt("BudgesCount", 0);
                count++;
               // String dataFromDataBase = sharedPref.getString("code_Key", "");
                String EnterCode = validationCode.getText().toString().trim();
                String dataFromDataBase = sharedPref.getString("key_code", "");
                if (count < badgesList.size() + 1) {
                    // String EnterCode = validationCode.getText().toString().trim();
                    if (EnterCode.equals(dataFromDataBase)) {
                        updateGridView(badgesList, count);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("BudgesCount", count);
                        editor.commit();
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("StampLoad");
                        builder.setIcon(R.drawable.happyicon);
                        builder.setMessage("Stamp Earned");
                        builder.show();
                    }  else if (!(EnterCode.equals(dataFromDataBase))) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                        alert.setIcon(R.drawable.unhappyicon);
                        alert.setMessage("Please enter valid code");
                        alert.show();
                    }
                    }  else if ((count > badgesList.size() +1) && (EnterCode.equals(dataFromDataBase))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Stamp Load");
                    builder.setIcon(R.drawable.unhappyicon);
                    builder.setMessage("You have earned all the stamps. \n Directly Join your Merchant shop to get your gift.");
                    builder.show();
                }
                else  {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                   builder.setTitle("StampLoad");
                    builder.setIcon(R.drawable.unhappyicon);
                    builder.setMessage("You have earned all the stamps. \n Directly Join your Merchant shop to get your gift.");
                    builder.show();
                }
                // update shared preferences


            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        qrScan = new IntentIntegrator(this);
        QRCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });
         getData();
        getCode(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(Pink_house_Card.this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    int count = sharedPref.getInt("BudgesCount", 0);
                    count++;
                    // String dataFromDataBase = sharedPref.getString("code_Key", "");

                    if (count < badgesList.size() + 1) {
                        // String EnterCode = validationCode.getText().toString().trim();
                        if (result.getContents() == "pinkhouse") {
                            updateGridView(badgesList, count);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putInt("BudgesCount", count);
                            editor.commit();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                            builder.setTitle("StampLoad");
                            builder.setIcon(R.drawable.happyicon);
                            builder.setMessage("Stamp Earned");
                            builder.show();
                    }  else if (!(result.getContents() == "pinkhouse")) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
                            alert.setIcon(R.drawable.unhappyicon);
                            alert.setMessage("Please enter valid code");
                            alert.show();
                        }
                    }  else if ((count > badgesList.size() +1) && (result.getContents() == "pinkhouse")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
                        builder.setTitle("Stamp Load");
                        builder.setIcon(R.drawable.unhappyicon);
                        builder.setMessage("You have earned all the stamps. \n Directly Join your Merchant shop to get your gift.");
                        builder.show();
                    }
                    else  {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
                        builder.setTitle("StampLoad");
                        builder.setIcon(R.drawable.unhappyicon);
                        builder.setMessage("You have earned all the stamps. \n Directly Join your Merchant shop to get your gift.");
                        builder.show();
                    }
                    // update shared preferences



                } catch (Exception e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(Pink_house_Card.this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private List<Badges> getAllItemList(int counter) {
        List<Badges> allItems = new ArrayList<Badges>();
        allItems.add(new Badges(R.drawable.stamp_empty));
        allItems.add(new Badges(R.drawable.stamp_empty1));
        allItems.add(new Badges(R.drawable.stamp_empty2));
        allItems.add(new Badges(R.drawable.stamp_empty3));
        allItems.add(new Badges(R.drawable.stamp_empty2));
        allItems.add(new Badges(R.drawable.stamp_empty3));
        allItems.add(new Badges(R.drawable.stamp_empty4));
        allItems.add(new Badges(R.drawable.stamp_empty5));

        for (int i = 0; i < counter; i++) {
            try{
                allItems.set(i, new Badges(R.drawable.earned_stamp));
            }catch (Exception e){

            }


        }

        return allItems;
    }

    private void updateGridView(List<Badges> listItems, int numberOfItemsToChange) {
        List<Badges> rowListItems = updateSpecificItem(listItems, numberOfItemsToChange);
        GridLayoutManager gridlayout = new GridLayoutManager(Pink_house_Card.this, 4);
        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view3);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gridlayout);
        CardAdapter rcAdapt = new CardAdapter(Pink_house_Card.this, rowListItems);
        rView.setAdapter(rcAdapt);
    }


    private List<Badges> updateSpecificItem(List<Badges> listItems, int numberOfItemsToChange) {

            for (int i = 0; i < numberOfItemsToChange; i++) {
                listItems.set(i, new Badges(R.drawable.earned_stamp));
        }
        return listItems;
    }



    private void getData() {
        String id = "1";


        String Url = Config.DATA_URL + id;
        StringRequest stringRequest = new StringRequest(Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showData(response);
                // redeemcard = JSONPARSER.parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Pink_house_Card.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }


    private void showData(String response){
        String name="";
        String enddate="";
        String startdate="";
        String Qr="";
        String code = "";
        String logocompany ="";
        String reward ="";
        try{
            JSONObject jsonObject = new JSONObject(response);
            //create an json array

            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject data = result.getJSONObject(0);
            name = (data.getString(Config.KEY_NAME));
            enddate=  data.getString(Config.KEY_ENDDATE);
            logocompany  =  data.getString(Config.KEY_LOGO);
            reward = data.getString(Config.KEY_REWARDS);
            code = data.getString(Config.KEY_CODE);


//Intent intent = new Intent(getApplicationContext(), stamp_card_pink_house.class);
            //   intent.putExtra("Key_code", code);
            //  startActivity(intent);
SharedPreferences.Editor editor = sharedPref.edit();

editor.putString("merchantName_key", name);
            editor.putString("endDate_key", enddate);
            editor.putString("reward_key", reward);
            editor.putString("photo_key", logocompany);
            editor.putString("code_Key", code);
            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Merchant_name.setText(name);
        Merchant_name.setAllCaps(true);
        End_Date.setText("Promotion Until: " + enddate);
        Reward_details.setText(reward);
        String imageUrl = "https://stampload1.000webhostapp.com/images/logo/" + logocompany;
        Picasso.with(this)
                .load(imageUrl).resize(80,80)
                //     .placeholder(android.R.drawable.star_on)
                //   .error(android.R.drawable.stat_notify_error)
                .into(photo);



    }

private void getCode(int i){

    String url = "https://stampload1.000webhostapp.com/API/getCodeNumber.php?code_id=" + i;

     StringRequest stringRequest1 = new StringRequest(url, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
        showCode(response);
             System.out.println("response");
         }
     }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
             Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
         }

     });
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest1);

}
    private void showCode(String response) {
       String Code="";
        try {
            JSONArray jsonArray = new JSONArray(response);

          for (int i = 0; i< jsonArray.length() ; i++){

              JSONObject jsondata = jsonArray.getJSONObject(i);

              Code = jsondata.getString("code");

              SharedPreferences.Editor editor = sharedPref.edit();
              editor.putString("key_code", Code);
              editor.commit();
          }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {
        //initiating the qr code scan

    }
}





