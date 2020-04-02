package com.example.fpgins.Login.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class FCMSessionManager {

    private static final String TAG = FCMSessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    private Context _context;
    private static FCMSessionManager mInstance;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_FCM_NAME = "FCMPref";

    // Firebase Cloud Messaging Key
    public static final String KEY_FCM_TOKEN = "fcm_token";

    // Constructor
    private FCMSessionManager(Context context) {
        this._context = context;
        if (_context == null) ;
        else {
            pref = _context.getSharedPreferences(PREF_FCM_NAME, Context.MODE_PRIVATE);
            // Log.e(TAG, "pref.contains(PREF_NAME)=" + pref.contains(PREF_NAME));
            editor = pref.edit();
        }
    }

    public static synchronized FCMSessionManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FCMSessionManager(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    // NOTE : Token will be generated on Fresh Install
    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_FCM_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FCM_TOKEN, token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken() {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(PREF_FCM_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(KEY_FCM_TOKEN, null);
    }

    public void clearPrefs() {
        // Clearing all data from Shared Preferences
        // if (editor == null) return;
        editor.clear();
        editor.commit();
    }

}
