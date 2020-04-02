package com.example.fpgins.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpgins.CreatePassword;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;
import com.example.fpgins.Utility.DefaultDialog;
import com.example.fpgins.ui.NotificationMessage.NotifMessageFragment;
import com.mindorks.paracamera.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class VerificationCodeActivity extends AppCompatActivity {

    private EditText mFirstNum;
    private EditText mSecondNum;
    private EditText mThirdNum;
    private EditText mFourthNum;

    private TextView mTvEmail;
    private TextView mTvResend;

    private Button mVerify;

    private String mEmail;
    private String mGeneratedCode;
    private String mAuth;

    private boolean isForgotPassword;

    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        mFirstNum = findViewById(R.id.etFirstNum);
        mSecondNum = findViewById(R.id.etSecondNum);
        mThirdNum = findViewById(R.id.etThirdNum);
        mFourthNum = findViewById(R.id.etFourthNum);

        mTvEmail = findViewById(R.id.tvEmail);
        mTvResend = findViewById(R.id.resend);

        mVerify = findViewById(R.id.btnVerify);

        Bundle bundle = getIntent().getExtras();

        mEmail = bundle.getString("email");
        mGeneratedCode = bundle.getString("generatedCode");
        mAuth = bundle.getString("auth");
        isForgotPassword = bundle.getBoolean("isForgotPassword");

        String startEmail = mEmail.substring(0, mEmail.indexOf("@"));
        String endEMail = mEmail.substring(mEmail.indexOf("@"));

        String emailEncypted = startEmail.replace(startEmail.substring(3), "••••••••");

        mTvEmail.setText(emailEncypted + endEMail);

        mDialog = createLoadingDialog();

        mFirstNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mFirstNum.getText().toString().length() == 1){
                    mSecondNum.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSecondNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mSecondNum.getText().toString().length() == 1){
                    mThirdNum.requestFocus();
                } else {
                    mFirstNum.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mThirdNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mThirdNum.getText().toString().length() == 1){
                    mFourthNum.requestFocus();
                } else {
                    mSecondNum.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFourthNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mFourthNum.getText().toString().length() != 1){
                    mThirdNum.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
                Cloud.resendVerification(mEmail, new Cloud.ResultListener() {
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
                                Toast.makeText(VerificationCodeActivity.this, message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        } else {
                            //SUCCESS
                            try {
                                mDialog.dismiss();
                                JSONObject obj = new JSONObject(jsonObject.getString("record"));
                                mEmail = obj.getString("email");
                                mGeneratedCode = obj.getString("code");
                                mAuth = obj.getString("authentication");
                                Toast.makeText(VerificationCodeActivity.this, "Verification code has been sent", Toast.LENGTH_SHORT).show();
                            } catch (Exception e){
                                e.getMessage();
                            }
                        }


                    }
                });
            }
        });

        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (allFieldsEmpty()){
                    Toast.makeText(VerificationCodeActivity.this, getResources().getString(R.string.fields_error), Toast.LENGTH_SHORT).show();
                } else {
                    mDialog.show();

                    String authentication = mFirstNum.getText().toString() +
                            mSecondNum.getText().toString() +
                            mThirdNum.getText().toString() +
                            mFourthNum.getText().toString();

                    String[] information = {mEmail, mGeneratedCode, mAuth};

                    if (mAuth.contentEquals(authentication)){
                        if (isForgotPassword){
                            Intent intent = new Intent(VerificationCodeActivity.this, CreatePassword.class);

                            intent.putExtra("email", mEmail);
                            intent.putExtra("generatedCode", mGeneratedCode);
                            intent.putExtra("auth", mAuth);
                            intent.putExtra("isForgotPassword", isForgotPassword);
                            startActivity(intent);
                        } else {
                            postVerification(information);
                        }
                    } else {
                        Toast.makeText(VerificationCodeActivity.this, "Verification code is not valid", Toast.LENGTH_SHORT).show();
                    }

                    mDialog.dismiss();
                }

            }
        });
    }

    private void postVerification (String[] information){
        Cloud.twoWayVerification(information, new Cloud.ResultListener() {
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
                        Toast.makeText(VerificationCodeActivity.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        new DefaultDialog.Builder(VerificationCodeActivity.this)
                                .imageResource(ContextCompat.getDrawable(VerificationCodeActivity.this, R.drawable.green_check), View.VISIBLE)
                                .message("Your account has been successfully verified")
                                .positiveAction("Ok", new DefaultDialog.OnClickListener() {
                                    @Override
                                    public void onClick(Dialog dialog, String et) {
                                        Intent intent = new Intent(VerificationCodeActivity.this, Login.class);
                                        startActivity(intent);
                                    }
                                })
                                .build()
                                .show();
                    } catch (Exception e){
                        e.getMessage();
                    }
                }
            }
        });
    }

    private boolean allFieldsEmpty() {
        EditText[] fields = { mFirstNum, mSecondNum, mThirdNum, mFourthNum };
        for(EditText f : fields) {
            if (f.getText().toString().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(VerificationCodeActivity.this, android.R.style.Theme_Black);
        View view = LayoutInflater.from(VerificationCodeActivity.this).inflate(R.layout.progress_bar, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        return dialog;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
