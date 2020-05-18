package com.example.fpgins.BottomNavigation.Claims;


import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fpgins.DataModel.MainClaimsData;
import com.example.fpgins.DataModel.PartnersData;
import com.example.fpgins.DataModel.ProductsData;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainClaimsFragment extends Fragment {


//    private List<MainClaimsData> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MainClaimsAdapter mAdapter;
//    private LinearLayout mMotorCms;

    private SwipeRefreshLayout mSwipeRefresh;
    private List<ProductsData> mData = new ArrayList<>();
    private AVLoadingIndicatorView mProgressPartners;


    public MainClaimsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_claims, container, false);

//        mMotorCms = root.findViewById(R.id.lv_motorCms);
//        mMotorCms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ProductsFragment.class);
//                intent.putExtra("product_url", "https://www.google.com/");
//                startActivity(intent);
//            }
//        });

        mProgressPartners = root.findViewById(R.id.progress_products);
        recyclerView = root.findViewById(R.id.rvList);
        mAdapter = new MainClaimsAdapter(mData, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

//        inquireClaimsData();

        mSwipeRefresh = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.fpg_orange));
        mSwipeRefresh.setDistanceToTriggerSync(450);// in dips
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(true);
                //preparing data all again when ever pull to refresh is active
                getAllProducts();
            }

        });

        getAllProducts();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

//    private void inquireClaimsData() {
//        dataList.clear();
//
//        MainClaimsData data = new MainClaimsData
//                ("Motor", "Find out more",
//                        ContextCompat.getDrawable(getContext(), R.drawable.pic_four));
//        dataList.add(data);
//
//        data = new MainClaimsData
//                ("Travel", "Find out more" ,
//                        ContextCompat.getDrawable(getContext(), R.drawable.pic_five));
//        dataList.add(data);
//
////        data = new MainClaimsData
////                ("Motor", "Web CMS" ,
////                        ContextCompat.getDrawable(getContext(), R.drawable.pic_eight));
////        dataList.add(data);
//
//        mAdapter.notifyDataSetChanged();
//    }

    private void getAllProducts() {
        mData.clear();
        Cloud.getAllProducts(new Cloud.ResultListener() {
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
                        Toast.makeText(getContext(), "Please check your connection and try again",
                                Toast.LENGTH_LONG).show();
                        mProgressPartners.setVisibility(View.GONE);
                        String message = jsonObject.getString("message");
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        mProgressPartners.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        generateResult(jsonArray);
                        recyclerView.setAdapter(mAdapter);
                        mSwipeRefresh.setRefreshing(false);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
    }

    private void generateResult(JSONArray jsonArray){
        try {
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String title = jsonObject.getString("title");
                String content = jsonObject.getString("content");
                String link = jsonObject.getString("link");
                String officeCountryName = jsonObject.getString("office_country_name");

                JSONArray files = jsonObject.getJSONArray("files");
                JSONObject obj = files.getJSONObject(0);
                String pic = obj.getString("file_url");

                ProductsData productsData = new ProductsData(id, title, content, link, officeCountryName, pic);
                mData.add(productsData);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
