package com.example.fpgins.BottomNavigation.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpgins.DataModel.ClientPoliciesData;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.DataModel.VehicleDetailsData;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ClientPolicyActivity extends AppCompatActivity {

    private ImageView mBackButton;
    private ArrayList<ClientPoliciesData> mPolicyList =  new ArrayList<>();
    private ArrayList<VehicleDetailsData> mVehicleList =  new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ClientPolicyAdapter mAdapter;
    private TextView mAgentFullName, mAgentEmail, mAgentContactNo;

    private Dialog mDialog;

    private UserData mUserData;
    private String accountCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_policy);

        mDialog = createLoadingDialog();
        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        createExampleList();

        mAgentFullName = findViewById(R.id.txt_agentFullName);
        mAgentEmail = findViewById(R.id.txt_agentEmail);
        mAgentContactNo = findViewById(R.id.txt_agentNumber);

        mRecyclerView = findViewById(R.id.rv_clientPolicyList);
        mAdapter = new ClientPolicyAdapter(mPolicyList, ClientPolicyActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(mAdapter);

        mUserData = new UserData(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        accountCode = mUserData.getAccountCode();

        mDialog.show();
        if (accountCode.equals("AGT")){
            getAgentPolicyList("D01CT00001");
        } else {
            getClientPolicyList("D03MF00001");
        }

        getAgentDetails("M02DI00001");

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
                        generateAgentResult(jsonArray);
                        mRecyclerView.setAdapter(mAdapter);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
    }

    private void generateAgentResult(JSONArray jsonArray){
        try {
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("Name");
//                String content = Html.fromHtml(jsonObject.getString("content")).toString();
                String email = jsonObject.getString("Email");
                String number = jsonObject.getString("Phone_1");

                mAgentFullName.setText(name);
                mAgentEmail.setText(email);
                mAgentContactNo.setText(number);

            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getClientPolicyList(String clientCode){
        Cloud.clientPolicy(clientCode, new Cloud.ResultListener() {
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
                        mDialog.dismiss();
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
//                        generateEacItem(jsonArray);
                        generatePolicyListResult(jsonArray);

                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
    }

    private void getAgentPolicyList(String agentCode){
        Cloud.agentPolicy(agentCode, new Cloud.ResultListener() {
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
                        mDialog.dismiss();
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
                        generatePolicyListResult(jsonArray);

                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
    }

    private void generatePolicyListResult(JSONArray jsonArray){
        try {
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray policy = jsonObject.getJSONArray("policy");
                JSONArray vehicle = jsonObject.getJSONArray("vehicle");

                for (int p = 0; p < policy.length(); p++){
                    JSONObject ho = policy.getJSONObject(p);
                    String policyNo = ho.getString("policy_no");
                    String certificate_no = ho.getString("certificate_no");
                    String inception_date = ho.getString("inception_date");
                    String expiry_date = ho.getString("expiry_date");
                    String assured_id = ho.getString("assured_id");
                    String assured_name = ho.getString("assured_name");
                    String intermediary_id = ho.getString("intermediary_id");

                    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //yyyy-MM-dd HH:mm:ss
                    Date dateInception = input.parse(inception_date);
                    Date dateExpiry = input.parse(expiry_date);

                    SimpleDateFormat output = new SimpleDateFormat("MMMM dd, yyyy");
                    String newdateInception = output.format(dateInception);
                    String newdateExpiry = output.format(dateExpiry);

                    for (int y = 0; y < vehicle.length(); y++){
                        JSONObject ve = vehicle.getJSONObject(y);
                        String make = ve.getString("make");
                        String model = ve.getString("model");
                        String variance = ve.getString("variance");
                        String type_body = ve.getString("type_body");
                        String year_manufactured = ve.getString("year_manufactured");
                        String plate_no = ve.getString("plate_no");
                        String conduction_sticker = ve.getString("conduction_sticker");
                        String mv_file_no = ve.getString("mv_file_no");
                        String engine_no = ve.getString("engine_no");
                        String chassis_no = ve.getString("chassis_no");
                        String color = ve.getString("color");
                        String no_passenger = ve.getString("no_passenger");
                        String accessories = ve.getString("accessories");

                        VehicleDetailsData vehicleDetailsData = new VehicleDetailsData(make, model, variance, type_body,
                                year_manufactured, plate_no, conduction_sticker, mv_file_no, engine_no, chassis_no, color, no_passenger, accessories);
                        mVehicleList.add(vehicleDetailsData);
                    }

                    ClientPoliciesData clientPoliciesData = new ClientPoliciesData(policyNo, certificate_no,newdateInception, newdateExpiry,
                            assured_id, assured_name, intermediary_id, mVehicleList);

                    mPolicyList.add(clientPoliciesData);

                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        mDialog.dismiss();
    }

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(ClientPolicyActivity.this, android.R.style.Theme_Black);
        View view = LayoutInflater.from(ClientPolicyActivity.this).inflate(R.layout.progress_bar, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        return dialog;
    }
}
