package com.example.fpgins.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpgins.CreatePassword;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;
import com.example.fpgins.Utility.DefaultDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {

    private EditText mUsername;
    private TextView mTvErrorMessage;
    private Button mDone;
    private ImageView mBack;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mBack = findViewById(R.id.img_backbutton);
        mUsername = findViewById(R.id.edt_username);
        mUsername.addTextChangedListener(getTextWatcher(mUsername));
        mDone = findViewById(R.id.btn_done);
        mDone.setEnabled(false);
        mDone.setAlpha((float) 0.5);

        mTvErrorMessage = findViewById(R.id.tvErrorMessage);

        mDialog = createLoadingDialog();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
                String email = mUsername.getText().toString();

                if (email.isEmpty()){
                    mDialog.dismiss();
                    handleFailure("Email is necessary");
                } else {
                   postForgotPassword(email);
                }

            }
        });

    }

    private void handleFailure(String msg){
        mTvErrorMessage.setText(msg);
        mTvErrorMessage.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTvErrorMessage.setVisibility(View.INVISIBLE);
            }
        }, 6000);
    }

    private void postForgotPassword(final String email){
        Cloud.forgotPassword(email, new Cloud.ResultListener() {
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
                        Toast.makeText(ForgotPassword.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {

                        JSONObject obj = new JSONObject(jsonObject.getString("record"));
                        String generatedCode = obj.getString("code");
                        String auth = obj.getString("authentication");
                        Intent intent = new Intent(ForgotPassword.this, VerificationCodeActivity.class);

                        intent.putExtra("email", email);
                        intent.putExtra("generatedCode", generatedCode);
                        intent.putExtra("auth", auth);
                        intent.putExtra("isForgotPassword", true);

                        startActivity(intent);
                        mDialog.dismiss();

                    } catch (Exception e){
                        e.getMessage();
                    }
                }
            }
        });
    }

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(ForgotPassword.this, android.R.style.Theme_Black);
        View view = LayoutInflater.from(ForgotPassword.this).inflate(R.layout.progress_bar, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        return dialog;
    }

    private TextWatcher getTextWatcher(final EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDone.setEnabled(false);
                mDone.setAlpha((float) 0.5);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do what you want with your EditText
                if (mUsername.getText().length() != 0){
                    mDone.setEnabled(true);
                    mDone.setAlpha(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (mUsername.getText().length() == 0){
                    mDone.setEnabled(false);
                    mDone.setAlpha((float) 0.5);
                }

            }
        };
    }
}
