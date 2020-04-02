package com.example.fpgins.Utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkUtil {

    public static final int NETWORK_DELAY = 2000;
    public static final int RENDER_DELAY = 1000;

    /**
     * TODO: need to user Handler() or WeakHandler() to make this more reliable while handling more list of data from API responses
     * Added this to make the MyList more Accurate,
     *      some item on the MyList is sometimes blank if this is not applied
     * @param delay
     */
    public static void simulateNetworkAccess(int delay) {
        // Simulate network access. For 2 Seconds.
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
            return isConnected;
        }
        return false;
    }

    /**
     * TODO: This can be used to check if device is Under API <= 24
     * Return TRUE if Device is Connected to the Internet
     * @param activity - the Calling Activity / Context
     * @return TRUE | FALSE
     */
    public boolean isConnected_(Activity activity) {
        if (activity != null) {
            ConnectivityManager connMgr = null;
            try {
                connMgr = (ConnectivityManager) activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected())
                    return true;
                else
                    return false;
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
}
