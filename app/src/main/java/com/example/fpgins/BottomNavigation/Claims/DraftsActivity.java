package com.example.fpgins.BottomNavigation.Claims;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fpgins.DataModel.MotorsDraft;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Login.Session.UserSessionManager;
import com.example.fpgins.R;
import com.example.fpgins.SQLiteDB.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DraftsActivity extends AppCompatActivity {

    private FloatingActionButton mAddDrafts;
    private ArrayList<MotorsDraft> mData = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private DraftsAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefresh;
    private DBHelper mDBHelper;
    private ImageView mBack;

    private String mUserEmail;
//    private SwipeController mSwipeController;

    //No internet connection
//    private LinearLayout notification;
//    private Button noDataRetryButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_draft_list);

        UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(DraftsActivity.this));

        mUserEmail = userData.getEmail();

        mAddDrafts = (FloatingActionButton) findViewById(R.id.addDrafts);
        mBack = findViewById(R.id.img_backbutton);

        mRecyclerView = (RecyclerView) findViewById(R.id.draftList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(DraftsActivity.this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        notification = (LinearLayout) findViewById(R.id.relativeNoConnection);
//        noDataRetryButton = (Button) findViewById(R.id.buttonRetry);

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.fpg_orange));
        mSwipeRefresh.setDistanceToTriggerSync(450);// in dips
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(true);
                //preparing data all again when ever pull to refresh is active
                getData(mUserEmail);
            }

        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAdapter = new DraftsAdapter(mData, DraftsActivity.this);


        mAddDrafts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(DraftsActivity.this));
//                userData.setDrafts(true);

                DBHelper dbHelper = new DBHelper(DraftsActivity.this);
                dbHelper.open();
                int counter = dbHelper.retrieveLastCount();
                dbHelper.close();

//                if (userData.isDrafts()) {
                    //To increment the trigger button is save as draft in motor file
                    //when the value is TRUE it will automatically increment
//                    char lastChar = draft.charAt(draft.length() - 1);
//                    int count = Integer.parseInt(String.valueOf(lastChar));
                    userData.setDraftsCount(userData.getEmail() + (counter + 1));
//                }

//                userData.setDrafts(false);

                Intent intent = new Intent(DraftsActivity.this, ClaimsActivity.class);
                startActivity(intent);
                finish();

//                ClaimsActivity claimsFragment = new ClaimsActivity();
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.nav_fragment, claimsFragment, "")
//                        .addToBackStack(null)
//                        .commit();
            }
        });

        checkComponents();

//        noDataRetryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkComponents();
//            }
//        });

    }

    public void checkComponents() {
        getData(mUserEmail);
        if (mData.size() == 0) {
            UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(DraftsActivity.this));
//            String draft = userData.getDraftsCount();
//            if (draft == null || draft == "") {
            userData.setDraftsCount(mUserEmail + "1");
//            }
            Intent intent = new Intent(DraftsActivity.this, ClaimsActivity.class);
            startActivity(intent);
            finish();
//            notification.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void getData(String email){
        mData.clear();

        mDBHelper = new DBHelper(DraftsActivity.this);

        mDBHelper.open();

        mData.addAll(mDBHelper.retrieveAllMotorDraftsDetails(email));

        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefresh.setRefreshing(false);

        mDBHelper.close();
    }
}
