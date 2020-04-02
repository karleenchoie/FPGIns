package com.example.fpgins.BottomNavigation.Claims.TabLayout;

import android.os.Bundle;

import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.R;
import com.google.android.material.tabs.TabLayout;

import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import com.example.fpgins.BottomNavigation.Claims.TabLayout.ui.main.SectionsPagerAdapter;

public class TabularActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ImageView mBackButton;
    String claimNo;
    UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabular);

        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mUserData = new UserData(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        mUserData.isActivityRunning(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUserData = new UserData(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        mUserData.isActivityRunning(false);
    }
}