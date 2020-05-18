package com.example.fpgins.BottomNavigation.Settings.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.fpgins.BottomNavigation.Settings.PolicyFragment;
import com.example.fpgins.BottomNavigation.Settings.VehicleFragment;
import com.example.fpgins.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1);
        switch (position){
            case 0:
                PolicyFragment policyFragment = new PolicyFragment();
                return policyFragment;
            case 1:
                VehicleFragment vehicleFragment = new VehicleFragment();
                return vehicleFragment;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return mContext.getResources().getString(R.string.policy);
            case 1:
                return mContext.getResources().getString(R.string.vehicle);
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}