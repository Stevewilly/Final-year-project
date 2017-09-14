package com.example.drake.stamploadproject.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.drake.stamploadproject.R;

/**
 * Created by drake on 6/22/2017.
 */
public class RecyclerViewHolders extends RecyclerView.ViewHolder {
   public EditText codenumber;
    public ImageView img;
    public RecyclerViewHolders(View itemView) {
        super(itemView);
      codenumber = (EditText)itemView.findViewById(R.id.code_number1);
       img = (ImageView)itemView.findViewById(R.id.stamp);
    }
}
