package com.example.drake.stamploadproject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drake.stamploadproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StampRecordsFragment extends Fragment {


    public StampRecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stamp_record, container, false);
    }

}
