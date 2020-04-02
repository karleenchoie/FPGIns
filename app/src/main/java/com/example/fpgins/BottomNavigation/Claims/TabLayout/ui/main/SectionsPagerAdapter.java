package com.example.fpgins.BottomNavigation.Claims.TabLayout.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fpgins.BottomNavigation.Claims.TabLayout.ForminfoFragment;
import com.example.fpgins.BottomNavigation.Claims.TabLayout.StatusFragment;
import com.example.fpgins.BottomNavigation.Claims.TabLayout.MessagingFragment;
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
                ForminfoFragment forminfoFragment = new ForminfoFragment();
                return forminfoFragment;
            case 1:
                StatusFragment statusFragment = new StatusFragment();
                return statusFragment;
            case 2:
                MessagingFragment messagingFragment = new MessagingFragment();
                return messagingFragment;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return mContext.getResources().getString(R.string.information);
            case 1:
                return mContext.getResources().getString(R.string.status);
            case 2:
                return mContext.getResources().getString(R.string.messaging);
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}