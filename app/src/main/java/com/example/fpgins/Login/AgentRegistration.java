package com.example.fpgins.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;
import com.example.fpgins.Utility.DefaultDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AgentRegistration extends AppCompatActivity {

    private EditText mAgentCode;
//    private EditText mEmail;
    private EditText mPassword;
    private EditText mRetypePassword;
//    private EditText mMobileNo;

    private ImageView mCheckUpper;
    private ImageView mCheckLower;
    private ImageView mCheckNumber;
    private ImageView mCheckChar;
    private ImageView mBackButton;

    private Spinner mSpinner;

    private TextView mErrorMessage;

    private Button mRegister;

    private Dialog mDialog;

    private static final String[] PATHS = {"--Select Type--", "Client", "Agent"};
    private String mAccountCode;

    private boolean isPressed = true;

    private String email;

    private String mEmail;
    private String mGeneratedCode;
    private String mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_registration);

//        mMobileNo = (EditText) findViewById(R.id.etMobileNo);

        mAgentCode = (EditText) findViewById(R.id.etAgentCode);
        mAgentCode.addTextChangedListener(getTextWatcher(mAgentCode));

//        mEmail = (EditText) findViewById(R.id.etEmail);
//        mEmail.addTextChangedListener(getTextWatcher(mEmail));

        mPassword = (EditText) findViewById(R.id.etPw);
        mPassword.addTextChangedListener(getTextWatcher(mPassword));

        mRetypePassword = (EditText) findViewById(R.id.etRetypePw);
        mRetypePassword.addTextChangedListener(getTextWatcher(mRetypePassword));

//        mCheckBoxShowPw = (CheckBox) findViewById(R.id.checkBox);

        //checkmarks
        mCheckUpper = (ImageView) findViewById(R.id.checkmark1);
        mCheckLower = (ImageView) findViewById(R.id.checkmark2);
        mCheckNumber = (ImageView) findViewById(R.id.checkmark3);
        mCheckChar = (ImageView) findViewById(R.id.checkmark4);

        mRegister = (Button) findViewById(R.id.btnRegister);
        mRegister.setEnabled(false);
        mRegister.setAlpha((float) 0.5);

//        mSpinner = (Spinner) findViewById(R.id.spinner);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Registration.this, android.R.layout.simple_spinner_dropdown_item, PATHS);
//        mSpinner.setAdapter(adapter);
//        mSpinner.setOnItemSelectedListener(this);

//        mErrorMessage = (TextView) findViewById(R.id.tvErrorMessage);

        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mDialog = createLoadingDialog();

        mPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= (mPassword.getRight() - mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        if (isPressed){
                            Drawable icon = ContextCompat.getDrawable(AgentRegistration.this, R.drawable.ic_action_eye);
                            icon.setBounds(0, mPassword.getHeight(), 0, mPassword.getHeight());
                            mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
                            mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }else {
                            Drawable icon = ContextCompat.getDrawable(AgentRegistration.this, R.drawable.ic_action_slashedeye);
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

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = mPassword.getText().toString();

                if (text.length() != 0){
                    mPassword.setBackground(ContextCompat.getDrawable(AgentRegistration.this, R.drawable.button_rectangle));
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

        mRetypePassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= (mRetypePassword.getRight() - mRetypePassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        if (isPressed){
                            Drawable icon = ContextCompat.getDrawable(AgentRegistration.this, R.drawable.ic_action_eye);
                            icon.setBounds(0, mRetypePassword.getHeight(), 0, mRetypePassword.getHeight());
                            mRetypePassword.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
                            mRetypePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }else {
                            Drawable icon = ContextCompat.getDrawable(AgentRegistration.this, R.drawable.ic_action_slashedeye);
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

        mRetypePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = mRetypePassword.getText().toString();

                if (text.length() != 0){
                    mRetypePassword.setBackground(ContextCompat.getDrawable(AgentRegistration.this, R.drawable.button_rectangle));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String mobileNo = "+639998877665";
                String email = mAgentCode.getText().toString().trim();
                String password = mPassword.getText().toString();
//                String firstName = "AGENT";
//                String lastName = "SAMPLE";
                if (!validateInfo()){
                    mDialog.dismiss();
                    return;
                } else {
                    //postAPI
                    getAgentDetails(email);
                    mDialog.dismiss();
                }
            }
        });

    }

    private void postRegister(final String[] information){
        Cloud.registerAccount(information, new Cloud.ResultListener() {
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
                        Toast.makeText(AgentRegistration.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } else {
                    //SUCCESS
                    try {
//                        String email = mEmail.getText().toString();
                        String message = jsonObject.getString("message");
                        String generatedCode = jsonObject.getString("generate_code");
                        String auth = jsonObject.getString("authentication");

                        Toast.makeText(getApplicationContext(), auth, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(AgentRegistration.this, VerificationCodeActivity.class);

                        intent.putExtra("email", email);
                        intent.putExtra("generatedCode", generatedCode);
                        intent.putExtra("auth", auth);
                        intent.putExtra("isForgotPassword", false);

                        startActivity(intent);

                    } catch (Exception e){
                        e.getMessage();
                    }
                }

                mDialog.dismiss();
            }
        });
    }

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(AgentRegistration.this, android.R.style.Theme_Black);
        View view = LayoutInflater.from(AgentRegistration.this).inflate(R.layout.progress_bar, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        return dialog;
    }

    private boolean validateInfo(){
//        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (allFieldsEmpty()){
            return false;
        }

//        if (!isEmailValid(email)){
//            displayError(mEmail, getResources().getString(R.string.email_error));
//            return false;
//        } else {
//            mEmail.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
//        }

        if (!isPasswordLongEnough()){
            displayError(mPassword, getResources().getString(R.string.password_length));
            return false;
        } else {
            mPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
        }

        if (!password.matches(".*[A-Z].*")){
            displayError(mPassword, getResources().getString(R.string.password_capital_letter));
            return false;
        } else {
            mPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
        }

        if (!password.matches(".*[a-z].*")){
            displayError(mPassword, getResources().getString(R.string.password_small_letter));
            return false;
        } else {
            mPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
        }

        if (!password.matches(".*\\d.*")){
            displayError(mPassword, getResources().getString(R.string.password_number));
            return false;
        }

        if (!isPasswordSame()){
            displayError(mRetypePassword, getResources().getString(R.string.password_unmatched));
            return false;
        } else {
            mRetypePassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
        }

//        mEmail.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
        mPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));
        mRetypePassword.setBackground(ContextCompat.getDrawable(this, R.drawable.button_rectangle));

        return true;
    }

    private void displayError (EditText edittext, String error){
        edittext.setBackground(ContextCompat.getDrawable(this, R.drawable.rectangle_red));
        edittext.setError(error);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
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
            Toast.makeText(AgentRegistration.this, R.string.password_length, Toast.LENGTH_LONG);
            return false;
        }
    }

    private boolean allFieldsEmpty() {
        EditText[] fields = { mAgentCode, mPassword, mRetypePassword };
        for(EditText f : fields) {
            if (f.getText().toString().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private TextWatcher getTextWatcher(final EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mRegister.setEnabled(false);
                mRegister.setAlpha((float) 0.5);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do what you want with your EditText
                if (!allFieldsEmpty()){
                    mRegister.setEnabled(true);
                    mRegister.setAlpha(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (allFieldsEmpty()){
                    mRegister.setEnabled(false);
                    mRegister.setAlpha((float) 0.5);
                }

            }
        };
    }

    private void getAgentDetails(String code){
        Cloud.agentDetails(code, new Cloud.ResultListener() {
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
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }else {
                    //SUCCESS
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        generateResult(jsonArray);
//                        mRecyclerView.setAdapter(mAdapter);
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
                String firstName = jsonObject.getString("FirstName");
                String lastName = jsonObject.getString("LastName");
                email = jsonObject.getString("Email");
                String number = jsonObject.getString("Mobile_1");
                String password = mPassword.getText().toString().trim();

                String[] info = {"AGT", email, password, firstName, lastName, number};
                postRegister(info);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
