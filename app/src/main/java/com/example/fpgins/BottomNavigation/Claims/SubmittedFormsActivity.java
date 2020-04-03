package com.example.fpgins.BottomNavigation.Claims;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpgins.DataModel.SubmittedFormsData;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Login.SnackBarNotificationUtil;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;
import com.example.fpgins.ui.NotificationMessage.NotifMessageFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubmittedFormsActivity extends AppCompatActivity {

    private List<SubmittedFormsData> mData = new ArrayList<>();
    private ImageView mBackButton;
    private RecyclerView mSubmittedList;
    private SubmittedFormsAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefresh;

    private View mNoContent;
    private TextView mTvInfo;

    private UserData mUserData;

    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_forms);

        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mNoContent = findViewById(R.id.noContent);
        mTvInfo = findViewById(R.id.tvResult);


        mDialog = createLoadingDialog();

        mUserData = new UserData(PreferenceManager.getDefaultSharedPreferences(SubmittedFormsActivity.this));

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.fpg_orange));
        mSwipeRefresh.setDistanceToTriggerSync(450);// in dips
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(true);
                //preparing data all again when ever pull to refresh is active
                checkComponents();
            }

        });

        mSubmittedList = findViewById(R.id.rv_submittedList);
        mSubmittedList.setLayoutManager(new LinearLayoutManager(SubmittedFormsActivity.this));
        mSubmittedList.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SubmittedFormsAdapter(mData, SubmittedFormsActivity.this);

        checkComponents();
    }

    private void checkComponents(){
        getData(mUserData.getId());

    }

    private void getData(String accountId){
        mDialog.show();
        mData.clear();

        Cloud.getSubmittedClaims(accountId, new Cloud.ResultListener() {
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
                        mDialog.dismiss();
                        String message = jsonObject.getString("message");
                        Toast.makeText(SubmittedFormsActivity.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        generateResult(jsonArray);
                        mSubmittedList.setAdapter(mAdapter);
                        mSwipeRefresh.setRefreshing(false);
                        mDialog.dismiss();
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
                String trasactionId = jsonObject.getString("transaction_id");
                String policyNo = jsonObject.getString("policy_no");
                String claimNo = jsonObject.getString("claim_no");
                String date = jsonObject.getString("send_when");
                String status = jsonObject.getString("status_id");
                String statusName = jsonObject.getString("status_name");

                if (!trasactionId.equals("") && !claimNo.equals("") && !policyNo.equals("")){
                    SubmittedFormsData data = new SubmittedFormsData(id, claimNo, policyNo, date, status, trasactionId, statusName);
                    mData.add(data);
                }
            }

            if (mData.size() == 0){
                mNoContent.setVisibility(View.VISIBLE);
                mTvInfo.setText("No submitted forms yet");
            } else {
                mNoContent.setVisibility(View.GONE);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void submittedFormsData() {
//        SubmittedFormsData data = new SubmittedFormsData("1C2L3A4I5M6N7O","0P9O8L7I6C5Y","2-24-2020 | 6:00AM","active");
//        mData.add(data);
//
//        data = new SubmittedFormsData("9C8L7A6I5M4N3O","1P2O3L4I5C6Y","2-22-2020 | 11:24PM","processing");
//        mData.add(data);
//
//        data = new SubmittedFormsData("8M2L3A4G5M6NLO","KP9Y8LMI6C56","1-21-2020 | 7:38AM","closed");
//        mData.add(data);

        mAdapter.notifyDataSetChanged();
    }

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(SubmittedFormsActivity.this, android.R.style.Theme_Black);
        View view = LayoutInflater.from(SubmittedFormsActivity.this).inflate(R.layout.progress_bar, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        return dialog;
    }
}
