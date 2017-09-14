package com.example.drake.stamploadproject.Fragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drake.stamploadproject.Adapter.CompanyCardRecycleViewAdapterClass;
import com.example.drake.stamploadproject.R;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class stamp_and_rewardsFragment extends Fragment {
    public static final String DATA_URL = "https://stampload1.000webhostapp.com/test.php";
    LinearLayoutManager linearLayoutManager;
    String JSON_ID = "user_id";
    String JSON_NAME = "name";
    String JSON_Logo = "logo1";
    String JSON_location = "location";
    String finalJSON, parseJSON;
    ProgressDialog progressDialog;
    RecyclerView recView1;
    List<companyCardDetailsClass> data;
    CompanyCardRecycleViewAdapterClass rcAdapter;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    TextView textPHP;
    public stamp_and_rewardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stamp_and_rewards, container, false);

     /*recView1 = (RecyclerView)view.findViewById(R.id.recycler_view2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
       recView1.setLayoutManager(linearLayoutManager);
       CompanyCardRecycleViewAdapterClass rcAdapter = new CompanyCardRecycleViewAdapterClass(getActivity(), data);
    recView1.setAdapter(rcAdapter);*/
        new AsyncFetch().execute();

//        load_data_from_server();
        return view;



    }


    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
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
                url = new URL("https://stampload1.000webhostapp.com/API/getCardData.php");
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
                if (response_code == HttpURLConnection.HTTP_OK) {

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
                } else{
                        return ("Unsuccessful");
                    }
                }catch(IOException e1){
                    e1.printStackTrace();
                    return e1.toString();
                }finally{
                    conn.disconnect();

                }

            }


            @Override
            protected void onPostExecute (String result){
                //this method will be running on UI thread
                pdLoading.dismiss();

                List<companyCardDetailsClass> data = new ArrayList<>();
                pdLoading.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    // Extract data from PHP file and store into arrayList as class
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);

                        companyCardDetailsClass data_s = new companyCardDetailsClass();
                        data_s.companyname = json_data.getString("companyname");
                        data_s.location = json_data.getString("location");
                        data_s.ImageCpny = json_data.getString("logo");

                        data.add(data_s);
                    }
                    recView1 = (RecyclerView) getView().findViewById(R.id.recycler_view2);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recView1.setLayoutManager(linearLayoutManager);

                    CompanyCardRecycleViewAdapterClass rcAdapter = new CompanyCardRecycleViewAdapterClass(getActivity(), data);
                    recView1.setAdapter(rcAdapter);
                    // Setup and Handover data to recyclerview

                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }
    }





