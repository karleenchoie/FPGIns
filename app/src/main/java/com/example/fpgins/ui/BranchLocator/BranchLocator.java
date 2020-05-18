package com.example.fpgins.ui.BranchLocator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fpgins.DataModel.BranchData;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BranchLocator extends AppCompatActivity {

    private ImageView mBackButton;
    private ArrayList<BranchData> mData = new ArrayList<>();
    private BranchAdapter mAdapter;
    private AVLoadingIndicatorView mProgressBranchLocator;
//    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_locator);

//        mRecyclerView = findViewById(R.id.mainOffice_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(BranchLocator.this));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        getBranch();

        mAdapter = new BranchAdapter(mData, BranchLocator.this);
        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void getBranch(){
        mData.clear();
        Cloud.getOfficeAddress(new Cloud.ResultListener() {
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
                        Toast.makeText(getApplicationContext(), "Please check your connection and try again",
                                Toast.LENGTH_LONG).show();
                        mProgressBranchLocator.setVisibility(View.GONE);
                        String message = jsonObject.getString("message");
                        Toast.makeText(BranchLocator.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        mProgressBranchLocator.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        generateResult(jsonArray);
//                        mRecyclerView.setAdapter(mAdapter);
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
                String name = jsonObject.getString("name");
                String address = jsonObject.getString("address");
                String contact_no = jsonObject.getString("contact_no");
                String email = jsonObject.getString("email");
                String fax_no = jsonObject.getString("fax_no");
                String office_country_name = jsonObject.getString("office_country_name");
                String office_type_name = jsonObject.getString("office_type_name");

                BranchData data = new BranchData(id, name, address, contact_no, email, fax_no, office_country_name, office_type_name);
                mData.add(data);
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
