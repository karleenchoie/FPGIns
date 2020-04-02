package com.example.fpgins.ui.motors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.fpgins.R;
import com.example.fpgins.ui.motors.Steps.StepFourMotorFragment;
import com.example.fpgins.ui.motors.Steps.StepOneMotorFragment;
import com.example.fpgins.ui.motors.Steps.StepThreeMotorFragment;
import com.example.fpgins.ui.motors.Steps.StepTwoMotorFragment;

public class MotorsFragment extends Fragment {


    private Button mNext;
    private MotorsViewModel motorsViewModel;
    private View mLineIndicator1;
    private View mLineIndicator2;
    private View mLineIndicator3;
    private int mIndicator = 0;

    private TextView mCircle1;
    private TextView mCircle2;
    private TextView mCircle3;
    private TextView mCircle4;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        motorsViewModel = ViewModelProviders.of(this).get(MotorsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_motors, container, false);
        mNext = (Button) root.findViewById(R.id.btnNext);
        mLineIndicator1 = (View) root.findViewById(R.id.line1);
        mLineIndicator2 = (View) root.findViewById(R.id.line2);
        mLineIndicator3 = (View) root.findViewById(R.id.line3);

        mCircle1 = (TextView) root.findViewById(R.id.step1circle);
        mCircle2 = (TextView) root.findViewById(R.id.step2circle);
        mCircle3 = (TextView) root.findViewById(R.id.step3circle);
        mCircle4 = (TextView) root.findViewById(R.id.step4circle);

        mCircle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndicator = 0;
                StepOneMotorFragment stepOneMotorFragment = new StepOneMotorFragment();
                setViews(0, 0, stepOneMotorFragment, true);
            }
        });

        mCircle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndicator = 1;
                StepTwoMotorFragment stepTwoMotorFragment = new StepTwoMotorFragment();
                setViews(1, 1, stepTwoMotorFragment, true);
            }
        });

        mCircle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndicator = 2;
                StepThreeMotorFragment stepThreeMotorFragment = new StepThreeMotorFragment();
                setViews(2, 2, stepThreeMotorFragment, true);
            }
        });

        mCircle4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndicator = 3;
                StepFourMotorFragment stepFourMotorFragment = new StepFourMotorFragment();
                setViews(3, -1, stepFourMotorFragment, true);
            }
        });

        StepOneMotorFragment stepOneMotorFragment = new StepOneMotorFragment();
        setViews(0, -1, stepOneMotorFragment, false);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_motor_fragment);

                if (fragment instanceof StepOneMotorFragment){
                    StepTwoMotorFragment stepTwoMotorFragment = new StepTwoMotorFragment();
                    setViews(1, 0, stepTwoMotorFragment, false);
                } else if (fragment instanceof StepTwoMotorFragment){
                    StepThreeMotorFragment stepThreeMotorFragment = new StepThreeMotorFragment();
                    setViews(2, 1, stepThreeMotorFragment, false);
                } else if (fragment instanceof StepThreeMotorFragment){
                    StepFourMotorFragment stepFourMotorFragment = new StepFourMotorFragment();
                    setViews(3, 2, stepFourMotorFragment, false);
                }
            }
        });

        return root;
    }

    private void setViews(int currentSlide, int currentSlideLine, Fragment fragment, boolean isClearIndicators){

        TextView[] textViews = {mCircle1, mCircle2, mCircle3, mCircle4};
        View[] lineIndicators = {mLineIndicator1, mLineIndicator2, mLineIndicator3};

        for (int i = 0; i <= 3; i++){
            if (i == currentSlide){
                textViews[i].setBackgroundResource(R.drawable.circle_done);
            }

            if (i == currentSlideLine){
                lineIndicators[i].setBackgroundColor(getResources().getColor(R.color.fpg_orange));
            }

            if (mIndicator > i){
                textViews[i].setBackgroundResource(R.drawable.circle_done);
                lineIndicators[i].setBackgroundColor(getResources().getColor(R.color.fpg_orange));
            }
        }


        if (isClearIndicators){
            for (int i = mIndicator + 1; i <= 3; i++){
                textViews[i].setBackgroundResource(R.drawable.circle_processing);
                lineIndicators[i - 1].setBackgroundColor(getResources().getColor(R.color.black_opacity20));
            }
        }

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_motor_fragment, fragment, "")
                .addToBackStack(null)
                .commit();

    }

}
