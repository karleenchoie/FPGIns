package com.example.fpgins.Login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fpgins.BottomNavigation.BottomNavigationActivity;
import com.example.fpgins.BottomNavigation.FPGAssist.HelpActivity;
import com.example.fpgins.Login.Session.UserSessionManager;
import com.example.fpgins.R;

public class SplashScreen extends Activity{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mSession = UserSessionManager.getInstance(this);

        if (mSession.isLoggedIn()){
            goToLandingPage();
        }

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
                Intent intent = new Intent(SplashScreen.this, Registration.class);
                startActivity(intent);
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
}