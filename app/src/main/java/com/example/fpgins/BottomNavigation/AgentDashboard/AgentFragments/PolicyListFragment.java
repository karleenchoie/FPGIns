package com.example.fpgins.BottomNavigation.AgentDashboard.AgentFragments;


import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.CorporateActivity;
import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.HomeActivity;
import com.example.fpgins.BottomNavigation.AgentDashboard.MotorActivity;
import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.PersonalAccidentActivity;
import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.TravelActivity;
import com.example.fpgins.R;
import com.wang.avi.AVLoadingIndicatorView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PolicyListFragment extends Fragment {

    private LinearLayout mMotor, mPA, mTravel, mHome, mCorporate, mPaid, mUnpaid;

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
        mPaid = root.findViewById(R.id.linear_paid);
        mUnpaid = root.findViewById(R.id.linear_unpaid);
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

        mPaid.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(), "from internet", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), ViewActivity.class);
                intent.putExtra("ViewType", "internet");
                startActivity(intent);
                return false;
            }
        });

        mUnpaid.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(), "from assets", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), ViewActivity.class);
                intent.putExtra("ViewType", "assets");
                startActivity(intent);
                return false;
            }
        });

        return root;
    }

}
