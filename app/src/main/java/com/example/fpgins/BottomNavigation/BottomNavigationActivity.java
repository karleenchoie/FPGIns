package com.example.fpgins.BottomNavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpgins.BottomNavigation.ClientDashboard.ClientDashboard;
import com.example.fpgins.BottomNavigation.Dashboard.DashboardFragment;
import com.example.fpgins.BottomNavigation.Partners.PartnersFragment;
import com.example.fpgins.BottomNavigation.Claims.MainClaimsFragment;
import com.example.fpgins.BottomNavigation.Products.ProductsFragment;
import com.example.fpgins.BottomNavigation.FPGAssist.FPGAssist;
import com.example.fpgins.BottomNavigation.Settings.SettingsFragment;
import com.example.fpgins.BottomSheetDialog.BottomSheetMaterialDialog;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Login.Login;
import com.example.fpgins.Login.Session.UserSessionManager;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.NewsLetterNotificationDialog;
import com.example.fpgins.R;
import com.example.fpgins.ui.BranchLocator.BranchLocator;
import com.example.fpgins.ui.NotificationMessage.NotifMessage;
import com.example.fpgins.ui.NotificationMessage.NotifMessageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mindorks.paracamera.Camera;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BottomNavigationActivity extends AppCompatActivity {

    //Try
    //MACKY
    private UserSessionManager mSession;
    private Camera mCamera;
    private String mImageName;
    private TextView mAppbarTitle;
    private ImageView mNotification,mBranchLocator,mShare;
    private String mCurrentTitle = "";
    private boolean isReadNewsLetter;
    private UserData mUserData;
    private int userType;
    private final int USER_BROADCAST_ALL = 1;

    boolean doubleBackToExitPressedOnce = false;

    BottomNavigationView navView;

    private ArrayList<String> mPictures = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.default_app_bar);
        View view = getSupportActionBar().getCustomView();

        mAppbarTitle = view.findViewById(R.id.app_bar_title);
        mAppbarTitle.setText(getResources().getString(R.string.dashboard));

        mUserData = new UserData(PreferenceManager.getDefaultSharedPreferences(this));

        mNotification = view.findViewById(R.id.img_notification);
        mNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotifMessageFragment.class);
                startActivity(intent);
            }
        });
        mBranchLocator = view.findViewById(R.id.img_branch);
        mBranchLocator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BranchLocator.class);
                startActivity(intent);
            }
        });
        mShare = view.findViewById(R.id.img_share);

        if (mUserData.getAccountCode().contentEquals("AGT")){
            userType = 2;
            mShare.setVisibility(View.VISIBLE);
        } else {
            userType = 3;
            mShare.setVisibility(View.GONE);
        }

        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "FPG Mobile\n");
                    String shareMessage = "You can click this link for installation of FPG Mobile app\n\nLink";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Choose One"));
                } catch(Exception e) {
                    e.getMessage();
                }
            }
        });

        showNewsLetter();

        navView = findViewById(R.id.nav_view);

        ClientDashboard clientDashboard = new ClientDashboard();
        setFragmentView(clientDashboard);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_claims:
                        ClientDashboard clientDashboard = new ClientDashboard();
                        setFragmentView(clientDashboard);
                        mAppbarTitle.setText(getResources().getString(R.string.dashboard));
                        showNewsLetter();
                        return true;
                    case R.id.navigation_products:
                        MainClaimsFragment productsFragment = new MainClaimsFragment();
                        setFragmentView(productsFragment);
                        mAppbarTitle.setText(getResources().getString(R.string.products));
                        return true;
                    case R.id.navigation_fpgassist:
                        FPGAssist fpgAssist = new FPGAssist();
                        setFragmentView(fpgAssist);
                        mAppbarTitle.setText(getResources().getString(R.string.fpg_assist));
                        return true;
                    case R.id.navigation_settings:
                        SettingsFragment settingsFragment = new SettingsFragment();
                        setFragmentView(settingsFragment);
                        mAppbarTitle.setText(getResources().getString(R.string.settings));
                        return true;
                    case R.id.navigation_partners:
                        PartnersFragment partnersFragment = new PartnersFragment();
                        setFragmentView(partnersFragment);
                        mAppbarTitle.setText(getResources().getString(R.string.partners));
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            BottomSheetMaterialDialog bottomSheetMaterialDialog = new BottomSheetMaterialDialog.Builder(BottomNavigationActivity.this)
                    .setTitle(getString(R.string.logout))
                    .setMessage(getString(R.string.logout_sure))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.logout) ,new BottomSheetMaterialDialog.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            mSession = UserSessionManager.getInstance(BottomNavigationActivity.this);
                            mSession.clearPrefs();
                            Logout();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), new BottomSheetMaterialDialog.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    })
                    .build();
            bottomSheetMaterialDialog.show();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
    }

    public void showNewsLetter(){
        Cloud.newNotification(mUserData.getId(), new Cloud.ResultListener() {
            @Override
            public void onResult(JSONObject result) {
                int returnCode;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = result;
                    returnCode = Integer.parseInt(jsonObject.get("code").toString());
                }catch (JSONException e){
                    returnCode = Cloud.DefaultReturnCode.INTERNAL_SERVER_ERROR;
                    e.printStackTrace();
                }

                if (returnCode != Cloud.DefaultReturnCode.SUCCESS){
                    //FAIL
                    try {
                        String message = jsonObject.getString("message");
                        Log.d("Server Error Message: ", message);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }else {
                    //SUCCESS
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        generateResult(jsonArray);
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
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                final String id = jsonObject.getString("id");
                String notificationTypeId = jsonObject.getString("notification_type_id");
                String notificationRecipientId = jsonObject.getString("notification_recipient_id");
                String title_ =  Html.fromHtml(jsonObject.getString("title")).toString();
                String content =  Html.fromHtml(jsonObject.getString("content")).toString();
                String pict = "";

                JSONArray pictures = jsonObject.getJSONArray("files");
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


                if (Integer.parseInt(notificationRecipientId) == userType || Integer.parseInt(notificationRecipientId) == USER_BROADCAST_ALL){
//                if (Integer.parseInt(id) == 47){
                    new NewsLetterNotificationDialog.Builder(BottomNavigationActivity.this)
                            .ids(id, notificationRecipientId, mUserData.getId())
                            .image(BottomNavigationActivity.this, pict)
                            .viewMoreInfo(title_, postDate, postTime, mPictures, content, link)
                            .newsText(content)
                            .viewMoreButton(new NewsLetterNotificationDialog.ViewMoreOnClickListener() {
                                @Override
                                public void onClick(Dialog dialog, String notificationId, String notificationRecipientId, String accountId, String title, String date, String time, ArrayList<String> pictures, String content, String link) {
                                    dialog.dismiss();
                                    String[] info = new String[]{notificationId, notificationRecipientId, accountId};
                                    dismissButton(info);

                                    Intent intent = new Intent(BottomNavigationActivity.this, NotifMessage.class);

                                    //Extras upon viewing
                                    intent.putExtra("title", title);
                                    intent.putExtra("date", date);
                                    intent.putExtra("time", time);
                                    intent.putExtra("pictures", pictures);
                                    intent.putExtra("content", content);
                                    intent.putExtra("link", link);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .dismissButton(new NewsLetterNotificationDialog.OnClickListener() {
                                @Override
                                public void onClick(Dialog dialog, final String notificationId, final String notificationRecipientId, final String accountId) {
                                    dialog.dismiss();
                                    String[] info = new String[]{notificationId, notificationRecipientId, accountId};
                                    dismissButton(info);

                                }
                            })
                            .build().show();
                }

                mPictures.clear();
            }
        } catch (Exception e){
            e.getMessage();
        }
    }

    private void dismissButton(String[] info){
        Cloud.dismissNotification(info, new Cloud.ResultListener() {
            @Override
            public void onResult(JSONObject result) {
                int returnCode;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = result;
                    returnCode = Integer.parseInt(jsonObject.get("code").toString());
                }catch (JSONException e){
                    returnCode = Cloud.DefaultReturnCode.INTERNAL_SERVER_ERROR;
                    e.printStackTrace();
                }

                if (returnCode != Cloud.DefaultReturnCode.SUCCESS){
                    //FAIL
                    try {
                        String message = jsonObject.getString("message");
                        Toast.makeText(BottomNavigationActivity.this, message, Toast.LENGTH_SHORT).show();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
//                else {
//                    //SUCCESS
//                    try {
////                        Toast.makeText(BottomNavigationActivity.this, "Notification has been closed", Toast.LENGTH_SHORT).show();
//                    } catch (Exception e) {
//                        e.getMessage();
//                    }
//                }
            }
        });
    }

    private void Logout(){
        mSession = UserSessionManager.getInstance(BottomNavigationActivity.this);
        mSession.clearPrefs();
        Intent i = new Intent(BottomNavigationActivity.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
        finish();
    }

    private void setFragmentView(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, fragment).commit();
    }
}
