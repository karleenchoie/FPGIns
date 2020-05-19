package com.example.fpgins.BottomNavigation.Settings;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fpgins.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PolicyFragment extends Fragment {
    private TextView mPolicyNum, mPremiumAmountDue, mPremiumOutsatnding, mPaymentDueDate, mInsurancePeriod, mEffectiveDate, mPolicyHolder, mAddress, mReferenceNo, mSTNCDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_policy, container, false);

        mPolicyNum = root.findViewById(R.id.txt_detail_policyNum);
        mPremiumAmountDue = root.findViewById(R.id.txt_detail_premiumAmountDue);
        mPremiumOutsatnding = root.findViewById(R.id.txt_detail_premiumOutstanding);
        mPaymentDueDate = root.findViewById(R.id.txt_detail_paymentDueDate);
        mInsurancePeriod = root.findViewById(R.id.txt_detail_insurancePeriod);
        mEffectiveDate = root.findViewById(R.id.txt_detail_effectiveDate);
        mPolicyHolder = root.findViewById(R.id.txt_detail_policyHolder);
        mAddress = root.findViewById(R.id.txt_detail_address);
        mReferenceNo = root.findViewById(R.id.txt_detail_referenceNumber);
        mSTNCDate = root.findViewById(R.id.txt_detail_stncDate);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null){
            mPolicyNum.setText(bundle.getString("clientpolicynum"));
            mPolicyHolder.setText(bundle.getString("clientassuredname"));
            mInsurancePeriod.setText(bundle.getString("clientinceptiondate") + " - "+ bundle.getString("clientexpirydate"));
            mSTNCDate.setText(bundle.getString("clientinceptiondate"));
            mEffectiveDate.setText(bundle.getString("clientinceptiondate"));
        }
        return root;
    }

}
