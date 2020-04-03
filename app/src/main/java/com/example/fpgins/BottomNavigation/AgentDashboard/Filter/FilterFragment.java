package com.example.fpgins.BottomNavigation.AgentDashboard.Filter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.fpgins.BottomNavigation.AgentDashboard.MotorActivity;
import com.example.fpgins.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class FilterFragment extends DialogFragment {

    private ChipGroup mGroupMake, mGroupValue, mGroupYear;
    private Button mDone;
    private String value = "";
    private String make = "";
    private String year = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_filter, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        setCancelable(false);

        mDone = view.findViewById(R.id.btn_done);
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MotorActivity.class);
                intent.putExtra("selectedmake", make);
                intent.putExtra("selectedValue", value);
                intent.putExtra("selectedYear", year);
                dismiss();
            }
        });

        mGroupMake = view.findViewById(R.id.filter_chip_group_make);
        mGroupValue = view.findViewById(R.id.filter_chip_group_value);
        mGroupYear = view.findViewById(R.id.filter_chip_group_year);

        mGroupValue.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                Chip chip = chipGroup.findViewById(i);
                if (chip != null){
                    Toast.makeText(getActivity(), chip.getText().toString(),Toast.LENGTH_LONG).show();
                    value = chip.getText().toString().toLowerCase();
                }
            }
        });

        mGroupYear.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                Chip chip = chipGroup.findViewById(i);
                if (chip != null){
                    Toast.makeText(getActivity(), chip.getText().toString(),Toast.LENGTH_LONG).show();
                    year = chip.getText().toString().toLowerCase();
                }
            }
        });


        mGroupMake.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                Chip chip = chipGroup.findViewById(i);
                if (chip != null){
                    Toast.makeText(getActivity(), chip.getText().toString(),Toast.LENGTH_LONG).show();
                    make = chip.getText().toString().toLowerCase();
                }
            }
        });

        return view;
    }
}
