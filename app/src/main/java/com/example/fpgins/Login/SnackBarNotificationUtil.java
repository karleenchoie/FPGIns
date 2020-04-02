package com.example.fpgins.Login;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fpgins.R;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

/**
 * Created by admin 4 on 04/04/2017.
 * Just to eliminate Redudant Call and same implementation of Snackbar within the App
 * USed for Fragments
 */

public final class SnackBarNotificationUtil {

    private static final String TAG = SnackBarNotificationUtil.class.getSimpleName();
    private static SnackBarNotificationUtil mSnackBarNotificationUtil;
    private static Snackbar mSnackbar;
    // private static int mColor = Color.WHITE;

    SnackBarNotificationUtil() {}

    public static SnackBarNotificationUtil setSnackBar(View view, CharSequence message) {
        mSnackBarNotificationUtil = new SnackBarNotificationUtil();
        mSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        return mSnackBarNotificationUtil;
    }

    public SnackBarNotificationUtil setColor(int color) {
        mSnackbar.setActionTextColor(color);
        TextView tv = (TextView) mSnackbar.getView().findViewById(R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        return mSnackBarNotificationUtil;
    }
// For Activity
    public void show() {
        if (((Activity) mSnackbar.getContext()) == null)
            // Assume the Context is Activity
            return;
        if (mSnackBarNotificationUtil != null && mSnackbar != null) {
            if (mSnackbar.isShown())
                mSnackbar.dismiss();

            if (!mSnackbar.isShown())
                mSnackbar.show();
        }
    }
//OR For Fragment
    private boolean fragmentIsAddedProperly(Fragment fragment) {
        return fragment.getActivity() != null && fragment.isAdded() && fragment.isVisible();
    }

    public void show(Fragment fragment) {
        if (fragmentIsAddedProperly(fragment)) {
            if (mSnackBarNotificationUtil != null && mSnackbar != null) {
                if (mSnackbar.isShown())
                    mSnackbar.dismiss();

                if (!mSnackbar.isShown())
                    mSnackbar.show();
            }
        } else {
            Log.e(TAG, "show: Fragment is not initialzed properly");
        }
    }

}
