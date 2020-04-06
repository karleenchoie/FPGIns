package com.example.fpgins.ui.BranchLocator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fpgins.R;

public class BranchLocator extends AppCompatActivity {

    private ImageView mBackButton;
    private TextView mPhilippines;
    private LinearLayout mPhBranch;
    private boolean isDisplayPh = false;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_locator);

        mPhilippines = findViewById(R.id.txt_Ph);
        mPhBranch = findViewById(R.id.linear_phBranch);
        mScrollView = findViewById(R.id.sv_Branch);

        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mPhilippines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDisplayPh = !isDisplayPh;
                if (isDisplayPh == true){
                    mPhBranch.setVisibility(View.VISIBLE);
                    mPhilippines.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                }else {
                    mPhBranch.setVisibility(View.GONE);
                    mPhilippines.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
