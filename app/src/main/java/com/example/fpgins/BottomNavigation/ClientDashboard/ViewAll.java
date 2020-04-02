package com.example.fpgins.BottomNavigation.ClientDashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.BottomNavigation.Claims.DraftsActivity;
import com.example.fpgins.DataModel.FirstSlideMenuData;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.R;
import com.example.fpgins.ui.NotificationMessage.NotifMessage;

import java.util.ArrayList;
import java.util.List;

public class ViewAll extends AppCompatActivity {

    private List<FirstSlideMenuData> mData = new ArrayList<>();
    private GridView mGridView;
    private ViewAllAdapter mAdapter;
    private ImageView mBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_dashboard);

        mGridView = findViewById(R.id.grid);
        mAdapter = new ViewAllAdapter(mData, getApplicationContext());

        mGridView.setAdapter(mAdapter);
        mBackButton = findViewById(R.id.img_backbutton);

        getFirstMenuData();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirstSlideMenuData data = mData.get(position);

                Intent intent = new Intent(ViewAll.this, NotifMessage.class);
                startActivity(intent);
            }
        });

    }

    private void getFirstMenuData(){
        UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(ViewAll.this));
        FirstSlideMenuData firstSlideMenuData1 = new FirstSlideMenuData(userData.getPhoto(),"First Image", "First Description");

        mData.add(firstSlideMenuData1);

        FirstSlideMenuData firstSlideMenuData2 = new FirstSlideMenuData(userData.getPhoto(),"Second Image", "Second Description");

        mData.add(firstSlideMenuData2);

        FirstSlideMenuData firstSlideMenuData3 = new FirstSlideMenuData(userData.getPhoto(),"Third Image", "Third Description");

        mData.add(firstSlideMenuData3);

        mAdapter.notifyDataSetChanged();
    }
}
