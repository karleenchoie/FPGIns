package com.example.fpgins.ui.customercare;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.fpgins.BottomSheetDialog.BottomSheetMaterialDialog;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;
import com.example.fpgins.Utility.DefaultDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomerCareFragment extends AppCompatActivity{

    private Button mSubmit;
    private BottomSheetMaterialDialog bottomSheetMaterialDialog;
    private ImageView mBackButton;
    private EditText mNumber, mMessage;
    private int mAccountId, mDepartmentId;
    private String mPolicyNum, mInquiry;
    private Spinner mSpinner;
    private UserData mUserData;
    private List<String> list = new ArrayList<String>();
    private int id;
    private int departmentId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tools);

        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mUserData = new UserData(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        id = Integer.parseInt(mUserData.getId());

        mNumber = findViewById(R.id.edt_contactPolicy);
        mNumber.addTextChangedListener(getTextWatcher(mNumber));
        mMessage = findViewById(R.id.edt_contactMessage);
        mMessage.addTextChangedListener(getTextWatcher(mMessage));
        mSpinner = findViewById(R.id.spinner2);

        getAllDepartment();

        mSubmit = findViewById(R.id.btn_sumbit);
        mSubmit.setEnabled(false);
        mSubmit.setAlpha((float) 0.5);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String policyNumber = mNumber.getText().toString().trim();
                String message = mMessage.getText().toString().trim();
                sendInquiry(id, departmentId, policyNumber, message);
            }
        });
    }

    public void sendInquiry(int accountId, int department_id,String policy_number,String message){
        Cloud.manageInquiry(accountId, department_id, policy_number, message, new Cloud.ResultListener() {
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
                        new DefaultDialog.Builder(CustomerCareFragment.this)
                                .message("Unable to send inquiry")
                                .detail("Please select department or Check you internet connection and try it again")
                                .positiveAction("Ok", new DefaultDialog.OnClickListener() {
                                    @Override
                                    public void onClick(Dialog dialog, String et) {
                                        dialog.dismiss();
                                    }
                                })
                                .build()
                                .show();
                        Log.i("CONTACT US FAILED", message);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        new DefaultDialog.Builder(CustomerCareFragment.this)
                                .message("Your inquiry has been successfully sent.")
                                .detail("FPG representative will answer it as soon as possible. Thank you.")
                                .positiveAction("Ok", new DefaultDialog.OnClickListener() {
                                    @Override
                                    public void onClick(Dialog dialog, String et) {
                                        dialog.dismiss();
                                    }
                                })
                                .build()
                                .show();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
    }

    private void getAllDepartment() {
        Cloud.getAllDepartment(new Cloud.ResultListener() {
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
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
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
        try {
            list.add("Select Department");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String department_category_id = jsonObject.getString("department_category_id");
                String department_category_name = jsonObject.getString("department_category_name");
                list.add(name);
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spinner_layout, list);
            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
            mSpinner.setAdapter(dataAdapter);
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String department = parent.getSelectedItem().toString();
                    if (department.equals("Engineering")){
                        departmentId = 46;
                    }
                    if (department.equals("Marine")){
                        departmentId = 43;

                    }if (department.equals("Travel")){
                        departmentId = 45;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private TextWatcher getTextWatcher(final EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSubmit.setEnabled(false);
                mSubmit.setAlpha((float) 0.5);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do what you want with your EditText
                if (mNumber.getText().length() != 0 && mMessage.getText().length() != 0){
                    mSubmit.setEnabled(true);
                    mSubmit.setAlpha(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (mNumber.getText().length() == 0 || mMessage.getText().length() == 0){
                    mSubmit.setEnabled(false);
                    mSubmit.setAlpha((float) 0.5);
                }

            }
        };
    }
}