package com.example.drake.stamploadproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.drake.stamploadproject.Class.Config;
import com.example.drake.stamploadproject.Class.RedemptionCard;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class stamp_card_pink_house extends Activity implements View.OnClickListener {
    private static final int BARCODE_READER_REQUEST_CODE = 9001 ;
    private static final String LOG_TAG = "barcode";
    List<RedemptionCard> redeemcard;
    private TextView MerchantName;
    private TextView CardEndDate;
    private ImageView LogoCompany;
    private EditText codeNumber;
    private TextView Rewardcard;
    private Button QrScanner;
    private Button validate;
    private ImageView stamp1;
    private ImageView stamp2;
    private ImageView stamp3;
    private ImageView stamp4;
    private ImageView stamp5;
    private ImageView stamp6;
    private ImageView stamp7;
    private ImageView stamp8;
    private ImageView stamp9;
    private ImageView stamp10;
    private ImageView stamp11;
    private ImageView stamp12;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private Button cancel;

List<Object> stampImageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_stamp_card_pink_house);
        List<RedemptionCard> data1 = new ArrayList<>();

        MerchantName = (TextView)findViewById(R.id.company);
        CardEndDate = (TextView)findViewById(R.id.end_date);
        LogoCompany = (ImageView)findViewById(R.id.logoCompany);
        QrScanner = (Button)findViewById(R.id.scan_qr1);
        codeNumber = (EditText) findViewById(R.id.code_number1);
        Rewardcard = (TextView)findViewById(R.id.rewards);
        validate = (Button) findViewById(R.id.gain_stamp);
        cancel = (Button)findViewById(R.id.cancel_card);
        stamp1 = (ImageView) findViewById(R.id.stamp1);
        stamp2 = (ImageView) findViewById(R.id.stamp2);
        stamp3 = (ImageView) findViewById(R.id.stamp3);
        stamp4 = (ImageView) findViewById(R.id.stamp4);
        stamp5 = (ImageView) findViewById(R.id.stamp5);
        stamp6 = (ImageView) findViewById(R.id.stamp6);
        stamp7 = (ImageView) findViewById(R.id.stamp7);
        stamp8 = (ImageView) findViewById(R.id.stamp8);
        stamp9 = (ImageView) findViewById(R.id.stamp9);
        stamp10 = (ImageView) findViewById(R.id.stamp10);
        stamp11 = (ImageView) findViewById(R.id.stamp11);
        stamp12 = (ImageView) findViewById(R.id.stamp12);



        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               final String Entercode = codeNumber.getEditableText().toString().trim();
                Toast.makeText(getApplicationContext(),Entercode,Toast.LENGTH_SHORT).show();

                if (Entercode.equals("sweet")) {


                }
            }
        });


getData();

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
                Toast.makeText(stamp_card_pink_house.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
               startdate=   data.getString(Config.KEY_STARTDATE);
              code=  data.getString(Config.KEY_CODE);
               Qr= data.getString(Config.KEY_QR);
             logocompany  =  data.getString(Config.KEY_LOGO);
                reward = data.getString(Config.KEY_REWARDS);


//Intent intent = new Intent(getApplicationContext(), stamp_card_pink_house.class);
         //   intent.putExtra("Key_code", code);
          //  startActivity(intent);




    } catch (JSONException e) {
            e.printStackTrace();
        }

        MerchantName.setText(name);
        MerchantName.setAllCaps(true);
        CardEndDate.setText("Promotion Until: " + enddate);
        Rewardcard.setText(reward);
        String imageUrl = "https://stampload1.000webhostapp.com/images/logo/" + logocompany;
        Picasso.with(this)
              .load(imageUrl).resize(80,80)
           //     .placeholder(android.R.drawable.star_on)
             //   .error(android.R.drawable.stat_notify_error)
                .into(LogoCompany);



    }

    private void codeValidation(){




    }

    @Override
    public void onClick(View view) {

    }
    public void scanQR(){
        try{
            Intent intent = new Intent (ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException activityNoFound){
            showDialog(stamp_card_pink_house.this,"NO Scanner Found", "Download a scanner code activity?", "yes", "NO").show();
        }
    }
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo){
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try{
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {


                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }

        });
        return downloadDialog.show();
    }
    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = getIntent().getStringExtra("SCAN_RESULT");
                Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_LONG).show();
                if (contents.equals("pinkhouse")) {
                    stampImageList = new ArrayList();
                    stampImageList.add(stamp1);
                    stampImageList.add(stamp2);
                    stampImageList.add(stamp3);
                    stampImageList.add(stamp4);
                    stampImageList.add(stamp5);
                    stampImageList.add(stamp6);
                    stampImageList.add(stamp7);
                    stampImageList.add(stamp8);
                    stampImageList.add(stamp9);
                    stampImageList.add(stamp10);
                    stampImageList.add(stamp11);
                    stampImageList.add(stamp12);
                    for (int i = 0; i < stampImageList.size(); i++) {
                      Object obj =  stampImageList.get(i);

                        if (obj == stampImageList.get(4)) {
                           // stamp4.setBackgroundResource(R.drawable.earned_stamp);
                            Bitmap bImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.earned_stamp);
                           stamp4.setImageBitmap(bImage);
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setIcon(R.drawable.logo);
                            builder.setMessage("Stamp Earned");

                        } else if (obj == stampImageList.get(5)) {
                            stamp5.setBackgroundResource(R.drawable.earned_stamp);
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setIcon(R.drawable.logo);
                            builder.setMessage("Stamp Earned");
                        }


                    }
                    // String format = getIntent().getStringExtra("SCAN_RESULT_FORMAT");
                    //Toast.makeText(this, "Content:" + contents + "Format:" + format, Toast.LENGTH_LONG).show();
                }
            }
        }

    }
   /* @Override
    protected void  onActivityResult(int requestCode, int resultCode, Intent data) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                    alertDialog.setMessage(barcode.displayValue);
                    alertDialog.show();
                   // mResultTextView.setText(barcode.displayValue);
                }// else mResultTextView.setText(R.string.no_barcode_captured);
                alertDialog.setMessage(R.string.no_barcode_captured);
                alertDialog.show();
            } else Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
               CommonStatusCodes.getStatusCodeString(resultCode)));
        } else super.onActivityResult(requestCode, resultCode, data);
    }*/
    private void CodeValidation(){
        final String CodeValidation = codeNumber.getText().toString();

/*if (!CodeValidation ){
    stampImageList = new ArrayList<>();
    if(stampImageList.get(3) != null){
        stamp3.setImageResource(R.drawable.earned_stamp);
        Toast.makeText(getApplicationContext(),"Stamp earned", Toast.LENGTH_LONG).show();
    }*/



    }

}
