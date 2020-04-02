package com.example.fpgins.BottomNavigation.AgentDashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.fpgins.DataModel.MotorData;
import com.example.fpgins.BottomNavigation.AgentDashboard.Filter.FilterFragment;
import com.example.fpgins.R;

import java.util.ArrayList;

public class MotorActivity extends AppCompatActivity {

    private ImageView mBackButton, mFilterButton;
    private ArrayList<MotorData> mMotorList;
    private ArrayList<MotorData> mMotorListFiltered;
    private RecyclerView mRecyclerView;
    private MotorAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText editText;
    private String make = "";
    private String value = "";
    private String year = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor);

        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mFilterButton = findViewById(R.id.img_filter);
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilter();
            }
        });

        createExampleList();
        buildRecyclerView();


        editText = findViewById(R.id.edittext);
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
    }

    private void filter(String text) {
        ArrayList<MotorData> filteredList = new ArrayList<>();

        for (MotorData item : mMotorList) {
            if (item.getmPolicyHolder().toLowerCase().contains(text.toLowerCase())
                    ||item.getmPolicyType().toLowerCase().contains(text.toLowerCase())
                    ||item.getmPolicyPlateNo().toLowerCase().contains(text.toLowerCase())
                    ||item.getmPolicyChassisNo().toLowerCase().contains(text.toLowerCase())
                    ||item.getmPolicyCarMake().toLowerCase().contains(text.toLowerCase())
                    ||item.getmPolicyCarYear().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    private void createExampleList() {
        mMotorList = new ArrayList<>();
        mMotorList.add(new MotorData("Standard", "ST10850007000031", "AKA 1234", "MNCLSFE405W491231","Toyota","800,000", "2020"));
        mMotorList.add(new MotorData("Standard", "ST10940006000022", "DAG 5234", "FTNSWPS405W491232","Honda","1,000,000", "2019"));
        mMotorList.add(new MotorData("Premium", "PR90850007900039", "DEB 6528", "PLOATZW405W491233","Audi","1,700,000","2020"));
        mMotorList.add(new MotorData("Standard", "ST10130007000075", "ABY 8512", "CHQTOSX405W491234","Toyota","900,000","2018"));
        mMotorList.add(new MotorData("Premium", "DE60930217000090", "AST 8901", "MLASGTD405W491235","Ford","1,300,000","2020"));
        mMotorList.add(new MotorData("Standard", "DE00930217008990", "NCE 3901", "CHASSIS405W491236","Nissan","1,100,000","2019"));
        mMotorList.add(new MotorData("Premium", "PR70850007900659", "AKA 1023", "PLATQSX405W491237","Jaguar","2,000,000","2020"));
        mMotorList.add(new MotorData("Premium", "PR30850007907030", "ACR 1099", "QWOSQAM405W491238","Ferrari","2,000,000","2018"));
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

//        mMotorListFiltered = new ArrayList<>();
//
//        for (MotorData motorData : mMotorList){
//            if (motorData.getmPolicyCarMake().toLowerCase().contains(make)
//                    && motorData.getmPolicyCarValue().toLowerCase().contains(value)
//                    && motorData.getmPolicyCarValue().toLowerCase().contains(year)){
//                mMotorListFiltered.add(motorData);
//            }
//        }

        mAdapter = new MotorAdapter(mMotorList, getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showFilter(){
        Bundle args = new Bundle();
        FilterFragment filterDialogFragment = new FilterFragment();
        filterDialogFragment.setArguments(args);
        filterDialogFragment.show(getSupportFragmentManager(), null);
    }

}