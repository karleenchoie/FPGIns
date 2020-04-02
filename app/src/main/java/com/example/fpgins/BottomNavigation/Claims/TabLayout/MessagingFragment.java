package com.example.fpgins.BottomNavigation.Claims.TabLayout;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fpgins.BottomNavigation.Claims.SubmittedFormsActivity;
import com.example.fpgins.DataModel.MessagingData;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Login.SnackBarNotificationUtil;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;
import com.example.fpgins.ui.NotificationMessage.NotifMessageFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagingFragment extends Fragment {

    private List<MessagingData> mData = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MessagingAdapter mAdapter;
    private EditText mMessage;
    private ImageView mSend;


    private SwipeRefreshLayout mSwipeRefresh;

    public MessagingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_messaging, container, false);

        mMessage = root.findViewById(R.id.edt_message);
        mSend = root.findViewById(R.id.img_send);

        UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(getActivity()));
        final String claimNo = userData.getSelectedClaimNo();
        final String id = userData.getId();

        mRecyclerView = root.findViewById(R.id.rv_Messaging);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top,
                                       int right, int bottom, int oldLeft,
                                       int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom){
                    mRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter.getItemCount() != 0){
                                mRecyclerView.scrollToPosition(mRecyclerView.getAdapter().getItemCount() -1);
                            }
                        }
                    }, 100);
                }
            }
        });

        mSwipeRefresh = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.fpg_orange));
        mSwipeRefresh.setDistanceToTriggerSync(450);// in dips
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(true);
                //preparing data all again when ever pull to refresh is active
                getAllMessages(claimNo);
            }

        });

        mAdapter = new MessagingAdapter(mData, getActivity());
        mSend.setEnabled(false);
        mSend.setAlpha((float) 0.5);

        mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollTo(0, mRecyclerView.getBottom());
            }
        });

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mMessage.getText().toString();
                if (message.contentEquals("")){
                    Toast.makeText(getActivity(), "Enter message", Toast.LENGTH_SHORT).show();
                } else {
                    String[] info = {claimNo, id, message};
                    sendMessage(info);
                    getAllMessages(claimNo);
                }

            }
        });

        mMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mSend.setEnabled(false);
                mSend.setAlpha((float) 0.5);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mMessage.getText().length() != 0){
                    mSend.setEnabled(true);
                    mSend.setAlpha((float) 1.0);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mMessage.getText().length() == 0){
                    mSend.setEnabled(false);
                    mSend.setAlpha((float) 0.5);
                }
            }
        });

        getAllMessages(claimNo);
        layoutManager.scrollToPositionWithOffset(mAdapter.getItemCount() - 1, 0);
        return root;
    }


    private void sendMessage(String[] info){
        Cloud.sendMessage(info, new Cloud.ResultListener() {
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
                        mSwipeRefresh.setRefreshing(false);
                        String message = jsonObject.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        mSwipeRefresh.setRefreshing(false);
                        Toast.makeText(getActivity(), "Message sent", Toast.LENGTH_SHORT).show();
                        mMessage.setText("");
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
    }

    private void getAllMessages(String claimNo){
        mData.clear();
        mSwipeRefresh.setRefreshing(true);
        Cloud.getAllMessages(claimNo, new Cloud.ResultListener() {
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
                        mSwipeRefresh.setRefreshing(false);
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
                String message = jsonObject.getString("message");
                String createdBy = jsonObject.getString("created_by");
                String createdWhen = jsonObject.getString("created_when");

                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date datevalue = input.parse(createdWhen);
                SimpleDateFormat output = new SimpleDateFormat("MMM dd, yyyy | HH:mm");
                String y = output.format(datevalue);

                MessagingData data = new MessagingData(id, createdBy, message, y);
                mData.add(data);
            }

            mRecyclerView.scrollToPosition(mData.size() - 1);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
