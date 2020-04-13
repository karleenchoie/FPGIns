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

                Intent intent = new Intent(ViewAll.this, NewsandEvents.class);
                startActivity(intent);
            }
        });

    }

    private void getFirstMenuData(){
//        FirstSlideMenuData firstSlideMenuData1 = new FirstSlideMenuData("https://i0.wp.com/www.adobomagazine.com/wp-content/uploads/2020/01/fpg-hero.jpg?fit=1440%2C757&ssl=1","New CEO", "FPG has new CEO");
//
//        mData.add(firstSlideMenuData1);
//
//        FirstSlideMenuData firstSlideMenuData2 = new FirstSlideMenuData("https://3.bp.blogspot.com/-jpRH0GXm9U0/XBNMhvu0rAI/AAAAAAAAAcc/UYsnWY1x2TomYc-eBlfktgtWeXWOxOdUACLcBGAs/s1600/FPG%2Bx%2BMYEG.jpg","I-Pay Partnership", "FPG Insurance inks partnership");
//
//        mData.add(firstSlideMenuData2);
//
//        FirstSlideMenuData firstSlideMenuData3 = new FirstSlideMenuData("https://3.bp.blogspot.com/-t7LaWaX7GUE/XH6he44xpmI/AAAAAAAChh0/JEZy0eyv074qm30YYoRVoFWNyMuFAClngCLcBGAs/s1600/FPG-Iloilo%2B%25283%2529.jpg","E-life", "FPG Insurance and Cebuana Lhuillier foundation");
//
//        mData.add(firstSlideMenuData3);

        mAdapter.notifyDataSetChanged();
    }
}
