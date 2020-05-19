package com.example.fpgins.BottomNavigation.AgentDashboard.AgentFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fpgins.DataModel.ClientNameData;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientListFragment extends Fragment {

    private ImageView mBackButton;
    private ArrayList<ClientNameData> mClientNameData = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ClientNamesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_list, container, false);

//        createExampleList();


        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new ClientNamesAdapter(mClientNameData,getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        getClientList("D01CT00001");

        EditText editText = view.findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString().trim());
            }
        });



        return view;
    }

    private void filter(String text) {
        ArrayList<ClientNameData> filteredList = new ArrayList<>();

        for (ClientNameData item : mClientNameData) {
            if (item.getId().toLowerCase().contains(text.toLowerCase())
                    ||item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    private void getClientList(String agentCode){
        Cloud.getAllClients(agentCode, new Cloud.ResultListener() {
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
//                        mDialog.dismiss();
                        String message = jsonObject.getString("message");
                        Log.d("Server Error Message: ", message);
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }else {
                    //SUCCESS
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("record");
                        generateResult(jsonArray);
                        mRecyclerView.setAdapter(mAdapter);

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
                String id = jsonObject.getString("assured_id");
                String name = jsonObject.getString("assured_name");

                ClientNameData clientNameData = new ClientNameData(id,name);
                mClientNameData.add(clientNameData);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}