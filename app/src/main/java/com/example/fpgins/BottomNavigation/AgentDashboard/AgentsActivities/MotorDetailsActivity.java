package com.example.fpgins.BottomNavigation.AgentDashboard.AgentsActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fpgins.R;

public class MotorDetailsActivity extends AppCompatActivity {

    private ImageView mBackButton;
    private TextView mPlate, mChassis, mMake, mValue, mYear;
    private String plateNum, chassisNum, make, value, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_details);

        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mPlate = findViewById(R.id.txt_platenum);
        mChassis = findViewById(R.id.txt_chassisnum);
        mMake= findViewById(R.id.txt_carmake);
        mValue = findViewById(R.id.txt_carValue);
        mYear = findViewById(R.id.txt_carYear);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            plateNum = bundle.getString("platenum");
            chassisNum = bundle.getString("chassisnum");
            make = bundle.getString("carmake");
            value = bundle.getString("carValue");
            year = bundle.getString("carYear");

            mPlate.setText(plateNum);
            mChassis.setText(chassisNum);
            mMake.setText(make);
            mValue.setText(value);
            mYear.setText(year);

        }
    }
}
