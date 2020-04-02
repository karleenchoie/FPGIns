package com.example.fpgins.ui.motors;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fpgins.ui.motors.Steps.StepOneMotorFragment;
import com.example.fpgins.ui.motors.Steps.StepTwoMotorFragment;

public class MotorsAdapter extends FragmentPagerAdapter {
    public MotorsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new StepOneMotorFragment();
            case 1:
                return new StepTwoMotorFragment();
            default:
                break;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
