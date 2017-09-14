package com.example.drake.stamploadproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drake.stamploadproject.Class.Badges;
import com.example.drake.stamploadproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drake on 6/22/2017.
 */

public class CardAdapter extends Adapter<RecyclerViewHolders> {
    List<Badges> badgelist = new ArrayList<>();
    private Context mcontext;
    public CardAdapter (Context context, List<Badges> badgelist){
        super();
        this.badgelist = badgelist;
        this.mcontext = context;
    }
    @Override

    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stampelements, parent, false);
        RecyclerViewHolders rcv = new RecyclerViewHolders(view);


        return rcv;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
             holder.img.setImageResource(badgelist.get(position).getImage());
    }




    @Override
    public int getItemCount() {
        return badgelist.size();
    }

}
