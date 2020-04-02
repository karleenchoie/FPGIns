package com.example.fpgins.ui.motors.Steps;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fpgins.R;

public class StepOneMotorFragment extends Fragment {

    private StepOneMotorViewModel mViewModel;

    public static StepOneMotorFragment newInstance() {
        return new StepOneMotorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_motor_step_one, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StepOneMotorViewModel.class);
        // TODO: Use the ViewModel
    }

}
