package com.example.fpgins.BottomNavigation.ClientDashboard.ClientMenus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fpgins.BottomNavigation.AgentDashboard.Filter.FilterFragment;
import com.example.fpgins.BottomNavigation.AgentDashboard.TravelAdapter;
import com.example.fpgins.DataModel.TravelData;
import com.example.fpgins.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class TravelActivity extends AppCompatActivity {

    private ImageView mBackButton, mFilterButton;
    private ArrayList<TravelData> mTravelList;
    private ArrayList<TravelData> mTravelListFiltered;
    private RecyclerView mRecyclerView;
    private TravelAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText editText;
    private String make = "";
    private String value = "";
    private String year = "";
    private String filteredContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
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
//                showFilter();
                LayoutInflater li = LayoutInflater.from(TravelActivity.this);
                View view = li.inflate(R.layout.fragment_filter, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TravelActivity.this);

                alertDialogBuilder.setView(view);

                final ChipGroup groupMake, groupValue, groupYear;

                groupMake = view.findViewById(R.id.filter_chip_group_make);
                groupValue = view.findViewById(R.id.filter_chip_group_value);
                groupYear = view.findViewById(R.id.filter_chip_group_year);

                groupValue.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(ChipGroup chipGroup, int i) {
                        Chip chip = chipGroup.findViewById(i);
                        if (chip != null){
                            Toast.makeText(TravelActivity.this, chip.getText().toString(),Toast.LENGTH_LONG).show();
                            value = chip.getText().toString().toLowerCase();
                        }
                    }
                });

                groupYear.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(ChipGroup chipGroup, int i) {
                        Chip chip = chipGroup.findViewById(i);
                        if (chip != null){
                            Toast.makeText(TravelActivity.this, chip.getText().toString(),Toast.LENGTH_LONG).show();
                            year = chip.getText().toString().toLowerCase();
                        }
                    }
                });

                final ArrayList<String> list = new ArrayList<>();
                for(int i = 0; i < groupMake.getChildCount(); i++){
                    Chip chip = (Chip) groupMake.getChildAt(i);
                    chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                list.add(buttonView.getText().toString());
                            }else{
                                list.remove(buttonView.getText().toString());
                            }
                            if(!list.isEmpty()){
                                Toast.makeText(TravelActivity.this, list.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                String output = list.toString().replaceAll("(^\\[|\\]$)", "");
                                filteredContent = value + "," + year + "," + list;
                                mAdapter.getFilter().filter(filteredContent);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

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
        ArrayList<TravelData> filteredList = new ArrayList<>();

        for (TravelData item : mTravelList) {
            if (item.getmTravelHolder().toLowerCase().contains(text.toLowerCase())
                    ||item.getmTravelType().toLowerCase().contains(text.toLowerCase())
                    ||item.getmTravelPlateNo().toLowerCase().contains(text.toLowerCase())
                    ||item.getmTravelChassisNo().toLowerCase().contains(text.toLowerCase())
                    ||item.getmTravelCarMake().toLowerCase().contains(text.toLowerCase())
                    ||item.getmTravelCarYear().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    private void createExampleList() {
        mTravelList = new ArrayList<>();
        mTravelList.add(new TravelData("Karleen Choie Galicia", "ST10850007000031", "AKA 1234", "MNCLSFE405W491231","Toyota","800,000", "2020"));
        mTravelList.add(new TravelData("Maximo Sevidal", "ST10940006000022", "DAG 5234", "FTNSWPS405W491232","Honda","1,000,000", "2019"));
        mTravelList.add(new TravelData("Jeffrey Dimla", "PR90850007900039", "DEB 6528", "PLOATZW405W491233","Audi","1,700,000","2020"));
        mTravelList.add(new TravelData("Hector Thomas Javier", "ST10130007000075", "ABY 8512", "CHQTOSX405W491234","Toyota","900,000","2018"));
        mTravelList.add(new TravelData("Matthew Dominic Estive", "DE60930217000090", "AST 8901", "MLASGTD405W491235","Ford","1,300,000","2020"));
        mTravelList.add(new TravelData("Ivan Eubans", "DE00930217008990", "NCE 3901", "CHASSIS405W491236","Nissan","1,100,000","2019"));
        mTravelList.add(new TravelData("Christian Grande", "PR70850007900659", "AKA 1023", "PLATQSX405W491237","Jaguar","2,000,000","2020"));
        mTravelList.add(new TravelData("Aldrene Victor Atienza", "PR30850007907030", "ACR 1099", "QWOSQAM405W491238","Ferrari","2,000,000","2018"));
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new TravelAdapter(mTravelList, getApplicationContext());
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