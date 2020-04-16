package com.example.fpgins.BottomNavigation.ClientDashboard;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.fpgins.DataModel.BannerData;
import com.example.fpgins.DataModel.FirstSlideMenuData;
import com.example.fpgins.DataModel.SubmittedFormsData;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.Network.ImageUploaderUtility.DownloadImageTask;
import com.example.fpgins.R;
import com.example.fpgins.ui.NotificationMessage.NotifImageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.EdgeDetail;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ClientDashboard extends Fragment {

    private List<FirstSlideMenuData> mData = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private FirstSlideMenuAdapter mAdapter;
    private LinearLayout mFileClaim, mMotor, mPersonalAccident, mTravel, mCorporate, mHome;
    private LinearLayout mFileExisting, mTransactionHistory, mShowMoreMenu, mMoreMenus, mShowLessMenu, mChangingMenu;
    private TextView mViewAll, mShowmore;

    private ViewPager mSingleImage;
    private List<BannerData> ImagesArray = new ArrayList<BannerData>();
    private BannerAdapter mBannerAdapter;
    private int count = 0;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private Timer timer;

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

//        new DownloadImageTask
//                ("https://cdn.dribbble.com/users/2005930/screenshots/5487747/dribbble-fpg-insurance-stbd_4x.png",
//                        mSingleImage);

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
        getBanner();


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
        mData.clear();

        Cloud.getNewsAndEvents(new Cloud.ResultListener() {
            @Override
            public void onResult(JSONObject result) {
                int returnCode;
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject = result;
                    returnCode = Integer.parseInt(jsonObject.get("code").toString());
                } catch (JSONException e){
                    returnCode = Cloud.DefaultReturnCode.INTERNAL_SERVER_ERROR;
                    e.printStackTrace();
                }

                if (returnCode != Cloud.DefaultReturnCode.SUCCESS){
                    //FAIL
                    try {
                        String message = jsonObject.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        generateResult(jsonArray);
                        mRecyclerView.setAdapter(mAdapter);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });

        mAdapter.notifyDataSetChanged();
    }

    private void generateResult(JSONArray jsonArray){
        ArrayList<String> pictures = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String title = Html.fromHtml(jsonObject.getString("title")).toString();
                String content = Html.fromHtml(jsonObject.getString("content")).toString();
                String link = jsonObject.getString("link");
                String postDate = jsonObject.getString("post_date");
                String categoryName = jsonObject.getString("category_name");

                JSONArray files = jsonObject.getJSONArray("files");

                if (files.length() > 0){
                    for (int m = 0; m < files.length(); m++){
                        JSONObject jsonPics = files.getJSONObject(m);
                        String pict = jsonPics.getString("file_url");
                        pictures.add(pict);
                    }
                } else {
                    pictures.add(null);
                }

                FirstSlideMenuData data = new FirstSlideMenuData(id, title, content, link, postDate, categoryName, pictures);
                mData.add(data);

                pictures.clear();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showClaimsOption(){
        Bundle args = new Bundle();
        ClaimsOptionFragment coverBannerDialogFragment = new ClaimsOptionFragment();
        coverBannerDialogFragment.setArguments(args);
        coverBannerDialogFragment.show(getFragmentManager(), null);
    }

    private void getBanner(){
        ImagesArray.clear();
        Cloud.getBanner(new Cloud.ResultListener() {
            @Override
            public void onResult(JSONObject result) {
                int returnCode;
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject = result;
                    returnCode = Integer.parseInt(jsonObject.get("code").toString());
                } catch (JSONException e){
                    returnCode = Cloud.DefaultReturnCode.INTERNAL_SERVER_ERROR;
                    e.printStackTrace();
                }

                if (returnCode != Cloud.DefaultReturnCode.SUCCESS){
                    //FAIL
                    try {
                        String message = jsonObject.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        generateBannerResult(jsonArray);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });

        mAdapter.notifyDataSetChanged();
    }

    private void generateBannerResult(JSONArray jsonArray){
        final ArrayList<String> bannerPictures = new ArrayList<>();
        final ArrayList<String> bannerLinks = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String title = Html.fromHtml(jsonObject.getString("title")).toString();
                final String link = jsonObject.getString("link");
                String postDate = jsonObject.getString("post_date");
                String bannerLocationName = jsonObject.getString("banner_location_name");

                JSONArray files = jsonObject.getJSONArray("files");
                JSONObject obj = files.getJSONObject(0);
                String pic = obj.getString("file_url");

                if (files.length() > 0){
                    for (int m = 0; m < files.length(); m++){
                        JSONObject jsonPics = files.getJSONObject(m);
                        String pict = jsonPics.getString("file_url");
                        bannerPictures.add(pict);
                    }
                } else {
                    bannerPictures.add(null);
                }

                bannerLinks.add(link);

                final BannerData data = new BannerData(id, title, bannerLinks, postDate, bannerLocationName, bannerPictures);
                ImagesArray.add(data);

                mBannerAdapter = new BannerAdapter(bannerPictures,bannerLinks,getContext());
                mSingleImage.setAdapter(mBannerAdapter);
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (count <= 5){
                                    mSingleImage.setCurrentItem(count);
                                    count++;
                                }else {
                                    count = 0;
                                    mSingleImage.setCurrentItem(count);
                                }
                            }
                        });
                    }
                },5000,5000);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
