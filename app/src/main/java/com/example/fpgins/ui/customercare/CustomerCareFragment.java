package com.example.fpgins.ui.customercare;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fpgins.BottomSheetDialog.BottomSheetMaterialDialog;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomerCareFragment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button mSubmit;
    private BottomSheetMaterialDialog bottomSheetMaterialDialog;
    private ImageView mBackButton;
    private EditText mNumber, mMessage;
    private int mAccountId, mDepartmentId;
    private String mPolicyNum, mInquiry;
    private Spinner mSpinner;
    private List<String> list = new ArrayList<String>();

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

        mNumber = findViewById(R.id.edt_contactPolicy);
        mMessage = findViewById(R.id.edt_contactMessage);
        mSpinner = findViewById(R.id.spinner2);

        getAllDepartment();

        mSubmit = findViewById(R.id.btn_sumbit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInquiry(2714, 44, "12345L", "FROM ANDROID!");
//                Toast.makeText(getApplicationContext(), String.valueOf(mSpinner.getSelectedItem()), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendInquiry(int accountId, int department_id, final String policy_number, final String message){
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
                        Log.d("Server Error Message: ", message);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }else {
                    //SUCCESS
                    try {
                        Toast.makeText(getApplicationContext(), "SUCCESSSSSSS!", Toast.LENGTH_LONG).show();
//                        sendSMS(mobile, "Location : " + location + "\n"
//                                + "Longitude : " + longitude +"\n"
//                                + "Latitude : " + latitude +"\n");
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
    }

//    public void sendInquiry(final int accountId,final int departmentId,final String policyNo,final String message){
//        Cloud.manageInquiry(accountId, departmentId, policyNo, message, new Cloud.ResultListener() {
//            @Override
//            public void onResult(JSONObject result) {
//                int returnCode;
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject = result;
//                    returnCode = Integer.parseInt(jsonObject.get("code").toString());
//                }catch (JSONException e){
//                    returnCode = Cloud.DefaultReturnCode.INTERNAL_SERVER_ERROR;
//                    e.printStackTrace();
//                }
//
//                if (returnCode != Cloud.DefaultReturnCode.SUCCESS){
//                    //FAIL
//                    try {
//                        String message = jsonObject.getString("message");
//                        Log.d("Server Error Message: ", message);
//                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                    }catch (JSONException e){
//                        e.printStackTrace();
//                    }
//                }else {
//                    //SUCCESS
//                    try {
//                        Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_LONG).show();
//                    } catch (Exception e) {
//                        e.getMessage();
//                    }
//                }
//            }
//        });
//    }

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
                Log.i("RESULT", id + name + department_category_id + department_category_name);
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spinner_layout, list);
            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
            mSpinner.setAdapter(dataAdapter);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}