package com.example.fpgins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Login.ForgotPassword;
import com.example.fpgins.Login.Login;
import com.example.fpgins.Login.SnackBarNotificationUtil;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.Utility.DefaultDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class CreatePassword extends AppCompatActivity {

    ImageView mBack;

    private EditText mPassword;
    private EditText mRetypePassword;

    private Button mUpdate;

    private TextView mTvErrorMessage, mStringOne, mStringTwo;

    private String mEmail;
    private String mGeneratedCode;
    private String mAuth;
    private String mUsername;

    private ImageView mCheckUpper;
    private ImageView mCheckLower;
    private ImageView mCheckNumber;
    private ImageView mCheckChar;

    private Dialog mDialog;

    private boolean isForgotPassword;
    private boolean isPressed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        mStringOne = findViewById(R.id.txt_stringOne);
        mStringTwo = findViewById(R.id.txt_stringTwo);
        mPassword = findViewById(R.id.etNewPw);
        mPassword.addTextChangedListener(getTextWatcher(mPassword));
        mRetypePassword = findViewById(R.id.etRetypePw);
        mRetypePassword.addTextChangedListener(getTextWatcher(mRetypePassword));
        mUpdate = findViewById(R.id.btnUpdatePassword);
        mUpdate.setEnabled(false);
        mUpdate.setAlpha((float) 0.5);
        mBack = findViewById(R.id.img_backbutton);

        mTvErrorMessage = findViewById(R.id.tvErrorMessage);

        UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            mStringOne.setVisibility(View.GONE);
            mStringTwo.setVisibility(View.GONE);
            mUsername = userData.getUsername();
            mEmail = userData.getEmail();
        }else {
            mEmail = bundle.getString("email");
            mGeneratedCode = bundle.getString("generatedCode");
            mAuth = bundle.getString("auth");
            isForgotPassword = bundle.getBoolean("isForgotPassword");
            mBack.setVisibility(View.GONE);
        }

        //checkmarks
        mCheckUpper = (ImageView) findViewById(R.id.checkmark1);
        mCheckLower = (ImageView) findViewById(R.id.checkmark2);
        mCheckNumber = (ImageView) findViewById(R.id.checkmark3);
        mCheckChar = (ImageView) findViewById(R.id.checkmark4);

        mDialog = createLoadingDialog();

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = mPassword.getText().toString();

                if (text.length() != 0){
                    mPassword.setBackground(ContextCompat.getDrawable(CreatePassword.this, R.drawable.button_rectangle));
                }

                if (text.matches(".*[A-Z].*")){
                    mCheckUpper.setImageDrawable(getDrawable(R.drawable.green_check));
                } else {
                    mCheckUpper.setImageDrawable(getDrawable(R.drawable.gray_check));
                }

                if (text.matches(".*[a-z].*")){
                    mCheckLower.setImageDrawable(getDrawable(R.drawable.green_check));
                } else {
                    mCheckLower.setImageDrawable(getDrawable(R.drawable.gray_check));
                }

                if (text.matches(".*\\d.*")){
                    mCheckNumber.setImageDrawable(getDrawable(R.drawable.green_check));
                } else {
                    mCheckNumber.setImageDrawable(getDrawable(R.drawable.gray_check));
                }

                if (text.length() >= 8){
                    mCheckChar.setImageDrawable(getDrawable(R.drawable.green_check));
                } else {
                    mCheckChar.setImageDrawable(getDrawable(R.drawable.gray_check));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= (mPassword.getRight() - mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        if (isPressed){
                            Drawable icon = ContextCompat.getDrawable(CreatePassword.this, R.drawable.ic_action_eye);
                            icon.setBounds(0, mPassword.getHeight(), 0, mPassword.getHeight());
                            mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
                            mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }else {
                            Drawable icon = ContextCompat.getDrawable(CreatePassword.this, R.drawable.ic_action_slashedeye);
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

        mRetypePassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= (mRetypePassword.getRight() - mRetypePassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        if (isPressed){
                            Drawable icon = ContextCompat.getDrawable(CreatePassword.this, R.drawable.ic_action_eye);
                            icon.setBounds(0, mRetypePassword.getHeight(), 0, mRetypePassword.getHeight());
                            mRetypePassword.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
                            mRetypePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }else {
                            Drawable icon = ContextCompat.getDrawable(CreatePassword.this, R.drawable.ic_action_slashedeye);
                            icon.setBounds(0, mRetypePassword.getHeight(), 0, mRetypePassword.getHeight());
                            mRetypePassword.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
                            mRetypePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                        isPressed = !isPressed;
                        return true;
                    }
                }
                return false;
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
                String newPw = mPassword.getText().toString();
                String retypePw = mRetypePassword.getText().toString();

                if (!validateInfo()){
                    mDialog.dismiss();
                    return;
                } else {
                    //postAPI
                    if (isForgotPassword){
                        String[] information = {mEmail, mGeneratedCode, mAuth, newPw, retypePw};
                        postNewPassword(information);
                    } else {
                        String[] information = {mEmail,mUsername, newPw, retypePw};
                        postChangePassword(information);
                    }

                    mDialog.dismiss();
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private TextWatcher getTextWatcher(final EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUpdate.setEnabled(false);
                mUpdate.setAlpha((float) 0.5);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do what you want with your EditText
                if (mPassword.getText().length() != 0 && mRetypePassword.getText().length() != 0){
                    mUpdate.setEnabled(true);
                    mUpdate.setAlpha(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (mPassword.getText().length() == 0 || mRetypePassword.getText().length() == 0){
                    mUpdate.setEnabled(false);
                    mUpdate.setAlpha((float) 0.5);
                }

            }
        };
    }

    private boolean validateInfo(){
        String password = mPassword.getText().toString();

        if (allFieldsEmpty()){
            return false;
        }

        if (!isPasswordLongEnough()){
            handleFailure(getString(R.string.password_length));
            return false;
        } else {
            mPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
        }

        if (!password.matches(".*[A-Z].*")){
            handleFailure(getString(R.string.password_capital_letter));
            return false;
        } else {
            mPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
        }

        if (!password.matches(".*[a-z].*")){
            handleFailure(getString(R.string.password_small_letter));
            return false;
        } else {
            mPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
        }

        if (!password.matches(".*\\d.*")){
            handleFailure(getString(R.string.password_number));
            return false;
        }

        if (!isPasswordSame()){
            handleFailure(getString(R.string.password_unmatched));
            return false;
        } else {
            mRetypePassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
        }

        mPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
        mRetypePassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));

        return true;
    }

    private boolean isPasswordLongEnough() {
        String password = mPassword.getText().toString();
        if (password.length() >= 8) {
            mPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle_red));
            return true;
        } else {
            return false;
        }
    }

    private boolean isPasswordSame() {
        String password1 = mPassword.getText().toString();
        String password2 = mRetypePassword.getText().toString();

        boolean longEnough = isPasswordLongEnough();
        if (longEnough) {
            if (password1.contentEquals(password2)) {
                mRetypePassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
                return true;
            } else {
                mRetypePassword.setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle_red));
                return false;
            }
        }else{
            handleFailure(getString(R.string.password_length));
            return false;
        }
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

    private void postChangePassword(final String[] strings){
        Cloud.changePassword(strings, new Cloud.ResultListener() {
            @Override
            public void onResult(JSONObject result) {
                int returnCode;
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject = result;
                    returnCode = Integer.parseInt(jsonObject.get("code").toString());
                } catch (JSONException e) {
                    returnCode= Cloud.DefaultReturnCode.INTERNAL_SERVER_ERROR;
                    e.printStackTrace();
                }

                if (returnCode != Cloud.DefaultReturnCode.SUCCESS){
                    //FAIL
                    try {
                        String message = jsonObject.getString("message");
                        Toast.makeText(CreatePassword.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        new DefaultDialog.Builder(CreatePassword.this)
                                .imageResource(ContextCompat.getDrawable(CreatePassword.this, R.drawable.green_check), View.VISIBLE)
                                .message("Your password has been changed")
                                .positiveAction(getString(R.string.ok), new DefaultDialog.OnClickListener() {
                                    @Override
                                    public void onClick(Dialog dialog, String et) {
                                        Intent intent = new Intent(CreatePassword.this, Login.class);
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

    private void postNewPassword(String[] info){
        Cloud.newPassword(info, new Cloud.ResultListener() {
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
                        Toast.makeText(CreatePassword.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
                        new DefaultDialog.Builder(CreatePassword.this)
                                .imageResource(ContextCompat.getDrawable(CreatePassword.this, R.drawable.green_check), View.VISIBLE)
                                .message("Your password has been successfully reset")
                                .positiveAction("Ok", new DefaultDialog.OnClickListener() {
                                    @Override
                                    public void onClick(Dialog dialog, String et) {
                                        Intent intent = new Intent(CreatePassword.this, Login.class);
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

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(CreatePassword.this, android.R.style.Theme_Black);
        View view = LayoutInflater.from(CreatePassword.this).inflate(R.layout.progress_bar, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        return dialog;
    }


    private boolean allFieldsEmpty() {
        EditText[] fields = {mPassword, mRetypePassword};
        for(EditText f : fields) {
            if (f.getText().toString().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!isForgotPassword){
            super.onBackPressed();
        }

    }
}
