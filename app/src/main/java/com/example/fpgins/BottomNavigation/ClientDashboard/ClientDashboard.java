package com.example.fpgins.BottomNavigation.ClientDashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fpgins.BottomNavigation.Claims.DraftsActivity;
import com.example.fpgins.BottomNavigation.Claims.SubmittedFormsActivity;
import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.ClientMotorActivity;
import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.CorporateActivity;
import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.HomeActivity;
import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.PersonalAccidentActivity;
import com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus.TravelActivity;
import com.example.fpgins.BottomNavigation.AgentDashboard.main.SectionsPagerAdapter;
import com.example.fpgins.DataModel.FirstSlideMenuData;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Network.ImageUploaderUtility.DownloadImageTask;
import com.example.fpgins.R;
import com.google.android.material.tabs.TabLayout;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.EdgeDetail;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.ArrayList;
import java.util.List;

public class ClientDashboard extends Fragment {

    private List<FirstSlideMenuData> mData = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private FirstSlideMenuAdapter mAdapter;
    private LinearLayout mFileClaim, mMotor, mPersonalAccident, mTravel, mCorporate, mHome;
    private LinearLayout mFileExisting, mTransactionHistory, mShowMoreMenu, mMoreMenus, mShowLessMenu, mChangingMenu;
    private TextView mViewAll, mShowmore;
    private ImageView mSingleImage;
    private boolean isShowMenus = false;
    private UserData mUserData;
    private LinearLayout mClientPage, mAgentPage;
    private RelativeLayout mFile, mInquire;

    //for Agent
    private int backIndex, series1Index;
    private TextView mPercentage;
    private DecoView decoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_dashboard, container, false);

        mClientPage = view.findViewById(R.id.view_Client);
        mAgentPage = view.findViewById(R.id.view_Agent);

        mUserData = new UserData(PreferenceManager.getDefaultSharedPreferences(getContext()));
        String accountCode = mUserData.getAccountCode();
        if (accountCode.equals("AGT")){
            mAgentPage.setVisibility(View.VISIBLE);
            mClientPage.setVisibility(View.GONE);
            initializeAgent(view);
        }
        if (accountCode.equals("CLT")){
            mAgentPage.setVisibility(View.GONE);
            mClientPage.setVisibility(View.VISIBLE);
            initialize(view);
        }
        else {
            //do nothing
        }

        return view;
    }

    //try commit

    private void initialize(View view){
        mRecyclerView = view.findViewById(R.id.firstMenuSlideRView);
        mFileClaim = view.findViewById(R.id.iconFileClaim);
        mFileExisting = view.findViewById(R.id.iconFileExist);
        mFile = view.findViewById(R.id.relative_file);
        mInquire = view.findViewById(R.id.relative_Inquire);
//        mMotor = view.findViewById(R.id.linear_motor);
//        mPersonalAccident = view.findViewById(R.id.linear_pa);
//        mTravel = view.findViewById(R.id.linear_travel);
//        mCorporate = view.findViewById(R.id.linear_corporate);
//        mHome = view.findViewById(R.id.linear_home);
        mViewAll = view.findViewById(R.id.viewAll);
//        mChangingMenu = view.findViewById(R.id.linear_changingMenu);
        mSingleImage = view.findViewById(R.id.imageUploaded);
        mTransactionHistory = view.findViewById(R.id.topLinearLayout);
        mShowMoreMenu = view.findViewById(R.id.linear_showMore);
        mShowLessMenu = view.findViewById(R.id.linear_showLess);
        mShowmore = view.findViewById(R.id.txt_showMore);
        mMoreMenus = view.findViewById(R.id.linear_moreMenus);

        new DownloadImageTask
                ("https://cdn.dribbble.com/users/2005930/screenshots/5487747/dribbble-fpg-insurance-stbd_4x.png",
                        mSingleImage);

        mAdapter = new FirstSlideMenuAdapter(mData, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DraftsActivity.class);
                startActivity(intent);
            }
        });

        mInquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubmittedFormsActivity.class);
                startActivity(intent);
            }
        });


//        mFileClaim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showClaimsOption();
//            }
//        });

//        mPersonalAccident.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), PersonalAccidentActivity.class);
//                startActivity(intent);
//            }
//        });

//        mTravel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), TravelActivity.class);
//                startActivity(intent);
//            }
//        });

//        mCorporate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), CorporateActivity.class);
//                startActivity(intent);
//            }
//        });

        mTransactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TransactionHistory.class);
                startActivity(intent);
            }
        });

//        mHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), HomeActivity.class);
//                startActivity(intent);
//            }
//        });

//        mMotor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ClientMotorActivity.class);
//                startActivity(intent);
//            }
//        });

//        mShowMoreMenu.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                isShowMenus = false;
//                TypedValue typedValue = new TypedValue();
//                getActivity().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
//                if (!isShowMenus){
//                    mMoreMenus.setVisibility(View.VISIBLE);
//                    mChangingMenu.setVisibility(View.VISIBLE);
//                    mShowMoreMenu.setVisibility(View.GONE);
//                }else {
//                    //do nothing
//                }
//            }
//        });

//        mShowLessMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isShowMenus = true;
//                mMoreMenus.setVisibility(View.GONE);
//                mChangingMenu.setVisibility(View.GONE);
//                mShowMoreMenu.setVisibility(View.VISIBLE);
//            }
//        });

//        mFileExisting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        mViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAll.class);
                startActivity(intent);
            }
        });

        getFirstMenuData();
    }

    private void initializeAgent(View view){

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        decoView = view.findViewById(R.id.dynamicArcView);
        mPercentage = view.findViewById(R.id.txt_percentage);

        final SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setLineWidth(29f)
                .setRange(0, 50, 0)
                .build();

        final SeriesItem seriesItem2 = new SeriesItem.Builder(getResources().getColor(R.color.fpg_orange))
                .setRange(0, 50, 0)
                .setLineWidth(25f)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT)
//                .setSeriesLabel(new SeriesLabel.Builder("%.0f%% Clients").build())
//                .setSeriesLabel(new SeriesLabel.Builder("LABEL HERE").build())
                .addEdgeDetail(new EdgeDetail(EdgeDetail.EdgeType.EDGE_OUTER, getResources().getColor(R.color.fpg_light_orange), 0.4f))
                .build();

        backIndex = decoView.addSeries(seriesItem);
        series1Index = decoView.addSeries(seriesItem2);

        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {

            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        seriesItem2.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                float percentFilled = ((currentPosition - seriesItem2.getMinValue()) / (seriesItem2.getMaxValue() - seriesItem2.getMinValue()));
                mPercentage.setText(String.format("%.0f%%", percentFilled * 100f));
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });

        decoView.addEvent(new DecoEvent.Builder(50)
                .setIndex(backIndex)
                .build());

        decoView.addEvent(new DecoEvent.Builder(35.5f)
                .setIndex(series1Index)
                .setDelay(2000)
                .build());
    }

    private void getFirstMenuData(){
        FirstSlideMenuData firstSlideMenuData1 = new FirstSlideMenuData("https://i0.wp.com/www.adobomagazine.com/wp-content/uploads/2020/01/fpg-hero.jpg?fit=1440%2C757&ssl=1","New CEO", "FPG has new CEO");

        mData.add(firstSlideMenuData1);

        FirstSlideMenuData firstSlideMenuData2 = new FirstSlideMenuData("https://3.bp.blogspot.com/-jpRH0GXm9U0/XBNMhvu0rAI/AAAAAAAAAcc/UYsnWY1x2TomYc-eBlfktgtWeXWOxOdUACLcBGAs/s1600/FPG%2Bx%2BMYEG.jpg","I-Pay Partnership", "FPG Insurance inks partnership");

        mData.add(firstSlideMenuData2);

        FirstSlideMenuData firstSlideMenuData3 = new FirstSlideMenuData("https://3.bp.blogspot.com/-t7LaWaX7GUE/XH6he44xpmI/AAAAAAAChh0/JEZy0eyv074qm30YYoRVoFWNyMuFAClngCLcBGAs/s1600/FPG-Iloilo%2B%25283%2529.jpg","E-life", "FPG Insurance and Cebuana Lhuillier foundation");

        mData.add(firstSlideMenuData3);

        mAdapter.notifyDataSetChanged();
    }

    public void showClaimsOption(){
        Bundle args = new Bundle();
        ClaimsOptionFragment coverBannerDialogFragment = new ClaimsOptionFragment();
        coverBannerDialogFragment.setArguments(args);
        coverBannerDialogFragment.show(getFragmentManager(), null);
    }
}
