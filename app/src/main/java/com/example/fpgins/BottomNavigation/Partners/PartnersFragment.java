package com.example.fpgins.BottomNavigation.Partners;


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

import com.example.fpgins.BottomNavigation.Claims.MainClaimsAdapter;
import com.example.fpgins.BottomNavigation.Claims.SubmittedFormsActivity;
import com.example.fpgins.DataModel.MainClaimsData;
import com.example.fpgins.DataModel.PartnersData;
import com.example.fpgins.DataModel.SubmittedFormsData;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.Network.ImageUploaderUtility.DownloadImageTask;
import com.example.fpgins.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartnersFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PartnersAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<PartnersData> mData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_partners, container, false);

        mRecyclerView = view.findViewById(R.id.rv_Partners);
        mAdapter = new PartnersAdapter(mData, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.fpg_orange));
        mSwipeRefresh.setDistanceToTriggerSync(450);// in dips
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(true);
                //preparing data all again when ever pull to refresh is active
                getAllPartners();
            }

        });


        getAllPartners();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void getAllPartners() {
        mData.clear();

        Cloud.getAllPartners(new Cloud.ResultListener() {
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
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        generateResult(jsonArray);
                        mRecyclerView.setAdapter(mAdapter);
                        mSwipeRefresh.setRefreshing(false);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
    }

    private void generateResult(JSONArray jsonArray){
        String dateToday = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String companyPic = "";
        try {
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String title = jsonObject.getString("title");
                String content = jsonObject.getString("content");
                String link = jsonObject.getString("link");
                String postDate = jsonObject.getString("post_date");
                String expiryDate = jsonObject.getString("expiry_date");
                String partnerClientName = jsonObject.getString("partner_client_name");

                JSONArray files = jsonObject.getJSONArray("files");
                JSONObject obj = files.getJSONObject(0);
                String pic = obj.getString("file_url");

                if (dateToday.compareTo(expiryDate) < 0){
                    PartnersData partnersData = new PartnersData(id, title, content, link, postDate, expiryDate, partnerClientName, pic);
                    mData.add(partnersData);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
