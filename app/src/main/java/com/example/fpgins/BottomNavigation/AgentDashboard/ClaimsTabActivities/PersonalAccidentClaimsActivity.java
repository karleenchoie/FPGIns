package com.example.fpgins.BottomNavigation.AgentDashboard.ClaimsTabActivities;

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

import com.example.fpgins.DataModel.PersonalAccidentData;
import com.example.fpgins.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class PersonalAccidentClaimsActivity extends AppCompatActivity {

    private ImageView mBackButton, mFilterButton;
    private ArrayList<PersonalAccidentData> mPAList;
    private ArrayList<PersonalAccidentData> mPAListFiltered;
    private RecyclerView mRecyclerView;
    private PersonalAccidentClaimsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText editText;
    private String make = "";
    private String value = "";
    private String year = "";
    private String filteredContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_accident_claims);

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
                LayoutInflater li = LayoutInflater.from(PersonalAccidentClaimsActivity.this);
                View view = li.inflate(R.layout.fragment_filter, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PersonalAccidentClaimsActivity.this);

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
                            Toast.makeText(PersonalAccidentClaimsActivity.this, chip.getText().toString(),Toast.LENGTH_LONG).show();
                            value = chip.getText().toString().toLowerCase();
                        }
                    }
                });

                groupYear.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(ChipGroup chipGroup, int i) {
                        Chip chip = chipGroup.findViewById(i);
                        if (chip != null){
                            Toast.makeText(PersonalAccidentClaimsActivity.this, chip.getText().toString(),Toast.LENGTH_LONG).show();
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
                                Toast.makeText(PersonalAccidentClaimsActivity.this, list.toString(),Toast.LENGTH_LONG).show();
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

//        final ChipGroup entryChipGroup = findViewById(R.id.entry_chip_group);
//        final Chip entryChip = getChip(entryChipGroup, "Hello World");
//        final Chip entryChip2 = getChip(entryChipGroup, "Test");
//        entryChipGroup.addView(entryChip);
//        entryChipGroup.addView(entryChip2);

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
        ArrayList<PersonalAccidentData> filteredList = new ArrayList<>();

        for (PersonalAccidentData item : mPAList) {
            if (item.getmPAHolder().toLowerCase().contains(text.toLowerCase())
                    ||item.getmPAType().toLowerCase().contains(text.toLowerCase())
                    ||item.getmPAPlateNo().toLowerCase().contains(text.toLowerCase())
                    ||item.getmPAChassisNo().toLowerCase().contains(text.toLowerCase())
                    ||item.getmPACarMake().toLowerCase().contains(text.toLowerCase())
                    ||item.getmPACarYear().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    private void createExampleList() {
        mPAList = new ArrayList<>();
        mPAList.add(new PersonalAccidentData("Karleen Choie Galicia", "ST10850007000031", "AKA 1234", "MNCLSFE405W491231","Toyota","800,000", "2020"));
        mPAList.add(new PersonalAccidentData("Maximo Sevidal", "ST10940006000022", "DAG 5234", "FTNSWPS405W491232","Honda","1,000,000", "2019"));
        mPAList.add(new PersonalAccidentData("Jeffrey Dimla", "PR90850007900039", "DEB 6528", "PLOATZW405W491233","Audi","1,700,000","2020"));
        mPAList.add(new PersonalAccidentData("Hector Thomas Javier", "ST10130007000075", "ABY 8512", "CHQTOSX405W491234","Toyota","900,000","2018"));
        mPAList.add(new PersonalAccidentData("Matthew Dominic Estive", "DE60930217000090", "AST 8901", "MLASGTD405W491235","Ford","1,300,000","2020"));
        mPAList.add(new PersonalAccidentData("Ivan Eubans", "DE00930217008990", "NCE 3901", "CHASSIS405W491236","Nissan","1,100,000","2019"));
        mPAList.add(new PersonalAccidentData("Christian Grande", "PR70850007900659", "AKA 1023", "PLATQSX405W491237","Jaguar","2,000,000","2020"));
        mPAList.add(new PersonalAccidentData("Aldrene Victor Atienza", "PR30850007907030", "ACR 1099", "QWOSQAM405W491238","Ferrari","2,000,000","2018"));
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new PersonalAccidentClaimsAdapter(mPAList, getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

//    public void showFilter(){
//        Bundle args = new Bundle();
//        FilterFragment filterDialogFragment = new FilterFragment();
//        filterDialogFragment.setArguments(args);
//        filterDialogFragment.show(getSupportFragmentManager(), null);
//    }

//    private Chip getChip(final ChipGroup entryChipGroup, String text) {
//        final Chip chip = new Chip(this);
//        chip.setChipDrawable(ChipDrawable.createFromResource(this, R.xml.chip));
//        int paddingDp = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, 10,
//                getResources().getDisplayMetrics()
//        );
//        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
//        chip.setText(text);
//        chip.setOnCloseIconClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                entryChipGroup.removeView(chip);
//            }
//        });
//        return chip;
//    }

}