package com.example.fpgins.BottomNavigation.Claims.TabLayout;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fpgins.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment {

    private LinearLayout mLinearLayout;


    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_status, container, false);


        return root;
    }

}
