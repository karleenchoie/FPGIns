package com.example.fpgins.BottomNavigation.AgentDashboard.AgentFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.CorporateActivity;
import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.HomeActivity;
import com.example.fpgins.BottomNavigation.AgentDashboard.MotorActivity;
import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.PersonalAccidentActivity;
import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.TravelActivity;
import com.example.fpgins.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PolicyListFragment extends Fragment {

    private LinearLayout mMotor, mPA, mTravel, mHome, mCorporate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_policy_list, container, false);

        mMotor = root.findViewById(R.id.linear_policyMotor);
        mPA = root.findViewById(R.id.linear_policyPa);
        mTravel = root.findViewById(R.id.linear_policyTravel);
        mHome = root.findViewById(R.id.linear_policyHome);
        mCorporate = root.findViewById(R.id.linear_policyCorporate);
        mMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MotorActivity.class);
                startActivity(intent);
            }
        });

        mPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PersonalAccidentActivity.class);
                startActivity(intent);
            }
        });

        mTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TravelActivity.class);
                startActivity(intent);
            }
        });

        mCorporate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CorporateActivity.class);
                startActivity(intent);
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

}
