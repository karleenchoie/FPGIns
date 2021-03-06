package com.example.fpgins.Login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fpgins.BottomNavigation.BottomNavigationActivity;
import com.example.fpgins.BottomNavigation.FPGAssist.HelpActivity;
import com.example.fpgins.BottomSheetDialog.BottomsheetFragment;
import com.example.fpgins.Login.Session.UserSessionManager;
import com.example.fpgins.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SplashScreen extends AppCompatActivity {

    private ViewGroup tContainer,tContainer2;
    private RelativeLayout rellay1, rellay2;
    private ImageView mCompany, mLogo;
    private Button mLogin, mSignup;
    private TextView mHelp;
    private boolean visible;
    private boolean visible2;
    private UserSessionManager mSession;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            onFadeIn();
        }
    };

    Handler handler2 = new Handler();
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            onFadeIn2();
        }
    };

    Handler handler3 = new Handler();
    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
        }
    };

    private JSONArray jsonArray;
    private OkHttpClient mClient = new OkHttpClient();
    private String token;

    private static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String FIREBASE_LEGACY_SERVER_KEY = "AAAARgcJM98:APA91bF1HA9vwyqgPQzqbxv9VknlAGq7X6xyL3pnFn5t2FwPtY7q-0dzcaPJ18YiMx63BWX9pTRpsxJeAxxk9LPm2MHDr9b0m-zoR5p1LZXxio5RWJkHIiBEgEHrFar4QltHjk7P5OWf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        mSession = UserSessionManager.getInstance(this);

        if (mSession.isLoggedIn()){
            goToLandingPage();
        }

        getToken();

        tContainer = findViewById(R.id.transition_container);
        tContainer2 = findViewById(R.id.relative_logo);
        rellay1 = findViewById(R.id.rellay1);
        mCompany = findViewById(R.id.imgView_company);
        mLogo = findViewById(R.id.imgView_logo);
        mLogin = findViewById(R.id.btn_login);
        mSignup = findViewById(R.id.btn_signup);
        mHelp = findViewById(R.id.txt_NeedHelp);

        handler.postDelayed(runnable, 1500);
        handler2.postDelayed(runnable2, 2500);
        handler3.postDelayed(runnable3, 3500);


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showBottomSheetDialogFragment();


//                BottomSheetMaterialDialog bottomSheetMaterialDialog = new BottomSheetMaterialDialog.Builder(SplashScreen.this)
//                        .setTitle(getString(R.string.choose_account_type))
//                        .setMessage(getString(R.string.detail_choose))
//                        .setCancelable(false)
//                        .setPositiveButton(getString(R.string.agent) ,new BottomSheetMaterialDialog.OnClickListener(){
//
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int which) {
//                                Intent intent = new Intent(SplashScreen.this, AgentRegistration.class);
//                                startActivity(intent);
//                                dialogInterface.dismiss();
//                            }
//                        })
//                        .setNegativeButton(getString(R.string.client), new BottomSheetMaterialDialog.OnClickListener(){
//
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int which) {
//                                Intent intent = new Intent(SplashScreen.this, Registration.class);
//                                startActivity(intent);
//                                dialogInterface.dismiss();
//                            }
//                        })
//                        .build();
//                bottomSheetMaterialDialog.show();
            }
        });

        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreen.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        onHideKeyboard();
    }

    private void goToLandingPage(){
        Intent i = new Intent(SplashScreen.this, BottomNavigationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
        finish();
    }

    private void onMove(){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mLogo, "x", -320f);
        animatorX.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();
    }

    public void onFadeIn(){
        TransitionManager.beginDelayedTransition(tContainer);
        visible = !visible;
        mLogo.setVisibility(visible ? View.VISIBLE: View.GONE);
    }

    public void onFadeIn2(){
        TransitionManager.beginDelayedTransition(tContainer2);
        visible2 = !visible2;
        mCompany.setVisibility(visible2 ? View.VISIBLE: View.GONE);
    }

    public void onHideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void showBottomSheetDialogFragment() {
        BottomsheetFragment bottomSheetFragment = new BottomsheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    public void getToken(){
        // Get token
        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("GET TOKEN", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        SharedPreferences.Editor editor = getSharedPreferences("firebase_token", MODE_PRIVATE).edit();
                        editor.putString("token", token);
                        editor.apply();

                        //jsonArray is a String array of registration ids, you want to send this message to.
                        // Keep in mind that the registration ids must always be in an array, even if you want it to send to a single recipient.
                        jsonArray = new JSONArray();
                        jsonArray.put(token);
                        Log.d("GET TOKEN", token);

                    }
                });
    }
}