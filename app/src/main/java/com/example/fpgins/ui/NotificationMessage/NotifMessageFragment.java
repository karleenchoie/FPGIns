package com.example.fpgins.ui.NotificationMessage;


import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fpgins.BottomNavigation.Claims.ClaimsActivity;
import com.example.fpgins.BottomNavigation.Claims.SubmittedFormsActivity;
import com.example.fpgins.DataModel.NotificationList;
import com.example.fpgins.DataModel.SubmittedFormsData;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Login.SnackBarNotificationUtil;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotifMessageFragment extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<NotificationList> mData = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefresh;
    private NotifMessageAdapter mAdapter;
    private ImageView mBackButton;
    private UserData mUserData;
    private ArrayList<String> mPictures = new ArrayList<String>();
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_messages);

        mDialog = createLoadingDialog();
        mBackButton = findViewById(R.id.img_backbutton);
        mRecyclerView = findViewById(R.id.notifList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mUserData = new UserData(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAdapter = new NotifMessageAdapter(mData, getApplicationContext());

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.fpg_orange));
        mSwipeRefresh.setDistanceToTriggerSync(450);// in dips
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(true);
                //preparing data all again when ever pull to refresh is active
                getList();
            }

        });


        getList();
    }

    private void getList(){
        mSwipeRefresh.setRefreshing(true);
        mData.clear();
        mDialog.show();
        Cloud.notificationAll(mUserData.getAccountCode(), new Cloud.ResultListener() {
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
                        Toast.makeText(NotifMessageFragment.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        mDialog.dismiss();
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
        mPictures.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                final String id = jsonObject.getString("id");
                String notificationTypeId = jsonObject.getString("notification_type_id");
                String notificationRecipientId = jsonObject.getString("notification_recipient_id");
                String title =  Html.fromHtml(jsonObject.getString("title")).toString();
                String content =  Html.fromHtml(jsonObject.getString("content")).toString();

                JSONArray pictures = jsonObject.getJSONArray("files");
                String pict = "";

                if (pictures.length() > 0){
                    for (int m = 0; m < pictures.length(); m++){
                        JSONObject jsonPics = pictures.getJSONObject(m);
                        String pic = jsonPics.getString("file_url");
                        mPictures.add(pic);
                    }
                    pict = mPictures.get(0);
                } else {
                    pict = "";
                }


                String link = jsonObject.getString("link");
                String postDate = jsonObject.getString("post_date");
                String postTime = jsonObject.getString("post_time");
                String notificationTypeName = jsonObject.getString("notification_type_name");
                String notificationRecipientName = jsonObject.getString("notification_recipient_name");

                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd"); //yyyy-MM-dd HH:mm:ss
                Date datevalue = input.parse(postDate);
                SimpleDateFormat output = new SimpleDateFormat("MMMM dd, yyyy");
                String y = output.format(datevalue);


                NotificationList data = new NotificationList(pict, title, content, y, postTime, mPictures, content, link);
                mData.add(data);
                mPictures.clear();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(NotifMessageFragment.this, android.R.style.Theme_Black);
        View view = LayoutInflater.from(NotifMessageFragment.this).inflate(R.layout.progress_bar, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        return dialog;
    }
}
