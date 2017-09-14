package com.example.drake.stamploadproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.drake.stamploadproject.Activity.Pink_house_Card;
import com.example.drake.stamploadproject.Class.companyCardDetailsClass;
import com.example.drake.stamploadproject.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

/**
 * Created by drake on 4/15/2017.
 */

public class CompanyCardRecycleViewAdapterClass extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   List<companyCardDetailsClass> data;
    String app_server_url = "https://stampload1.000webhostapp.com/API/fcm_insert.php";
    private Context context;
    companyCardDetailsClass current;
    int currentPos = 0;
private LayoutInflater inflater;
    public CompanyCardRecycleViewAdapterClass(Context context, List<companyCardDetailsClass> data ) {
        super();
        this.context = context;

        inflater = LayoutInflater.from(context);
        this.data = data;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_card_list, parent, false);
        MyHolder myholder = new MyHolder(view);


        return myholder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        companyCardDetailsClass getdata = data.get(position);
        MyHolder myholder = (MyHolder) holder;
        myholder.CompanyName.setText("Merchant Name: " + getdata.companyname);
        myholder.CompanyLocation.setText(" Promotion will be held in: " + getdata.location);
      //  myholder.CompanyProfilePhoto.setImageResource(getdata.ImageCpny);
        Glide.with(context).load("https://stampload1.000webhostapp.com/images/logo/" + getdata.ImageCpny)
              .into(myholder.CompanyProfilePhoto);
//
    }

   // @Override
   // public void onBindViewHolder(ViewHolder holder, int position) {

//
  //  }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView CompanyName;
        public TextView CompanyLocation;
        public ImageView CompanyProfilePhoto;
        public ImageView likeImage;
        public ImageView shareImage;
        public Button RedeemBtn;
        public MyHolder(View view) {
            super(view);
            CompanyLocation =(TextView)itemView.findViewById(R.id.location_company);
            CompanyName = (TextView)itemView.findViewById(R.id.name_company);
            CompanyProfilePhoto = (ImageView)itemView.findViewById(R.id.image_company);
            likeImage = (ImageView)itemView.findViewById(R.id.like);
            shareImage = (ImageView)itemView.findViewById(R.id.share);
            RedeemBtn = (Button)itemView.findViewById(R.id.btn_redeem1);
           // final RecyclerView recyclerView = (RecyclerView)itemView.findViewById(R.id.recycler_view2);

likeImage.setTag(R.drawable.ic_like);
        likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = (int)likeImage.getTag();
                if ( v == R.drawable.ic_like){
                    likeImage.setImageResource(R.drawable.ic_liked);
                    likeImage.setTag(R.drawable.ic_liked);

                    Toast.makeText(view.getContext(),CompanyName.getText() + "  liked", Toast.LENGTH_SHORT).show();
                   // LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                 //   linearLayoutManager.scrollToPosition();
                }
                else {

                    likeImage.setImageResource(R.drawable.ic_like);
                    likeImage.setTag(R.drawable.ic_like);
                    Toast.makeText(view.getContext(),CompanyName.getText() +  " unlike", Toast.LENGTH_SHORT).show();
                }
            }
        });
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Join me on Stamp Load to Download from play Store and Apple Store");
                shareIntent.setType("text/plain");
              view.getContext().startActivity(Intent.createChooser(shareIntent, "Share Using"));

            }
        });
        RedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().subscribeToTopic("test");
                FirebaseInstanceId.getInstance().getToken();
                /*final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setIcon(R.drawable.logo);
                alert.setMessage("You may start receiving notifications from this Merchant. Click Cancel to return");
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }

                });
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                    }
                });
                alert.show();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(String.valueOf((R.string.FCM_PREF)), Context.MODE_PRIVATE);
                final String token = sharedPreferences.getString(String.valueOf(R.string.FCM_TOKEN), "" );
                StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("fcm_token", token);
                        return params;
                    }
                };*/
                //MySingleton.getmInstance(getApplicationContext()).addToRequestqueue(stringRequest);
                int position = getLayoutPosition();
                if (position == 0) {
                    //  final Dialog Dialog = new Dialog(view.getContext());
                    Pink_house_Card card = new Pink_house_Card();



                    Intent redeemIntent = new Intent(view.getContext(), Pink_house_Card.class);
                    view.getContext().startActivity(redeemIntent);
                }

            }
        });

        }
    }
}
