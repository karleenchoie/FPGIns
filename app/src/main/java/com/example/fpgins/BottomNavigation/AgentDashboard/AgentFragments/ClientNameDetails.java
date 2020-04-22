package com.example.fpgins.BottomNavigation.AgentDashboard.AgentFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.fpgins.DataModel.ClientNameData;
import com.example.fpgins.R;

import java.util.ArrayList;

public class ClientNameDetails extends AppCompatActivity {

    private ImageView mBackButton;
    private RecyclerView mRecyclerView;
    private EditText mPrimaryEmail, mFirstName, mLastName, mBirthday, mContact1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_name_details);

        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mPrimaryEmail = findViewById(R.id.txt_clientEmail);
        mFirstName = findViewById(R.id.txt_clientFirstName);
        mLastName = findViewById(R.id.txt_clientLastName);
        mBirthday = findViewById(R.id.txt_clientBday);
        mContact1 = findViewById(R.id.txt_clientMobile);
    }
}
