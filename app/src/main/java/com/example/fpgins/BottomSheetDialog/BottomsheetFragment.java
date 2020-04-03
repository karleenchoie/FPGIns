package com.example.fpgins.BottomSheetDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.fpgins.Login.AgentRegistration;
import com.example.fpgins.Login.Registration;
import com.example.fpgins.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomsheetFragment extends BottomSheetDialogFragment {

    private LinearLayout mAgent, mClient;

    public BottomsheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.register_bottomsheet, container, false);

        mAgent = view.findViewById(R.id.linear_regAgent);
        mClient = view.findViewById(R.id.linear_regClient);

        mAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AgentRegistration.class);
                startActivity(intent);
                dismiss();
            }
        });

        mClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Registration.class);
                startActivity(intent);
                dismiss();
            }
        });

        return view;
    }
}