package com.example.fpgins.BottomNavigation.AgentDashboard.AgentFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fpgins.BottomNavigation.Claims.DraftsActivity;
import com.example.fpgins.BottomNavigation.Claims.SubmittedFormsActivity;
import com.example.fpgins.R;

public class ClaimsListFragment extends Fragment {

    private LinearLayout mFileAClaim, mInquireClaimStatus;

    public ClaimsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_claims_list, container, false);

        mFileAClaim = view.findViewById(R.id.linear_fileAClaim);
        mInquireClaimStatus = view.findViewById(R.id.linear_inquireClaimStatus);

        mFileAClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DraftsActivity.class);
                startActivity(intent);
            }
        });

        mInquireClaimStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubmittedFormsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}