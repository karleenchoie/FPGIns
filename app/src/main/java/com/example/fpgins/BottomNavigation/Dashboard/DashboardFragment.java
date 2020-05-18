package com.example.fpgins.BottomNavigation.Dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.fpgins.R;

public class DashboardFragment extends Fragment {

    private ImageView mNewsletters;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mNewsletters = root.findViewById(R.id.img_news);

        return root;
    }
}