package com.example.fpgins.Login;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.fpgins.BottomNavigation.BottomNavigationActivity;
import com.example.fpgins.BottomNavigation.FPGAssist.HelpActivity;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Login.Session.LogoutService;
import com.example.fpgins.Login.Session.UserSessionManager;
import com.example.fpgins.MainActivity;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.Utility.DefaultDialog;
import com.example.fpgins.Utility.NetworkUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpgins.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

//    comment

    private TextView mErrorMessage;
    private UserSessionManager mSession;
    private Button mLogin;
    private Dialog mDialog;
    private TextView mForgotPassword;
    private EditText mEmail;
    private EditText mPassword;
    private ImageView mBackButton;
    private LocationManager mLocationManager;
    private boolean isPressed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSession = UserSessionManager.getInstance(this);

        if (mSession.isLoggedIn()){
            goToLandingPage();
        }

        if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (Login.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) Login.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

//        mAvi = findViewById(R.id.avi);
        mErrorMessage = (TextView) findViewById(R.id.tvErrorMessage);
        mBackButton = findViewById(R.id.img_backbutton);
        mEmail = (EditText) findViewById(R.id.email);
        mEmail.addTextChangedListener(getTextWatcher(mEmail));
        mPassword = (EditText) findViewById(R.id.password);
        mPassword.addTextChangedListener(getTextWatcher(mPassword));
        mLogin = findViewById(R.id.btn_login);
        mLogin.setEnabled(false);
        mLogin.setAlpha((float) 0.5);
        mForgotPassword = (TextView) findViewById(R.id.txt_forgotpassword);
        mDialog = createLoadingDialog();

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
//                startActivity(intent);
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialog.show();
//                startAnimation();
//                mAvi.show();
                attemptLogin();

            }
        });

        mPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= (mPassword.getRight() - mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        if (isPressed){
                            Drawable icon = ContextCompat.getDrawable(Login.this, R.drawable.ic_action_eye);
                            icon.setBounds(0, mPassword.getHeight(), 0, mPassword.getHeight());
                            mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
                            mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }else {
                            Drawable icon = ContextCompat.getDrawable(Login.this, R.drawable.ic_action_slashedeye);
                            icon.setBounds(0, mPassword.getHeight(), 0, mPassword.getHeight());
                            mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
                            mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                        isPressed = !isPressed;
                        return true;
                    }
                }
                return false;
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

    }

    private TextWatcher getTextWatcher(final EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mLogin.setEnabled(false);
                mLogin.setAlpha((float) 0.5);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do what you want with your EditText
                if (mEmail.getText().length() != 0 && mPassword.getText().length() != 0){
                    mLogin.setEnabled(true);
                    mLogin.setAlpha(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (mEmail.getText().length() == 0 || mPassword.getText().length() == 0){
                    mLogin.setEnabled(false);
                    mLogin.setAlpha((float) 0.5);
                }

            }
        };
    }

    private void attemptLogin(){
        if (NetworkUtil.isConnected(Login.this)){
            loginProcess();
        } else {
            mDialog.dismiss();
            Toast.makeText(Login.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleLoginFailure(String msg){
        mErrorMessage.setText(msg);
        mErrorMessage.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mErrorMessage.setVisibility(View.INVISIBLE);
            }
        }, 6000);
    }

    private void loginProcess() {
        mEmail.setError(null);
        mPassword.setError(null);

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //Check for valid password, if the user entered one
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)){
            mPassword.setError(getString(R.string.password_error));
            focusView = mPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)){
            mEmail.setError(getString(R.string.email_empty));
            focusView = mEmail;
            cancel = true;
        }


        if (cancel){
            focusView.requestFocus();
            mDialog.dismiss();
        } else {
            //Use API to get all the info
            postLogin(email, password);
        }

    }

    private void postLogin(String email, String password){

        Cloud.login(email, password, new Cloud.ResultListener() {
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

                //FAIL
                if (returnCode != Cloud.DefaultReturnCode.SUCCESS){
                    mDialog.dismiss();
                    try {
                        String message = jsonObject.getString("message");
                        handleLoginFailure(message);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        JSONObject obj = new JSONObject(jsonObject.getString("record"));
                        generateUserData(obj);
                        goToLandingPage();
                    }catch (Exception e){
                        e.getMessage();
                    }
                }
            }
        });

    }

    private void generateUserData(JSONObject object) throws JSONException {
        String id = object.getString("id");
        String email = object.getString("email");
        String userName = object.getString("username");
        String firstName = object.getString("first_name");
        String lastName = object.getString("last_name");
        String acctCode = object.getString("account_code");
        String photo = object.getString("photo");

        UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(Login.this));
        userData.setId(id);
        userData.setEmail(email);
        userData.setUsername(userName);
        userData.setFirstName(firstName);
        userData.setLastName(lastName);
        userData.setAccountCode(acctCode);
        userData.setPhoto(photo);
        onHandleSuccessLogin(id);
    }

    private void onHandleSuccessLogin(String id){
        mSession = UserSessionManager.getInstance(Login.this);
        mSession.createLoginSession(mEmail.getText().toString(), id);
    }

    private void goToLandingPage(){
        Intent i = new Intent(Login.this, BottomNavigationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
        finish();
    }

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(Login.this, android.R.style.Theme_Black);
        View view = LayoutInflater.from(Login.this).inflate(R.layout.progress_bar, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        return dialog;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with logic process
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with logic process
        return password.length() >= 8;
    }

    private void startLoginService() {
        Intent intent = new Intent(Login.this, LogoutService.class);
        startService(intent);
    }




    private void fingerprint(){
        new DefaultDialog.Builder(this)
                .message("One-touch Sign In")
                .detail("Please place your fingertip on the scanner to verify your identity")
                .imageResource(ContextCompat.getDrawable(this, R.drawable.ic_action_fingerprint), View.VISIBLE)
                .negativeAction("Use Password", new DefaultDialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, String et) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

}
