package com.example.fpgins.BottomNavigation.FPGAssist;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.fpgins.BottomNavigation.Settings.PersonalInformation;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;
import com.example.fpgins.Utility.DefaultDialog;
import com.example.fpgins.Utility.GetLocation;
import com.example.fpgins.ui.customercare.CustomerCareFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;

public class FPGAssist extends Fragment implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private ImageView mSOS;
    private MapView mMap;
    private View mView;
    private LocationManager mLocationManager;
    private LinearLayout mHelp, mContactUs;
    private Vibrator vibrate;
    private TextView mTimer;
    private boolean isSpeakButtonLongPressed = false;
    private boolean isDone = false;
    private boolean isCancelled = false;
    private CountDownTimer mCountdownTimer;
    private UserData mUserData;
    private  String name, number;
    private int id;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.support_map_view, container, false);
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mHelp = mView.findViewById(R.id.linear_help);
        mContactUs = mView.findViewById(R.id.linear_contact);
        mTimer = mView.findViewById(R.id.txt_timer);
        mSOS = mView.findViewById(R.id.imgSOS);
        mSOS.setOnLongClickListener(speakHoldListener);
        mSOS.setOnTouchListener(speakTouchListener);
        mUserData = new UserData(PreferenceManager.getDefaultSharedPreferences(getContext()));
        name = mUserData.getFirstName() + " " + mUserData.getLastName();
        number = mUserData.getContactNo();
        id = Integer.parseInt(mUserData.getId());


        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HelpActivity.class);
                startActivity(intent);
            }
        });

        mContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CustomerCareFragment.class);
                startActivity(intent);
            }
        });

        return mView;
    }

    private View.OnLongClickListener speakHoldListener = (new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            vibrate = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrate.vibrate(500);
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            String locationAddress = "";
            final String information = name +
                    number;
            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                GetLocation.confirmLocation(getActivity());
            } else {
                final double lattitude = GetLocation.getLattitude(mLocationManager, getActivity());
                final double longitude = GetLocation.getLongitude(mLocationManager, getActivity());
                locationAddress = GetLocation.getExactAddress(lattitude, longitude, getActivity());
                final String finalLocationAddress = locationAddress;

                mCountdownTimer = new CountDownTimer(3000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        if (isCancelled){
                            mCountdownTimer.cancel();

                        } else {
                            mTimer.setText("seconds remaining: " + ((millisUntilFinished / 1000) + 1));
                        }

                    }
                    public void onFinish() {
                        mTimer.setText("done!");
                        isSpeakButtonLongPressed = false;
                        sendSOS(id, finalLocationAddress,
                                Double.valueOf(longitude).toString().trim(),
                                Double.valueOf(lattitude).toString().trim(),
                                number);
                    }
                }.start();
            }
            return true;
        }
    });

    private View.OnTouchListener speakTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View pView, MotionEvent pEvent) {
            if (pEvent.getAction() == MotionEvent.ACTION_UP) {
                mCountdownTimer.cancel();
                isCancelled = true;
                mTimer.setText("Please press button within 3 seconds");
            } else if (pEvent.getAction() == MotionEvent.ACTION_DOWN){
                isCancelled = false;
//                mCountdownTimer.start();

            }
            return false;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMap = (MapView) mView.findViewById(R.id.map);

        if (mMap != null){
            mMap.onCreate(null);
            mMap.onResume();
            mMap.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setScrollGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

        double lat = GetLocation.getLattitude(mLocationManager, getActivity());
        double longitude =  GetLocation.getLongitude(mLocationManager, getActivity());

        LatLng location = new LatLng(lat, longitude);
        String loc = GetLocation.getExactAddress(lat, longitude, getActivity());
        mGoogleMap.addMarker(new MarkerOptions().position(location).title(loc));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        //If in case the location can be changed by the user

//        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                // Setting the position for the marker
//                markerOptions.position(latLng);
//
//                // Setting the title for the marker.
//                // This will be displayed on taping the marker
//                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//
//                // Clears the previously touched position
//                mGoogleMap.clear();
//
//                // Animating to the touched position
//                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                // Placing a marker on the touched position
//                mGoogleMap.addMarker(markerOptions);
//            }
//        });
    }

    public void zoomMapToMarker(Marker marker) {
        //move camera to display marker above the mapAddtionFragment
        Projection projection = mGoogleMap.getProjection();

        LatLng markerLatLng = marker.getPosition();
        Point markerScreenPosition = projection.toScreenLocation(markerLatLng);
        Point pointHalfScreenAbove = new Point(markerScreenPosition.x,
                markerScreenPosition.y + (this.getResources().getDisplayMetrics().heightPixels / 5));

        LatLng aboveMarkerLatLng = projection
                .fromScreenLocation(pointHalfScreenAbove);

        marker.showInfoWindow();
        CameraUpdate center = CameraUpdateFactory.newLatLng(aboveMarkerLatLng);
        mGoogleMap.animateCamera(center);
    }

    public void sendSOS(int accountId, final String location, final String longitude, final String latitude, final String mobile){
        Cloud.sosAlert(accountId, location, longitude, latitude, mobile, new Cloud.ResultListener() {
            @Override
            public void onResult(JSONObject result) {
                int returnCode;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = result;
                    returnCode = Integer.parseInt(jsonObject.get("code").toString());
                }catch (JSONException e){
                    returnCode = Cloud.DefaultReturnCode.INTERNAL_SERVER_ERROR;
                    e.printStackTrace();
                }

                if (returnCode != Cloud.DefaultReturnCode.SUCCESS){
                    //FAIL
                    try {
                        String message = jsonObject.getString("message");
                        Log.d("Server Error Message: ", message);
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }else {
                    //SUCCESS
                    try {
                        sendSMS(mobile, "Location : " + location + "\n"
                                + "Longitude : " + longitude +"\n"
                                + "Latitude : " + latitude +"\n");
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        });
    }

    public void sendSMS(String phoneNo, String msg) {

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, msg, null, null);
                Bitmap mIcon = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.green_check);
//                mSOS.doneLoadingAnimation(R.color.black, mIcon);
                new DefaultDialog.Builder(getActivity())
                        .message("The SOS message has been sent successfully.")
                        .detail("FPG representative will contact you shortly. Thank you.")
                        .positiveAction("Ok", new DefaultDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String et) {
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show();
            } catch (Exception ex) {
                Toast.makeText(getActivity().getApplicationContext(),ex.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
            }
        }

    }
}
