package com.example.fpgins.BottomNavigation.Claims.TabLayout;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpgins.BottomNavigation.Claims.ClaimsActivity;
import com.example.fpgins.BottomNavigation.Claims.ImagesAdapter;
import com.example.fpgins.BottomNavigation.Claims.SubmittedFormsActivity;
import com.example.fpgins.DataModel.Images;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ForminfoFragment extends Fragment {

    private Dialog mDialog;
    private TextView mName;
    private TextView mStatus;
    private TextView mDateFiled;
    private TextView mPolicyNo;
    private TextView mClaimNo;
    private TextView mPlateNo;
    private TextView mConductionNo;
    private TextView mLocation;
    private TextView mRemarks;

    private ArrayList<Images> mData = new ArrayList<>();
    private FormInfoImageAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;


    public ForminfoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forminfo, container, false);

        mName = view.findViewById(R.id.claimName);
        mStatus = view.findViewById(R.id.claimStatus);
        mDateFiled = view.findViewById(R.id.claimDateFiled);
        mPolicyNo = view.findViewById(R.id.claimPolicyNumber);
        mClaimNo = view.findViewById(R.id.claimNumber);
        mPlateNo = view.findViewById(R.id.claimPlateNumber);
        mConductionNo = view.findViewById(R.id.claimConductionNumber);
        mLocation = view.findViewById(R.id.claimLocation);
        mRemarks = view.findViewById(R.id.claimRemarks);

        //test
        mRecyclerView = view.findViewById(R.id.imageListView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new FormInfoImageAdapter(mData, getActivity());

        mDialog = createLoadingDialog();

        UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(getActivity()));

        String claimNo = userData.getSelectedClaimNo();

        getData(claimNo);

        return view;
    }

    private void getData(String claimNo){
        mDialog.show();
        Cloud.getClaimsInfo(claimNo, new Cloud.ResultListener() {
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
                        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        JSONObject object = jsonObject.getJSONObject("record");
                        String firstName = object.getString("first_name");
                        String lastName = object.getString("last_name");
                        String status = object.getString("status_id");
                        String dateFiled = object.getString("send_when");
                        String policyNo = object.getString("policy_no");
                        String claimNo = object.getString("claim_no");
                        String plateNo = object.getString("plate_no");
                        String conductionNo = object.getString("conduction_sticker_no");
                        String location = object.getString("location");
                        String remarks = object.getString("remarks");

                        mName.setText(firstName + " " + lastName);
                        mStatus.setText(status);
                        mDateFiled.setText(dateFiled);
                        mPolicyNo.setText(policyNo);
                        mClaimNo.setText(claimNo);
                        mPlateNo.setText(plateNo);
                        mConductionNo.setText(conductionNo);
                        mLocation.setText(location);
                        mRemarks.setText(remarks);

                        //if there is no files
                        JSONArray jsonArray = object.getJSONArray("files");
                        generateResult(jsonArray);
                        mRecyclerView.setAdapter(mAdapter);
                        mDialog.dismiss();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
    }

    private void generateResult(JSONArray jsonArray){
        //pictures
        mData.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fileName = jsonObject.getString("file_name");
                String fileUrl = jsonObject.getString("file_url");
                String dateTaken = jsonObject.getString("created_when");

                Images data = new Images(fileUrl, "", null, fileName, dateTaken);

                mData.add(data);
            }
        } catch (Exception e){

        }
    }

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Black);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.progress_bar, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        return dialog;
    }

}
