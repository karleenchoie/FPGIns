package com.example.fpgins.BottomNavigation.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Login.SnackBarNotificationUtil;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;
import com.example.fpgins.Utility.DefaultDialog;
import com.example.fpgins.ui.NotificationMessage.NotifMessageFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonalInformation extends AppCompatActivity {

    private ImageView mBack;
    private TextView mEdit;
    private EditText mEmail, mFirstName, mLastName, mMobile;
    private boolean isEdit = true;
    private UserData mUserData;
    private Dialog mDialog;
    private String email, username, mobile, lastname,fistname;
    private boolean isEditted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        mBack = findViewById(R.id.img_backbutton);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mEdit = findViewById(R.id.txt_edit);
        mEmail = findViewById(R.id.edt_email);
        mFirstName = findViewById(R.id.edt_firstName);
        mLastName = findViewById(R.id.edt_lastName);
        mMobile = findViewById(R.id.edt_mobile);
        mDialog = createLoadingDialog();

        mUserData = new UserData(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        mEmail.addTextChangedListener(getTextWatcher(mEmail, mUserData.getEmail()));
        mFirstName.addTextChangedListener(getTextWatcher(mFirstName, mUserData.getFirstName()));
        mLastName.addTextChangedListener(getTextWatcher(mLastName, mUserData.getLastName()));

        setInfo();

        mEmail.setSelection(mEmail.length());
        mEmail.requestFocus();

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEdit = !isEdit;
                if (isEdit == true){
                    mEmail.setEnabled(true);
                    mEmail.setSelection(mEmail.length());
                    mEmail.requestFocus();
                    mFirstName.setEnabled(true);
                    mLastName.setEnabled(true);
                    mMobile.setEnabled(true);
                    mEdit.setText("DONE");

                }else{
                    mDialog.show();
                    email = mEmail.getText().toString();
                    username = mUserData.getUsername();
                    mobile = mMobile.getText().toString();
                    lastname = mLastName.getText().toString();
                    fistname = mFirstName.getText().toString();
                    mEmail.setEnabled(false);
                    mFirstName.setEnabled(false);
                    mLastName.setEnabled(false);
                    mMobile.setEnabled(false);
                    mEdit.setText("EDIT");

                    String[] information = {email,username,mobile, lastname, fistname};
                    updateInfo(information);
                }
            }
        });
    }

    private TextWatcher getTextWatcher(final EditText editText, final String fetched) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText.getText().toString().trim().equals(fetched)){
                    isEditted = false;
                }else {
                    isEditted = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public void setInfo(){
        mEmail.setText(mUserData.getEmail());
        mFirstName.setText(mUserData.getFirstName());
        mLastName.setText(mUserData.getLastName());
    }

    public void setOnUserData(){
        mUserData.setEmail(email);
        mUserData.setFirstName(fistname);
        mUserData.setLastName(lastname);
        try {
            new DefaultDialog.Builder(PersonalInformation.this)
                    .imageResource(ContextCompat.getDrawable(PersonalInformation.this, R.drawable.green_check), View.VISIBLE)
                    .message("Your personal information has been updated")
                    .positiveAction("OK", new DefaultDialog.OnClickListener() {
                        @Override
                        public void onClick(Dialog dialog, String et) {
                            dialog.dismiss();
                            isEditted = false;
                        }
                    })
                    .build()
                    .show();
        }catch (Exception e){
            e.getMessage();
        }
    }

    public void updateInfo(final String[] data){
        Cloud.updatePersonalInfo(data, new Cloud.ResultListener() {
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
                    mDialog.show();
                    try {
                        String message = jsonObject.getString("message");
                        Toast.makeText(PersonalInformation.this, message, Toast.LENGTH_SHORT).show();
                    }catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(PersonalInformation.this, "Please check your connection and try again", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //SUCCESS
                    if (isEditted){
                        mDialog.show();
                        setOnUserData();
                    }
                }
                mDialog.dismiss();
            }
        });
    }

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(PersonalInformation.this, android.R.style.Theme_Black);
        View view = LayoutInflater.from(PersonalInformation.this).inflate(R.layout.progress_bar, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        return dialog;
    }
}
