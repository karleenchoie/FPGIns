package com.example.fpgins.BottomNavigation.AgentDashboard.AgentFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fpgins.DataModel.ClientNameData;
import com.example.fpgins.R;

import java.util.ArrayList;

public class ClientNameDetails extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageView mBackButton;


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
    }
}
