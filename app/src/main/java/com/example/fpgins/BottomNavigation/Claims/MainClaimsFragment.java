package com.example.fpgins.BottomNavigation.Claims;


import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fpgins.DataModel.MainClaimsData;
import com.example.fpgins.R;

import java.util.ArrayList;
import java.util.List;


public class MainClaimsFragment extends Fragment {


    private List<MainClaimsData> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MainClaimsAdapter mAdapter;


    public MainClaimsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_claims, container, false);

        recyclerView = root.findViewById(R.id.rvList);
        mAdapter = new MainClaimsAdapter(dataList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        inquireClaimsData();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void inquireClaimsData() {
        dataList.clear();

        MainClaimsData data = new MainClaimsData
                ("Motor", "Find out more",
                        ContextCompat.getDrawable(getContext(), R.drawable.pic_four));
        dataList.add(data);

        data = new MainClaimsData
                ("Travel", "Find out more" ,
                        ContextCompat.getDrawable(getContext(), R.drawable.pic_five));
        dataList.add(data);

        data = new MainClaimsData("Personal Accident", "Find out more" ,
                ContextCompat.getDrawable(getContext(), R.drawable.pic_six));
        dataList.add(data);

        data = new MainClaimsData("Home", "Find out more" ,
                ContextCompat.getDrawable(getContext(), R.drawable.pic_seven));
        dataList.add(data);

        data = new MainClaimsData("Corporate Products", "Find out more" ,
                ContextCompat.getDrawable(getContext(), R.drawable.pic_one));
        dataList.add(data);

        mAdapter.notifyDataSetChanged();
    }

}
