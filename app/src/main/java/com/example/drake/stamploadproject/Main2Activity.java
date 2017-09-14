package com.example.drake.stamploadproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.drake.stamploadproject.Adapter.CompanyCardRecycleViewAdapterClass;
import com.example.drake.stamploadproject.Class.companyCardDetailsClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    ProgressDialog progressDialog;
    RecyclerView recView2;
    List<companyCardDetailsClass> data;
    CompanyCardRecycleViewAdapterClass rcAdapter;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
   /* ProgressDialog progressDialog;
    ArrayList<companyCardDetailsClass> rowListItem;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CompanyCardRecycleViewAdapterClass adapter;
    List<String> getItems;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView  recView2 = (RecyclerView)findViewById(R.id.recycler_view1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Main2Activity.this);
        recView2.setLayoutManager(linearLayoutManager);

        CompanyCardRecycleViewAdapterClass rcAdapter = new CompanyCardRecycleViewAdapterClass(Main2Activity.this, data);
        recView2.setAdapter(rcAdapter);
        new AsyncFetch().execute();



    }
    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(Main2Activity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                //Enter URL address where your json file resides
                url = new URL("http://192.168.185.1/stamploaddatabase/getMerchantDetails.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we receive data from json file.
                conn.setDoOutput(true);
            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }
            try {
                int response_code = conn.getResponseCode();
                //check if there is successfull connecction made
                if (response_code == HttpURLConnection.HTTP_OK) ;

                // read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Pass data to OnPostExecute method
                return (result.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();

            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result){
            //this method will be running on UI thread
            pdLoading.dismiss();

            List<companyCardDetailsClass> data = new ArrayList<>();
            pdLoading.dismiss();
            try{
                JSONArray jsonArray = new JSONArray(result);

                // Extract data from PHP file and store into arrayList as class
                for(int i = 0; i<jsonArray.length(); i++){
                    JSONObject json_data = jsonArray.getJSONObject(i);

                    companyCardDetailsClass data_s = new companyCardDetailsClass();
                    data_s.companyname = json_data.getString("companyname");
                    data_s.location= json_data.getString("location");
                    data_s.ImageCpny= json_data.getString("logo") ;

                    data.add(data_s);
                }
              RecyclerView  recView2 = (RecyclerView)findViewById(R.id.recycler_view1);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Main2Activity.this);
                recView2.setLayoutManager(linearLayoutManager);

                CompanyCardRecycleViewAdapterClass rcAdapter = new CompanyCardRecycleViewAdapterClass(Main2Activity.this, data);
                recView2.setAdapter(rcAdapter);
                // Setup and Handover data to recyclerview

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

  /*  private void load_data_from_server() {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                //send request to server for data
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://192.168.161.2/stamploaddatabase/getMerchantDetails.php?id=").build();
                try {
                    //get response from server in form of JSon array
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        companyCardDetailsClass data = new companyCardDetailsClass(object.getInt("user_id"), object.getString("companyname"), object.getString("location"),
                                object.getInt("logo"));
                        rowListItem.add(data);

                    }





                }

                catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("End of Content");
                }
return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (adapter != null){
                    adapter.notifyDataSetChanged();
                }
                adapter = new CompanyCardRecycleViewAdapterClass(this,rowListItem);
                recyclerView.setAdapter(adapter);

            }
        };
        task.execute();
    }*/
   /*
 public void getCompanyData() {
        String url = Config2.DATA_URL;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                showCompanyData(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       //Toast.makeText(getActivity(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }
   // public List<companyCardDetailsClass> getGetItems(){}
    public void showCompanyData(JSONArray array){
String SERVER_DATA = "merchantsdetails";
        for(int i = 0; i<array.length(); i++){

           companyCardDetailsClass GetDataAdapter2 = new companyCardDetailsClass();
            JSONObject json = null;

            try{
                json = array.getJSONObject(i);
                GetDataAdapter2.setCompanyname(json.getString(Config2.KEY_NAME));
                GetDataAdapter2.setLocation(json.getString(Config2.KEY_LOCATION));
                GetDataAdapter2.setId_company(json.getInt(Config2.KEY_USER_ID));

                getItems.add(json.getString(Config2.KEY_USER_ID));
                getItems.add(json.getString(Config2.KEY_NAME));
               getItems.add(json.getString(Config2.KEY_LOCATION));

              // getItems.add(json.getString(Config2.KEY_LOGO));

            } catch (JSONException e) {

            e.printStackTrace();
        }
        rowListItem.add(GetDataAdapter2);
    }
        adapter.notifyDataSetChanged();
       *//* RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view1);
    adapter = new CompanyCardRecycleViewAdapterClass(this, rowListItem);
    recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);*//*

}*/
}
