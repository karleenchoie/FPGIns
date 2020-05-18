package com.example.fpgins.BottomNavigation.AgentDashboard.AgentFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fpgins.BottomNavigation.AgentDashboard.ClaimsTabActivities.CorporateClaimsActivity;
import com.example.fpgins.BottomNavigation.AgentDashboard.ClaimsTabActivities.HomeClaimsActivity;
import com.example.fpgins.BottomNavigation.AgentDashboard.ClaimsTabActivities.MotorClaimsActivity;
import com.example.fpgins.BottomNavigation.AgentDashboard.ClaimsTabActivities.PersonalAccidentClaimsActivity;
import com.example.fpgins.BottomNavigation.AgentDashboard.ClaimsTabActivities.TravelClaimsActivity;
import com.example.fpgins.BottomNavigation.Claims.DraftsActivity;
import com.example.fpgins.BottomNavigation.Claims.SubmittedFormsActivity;
import com.example.fpgins.R;

public class ClaimsListFragment extends Fragment {

    private LinearLayout mMotor, mPA, mTravel, mCorporate, mHome;
    private LinearLayout mFileAClaim, mInquireClaimStatus;

    public ClaimsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_claims_list, container, false);

        mMotor = view.findViewById(R.id.linear_claimMotor);
        mPA = view.findViewById(R.id.linear_claimPa);
        mTravel = view.findViewById(R.id.linear_claimTravel);
        mCorporate = view.findViewById(R.id.linear_claimCorporate);
        mHome = view.findViewById(R.id.linear_claimHome);
        mFileAClaim = view.findViewById(R.id.linear_fileAClaim);
        mInquireClaimStatus = view.findViewById(R.id.linear_inquireClaimStatus);

        mMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MotorClaimsActivity.class);
                startActivity(intent);
            }
        });

        mPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalAccidentClaimsActivity.class);
                startActivity(intent);
            }
        });

        mTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TravelClaimsActivity.class);
                startActivity(intent);
            }
        });

        mCorporate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CorporateClaimsActivity.class);
                startActivity(intent);
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeClaimsActivity.class);
                startActivity(intent);
            }
        });

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