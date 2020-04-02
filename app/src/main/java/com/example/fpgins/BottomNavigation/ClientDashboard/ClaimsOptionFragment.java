package com.example.fpgins.BottomNavigation.ClientDashboard;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.fpgins.BottomNavigation.Claims.DraftsActivity;
import com.example.fpgins.BottomNavigation.Claims.SubmittedFormsActivity;
import com.example.fpgins.R;


public class ClaimsOptionFragment extends DialogFragment {

    private LinearLayout mFileClaim, mReviewClaim;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_claims_option, container, true);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
//        setCancelable(false);

        mFileClaim = view.findViewById(R.id.linear_fileAClaim);
        mReviewClaim = view.findViewById(R.id.linear_reviewClaim);

        mFileClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DraftsActivity.class);
                startActivity(intent);
                dismiss();
            }
        });

        mReviewClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubmittedFormsActivity.class);
                startActivity(intent);
                dismiss();
            }
        });


        return view;
    }
}
