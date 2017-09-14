package com.example.drake.stamploadproject.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drake.stamploadproject.R;

/**
 * Created by drake on 4/15/2017.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView CompanyName;
    public TextView CompanyAddress;
    public ImageView CompanyProfilePhoto;
    public ImageView likeImage;
    public ImageView shareImage;
    public Button RedeemBtn;



    public RecyclerViewHolder(final View itemView) {
        super(itemView);
        CompanyAddress =(TextView)itemView.findViewById(R.id.location_company);
        CompanyName = (TextView)itemView.findViewById(R.id.name_company);
        CompanyProfilePhoto = (ImageView)itemView.findViewById(R.id.image_company);
        likeImage = (ImageView)itemView.findViewById(R.id.like);
        shareImage = (ImageView)itemView.findViewById(R.id.share);
        RedeemBtn = (Button)itemView.findViewById(R.id.btn_redeem1);
        final RecyclerView recyclerView = (RecyclerView)itemView.findViewById(R.id.recycler_view1);
/*likeImage.setTag(R.drawable.ic_like);
        likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = (int)likeImage.getTag();
                if ( v == R.drawable.ic_like){
                    likeImage.setImageResource(R.drawable.ic_liked);
                    likeImage.setTag(R.drawable.ic_liked);

                    Toast.makeText(view.getContext(),CompanyName.getText() + "  liked", Toast.LENGTH_SHORT).show();
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
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

                int position = getLayoutPosition();
                if (position == 0) {
                  //  final Dialog Dialog = new Dialog(view.getContext());
stamp_card_pink_house card = new stamp_card_pink_house();



                    Intent redeemIntent = new Intent(view.getContext(), stamp_card_pink_house.class);
                    view.getContext().startActivity(redeemIntent);
                }
            }
        });*/
    }

    @Override
    public void onClick(View view) {

    }


}
