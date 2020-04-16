package com.example.fpgins.ui.BranchLocator;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.fpgins.DataModel.BranchData;
import com.example.fpgins.ExpandableListAdapter;
import com.example.fpgins.MenuModel;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BranchExpandableList extends AppCompatActivity {

    private ImageView mBackButton;
    private List<BranchData> mData = new ArrayList<>();
//    private List<BranchData> mDataChild = new ArrayList<>();
    private HashMap<BranchData, List<BranchData>> mDataChild = new HashMap<>();
    private ExpandableListView mExpandableListView;
    private BranchExpandableListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_locator);


        mExpandableListView = (ExpandableListView) findViewById(R.id.expandableList);

        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setChildIndicator(null);
        mExpandableListView.setChildDivider(ContextCompat.getDrawable(BranchExpandableList.this, R.color.white));
        mExpandableListView.setDivider(ContextCompat.getDrawable(BranchExpandableList.this, R.color.white));
        mExpandableListView.setDividerHeight(2);


        mAdapter = new BranchExpandableListAdapter(mData, mDataChild, this);

        getBranch();

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
        mDataChild.clear();
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
                        String message = jsonObject.getString("message");
                        Toast.makeText(BranchExpandableList.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        generateResult(jsonArray);
                        mExpandableListView.setAdapter(mAdapter);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });

    }

    private void generateResult(JSONArray jsonArray){

        BranchData headerData = null;
        BranchData childData = null;
        List<BranchData> indoBranch = new ArrayList<BranchData>();
        List<BranchData> phBranch = new ArrayList<BranchData>();
        List<BranchData> thBranch = new ArrayList<BranchData>();
        try {
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray indonesia = jsonObject.getJSONArray("indonesia");
                JSONArray philippines = jsonObject.getJSONArray("philippines");
                JSONArray thailand = jsonObject.getJSONArray("thailand");

                generateEacItem(indonesia);
                generateEacItem(philippines);
                generateEacItem(thailand);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void generateEacItem(JSONArray jsonArray){
        List<BranchData> branch = new ArrayList<BranchData>();
        try {
            for (int m = 0; m < jsonArray.length(); m++){
                JSONObject jsonObject = jsonArray.getJSONObject(m);
                JSONArray headOffice = jsonObject.getJSONArray("head-office");
                JSONArray branches = jsonObject.getJSONArray("branch");

                BranchData branchData = null;

                for (int p = 0; p < headOffice.length(); p++){
                    JSONObject ho = headOffice.getJSONObject(p);
                    String id = ho.getString("id");
                    String name = ho.getString("name");
                    String address = ho.getString("address");
                    String contact_no = ho.getString("contact_no");
                    String email = ho.getString("email");
                    String fax_no = ho.getString("fax_no");
                    String office_country_name = ho.getString("office_country_name");
                    String office_type_name = ho.getString("office_type_name");
                    branchData = new BranchData(id, name, address, contact_no, email, fax_no, office_country_name, office_type_name);
                    mData.add(branchData);

                }

                for (int n = 0; n < branches.length(); n++){
                    JSONObject ho = branches.getJSONObject(n);
                    String id = ho.getString("id");
                    String name = ho.getString("name");
                    String address = ho.getString("address");
                    String contact_no = ho.getString("contact_no");
                    String email = ho.getString("email");
                    String fax_no = ho.getString("fax_no");
                    String office_country_name = ho.getString("office_country_name");
                    String office_type_name = ho.getString("office_type_name");
                    BranchData branchData1 = new BranchData(id, name, address, contact_no, email, fax_no, office_country_name, office_type_name);

                    branch.add(branchData1);
                    mDataChild.put(branchData, branch);

                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        branch.clear();
    }

    public List<BranchData> hasChildren(String countryName, List<BranchData> list){
        List<BranchData> newList = new ArrayList<>();


        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
