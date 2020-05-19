package com.example.fpgins.BottomNavigation.Settings;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpgins.DataModel.VehicleDetailsData;
import com.example.fpgins.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleFragment extends Fragment {
    private Spinner mSpinner;
    private List<String> list = new ArrayList<String>();
    private ArrayList<VehicleDetailsData> vehicleDetailsData;
    private TextView mMake,mPlateNum,mConductionSticker, mYear, mModel,
            mColor, mVariance, mTypeBody, mMVFileNo, mEngineNo, mChassisNo, mNoPassenger, mAccessories;

    private ClientPolicyActivity clientPolicyActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_vehicle, container, false);

        mMake = root.findViewById(R.id.txt_detail_make);
        mModel = root.findViewById(R.id.txt_detail_model);
        mVariance = root.findViewById(R.id.txt_detail_variance);
        mTypeBody = root.findViewById(R.id.txt_detail_typeBody);
        mYear = root.findViewById(R.id.txt_detail_year);
        mPlateNum = root.findViewById(R.id.txt_detail_plateNo);
        mConductionSticker = root.findViewById(R.id.txt_detail_conduction);
        mMVFileNo = root.findViewById(R.id.txt_detail_mvFileNo);
        mEngineNo = root.findViewById(R.id.txt_detail_engineNo);
        mChassisNo = root.findViewById(R.id.txt_detail_chassisNo);
        mColor = root.findViewById(R.id.txt_detail_color);
        mNoPassenger = root.findViewById(R.id.txt_detail_passenger);
        mAccessories = root.findViewById(R.id.txt_detail_accessories);
        mSpinner = root.findViewById(R.id.spinnerCarList);

        createExampleList();
        vehicleList();

        test();

        return root;
    }

    private void createExampleList() {
        list = new ArrayList<>();
        list.add("AKA 1234");
        list.add("CRM 4321");
        list.add("DAF 6128");
        list.add("DAL 3324");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout, list){
        };

        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        mSpinner.setAdapter(dataAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VehicleDetailsData vehicleDetailsDataList = vehicleDetailsData.get(position);
                mMake.setText(vehicleDetailsDataList.getmMake());
                mPlateNum.setText(vehicleDetailsDataList.getmPlateNumber());
                mConductionSticker.setText(vehicleDetailsDataList.getmConductionSticker());
                mYear.setText(vehicleDetailsDataList.getmYearManufactured());
                mModel.setText(vehicleDetailsDataList.getmModel());
                mColor.setText(vehicleDetailsDataList.getmColor());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void vehicleList(){
        vehicleDetailsData = new ArrayList<>();
        vehicleDetailsData.add(new VehicleDetailsData("ISUZU", "Aluminum Van","", "", "", "a","","","","ML 0209","2017", "Camry", "Brownstone"));
        vehicleDetailsData.add(new VehicleDetailsData("NISSAN", "Aluminum Van","", "", "", "b","","","","ML 0209","2017", "Camry", "Brownstone"));
        vehicleDetailsData.add(new VehicleDetailsData("YAMAHA", "Aluminum Van","", "", "", "c","","","","ML 0209","2017", "Camry", "Brownstone"));
        vehicleDetailsData.add(new VehicleDetailsData("MITSUBISHI", "Aluminum Van","", "", "", "d","","","","ML 0209","2017", "Camry", "Brownstone"));
    }

    public void test() {
        clientPolicyActivity = new ClientPolicyActivity();
        List<VehicleDetailsData> list = clientPolicyActivity.getList();
        String size = String.valueOf(list.size());
        Log.i("SIZZZEEEE", size);
    }
}
