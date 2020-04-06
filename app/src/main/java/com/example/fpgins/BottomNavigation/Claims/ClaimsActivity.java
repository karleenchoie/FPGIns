package com.example.fpgins.BottomNavigation.Claims;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fpgins.DataModel.Images;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Login.SnackBarNotificationUtil;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.R;
import com.example.fpgins.SQLiteDB.DBHelper;
import com.example.fpgins.Utility.DefaultDialog;
import com.example.fpgins.Utility.GetLocation;
import com.example.fpgins.ui.NotificationMessage.NotifMessageFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mindorks.paracamera.Camera;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class ClaimsActivity extends AppCompatActivity {

    private FloatingActionButton mFab;
    private ArrayList<Images> mData = new ArrayList<>();
    private ImagesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private DBHelper mDBHelper;
    private View mNoContent;

    private LocationManager mLocationManager;

    private Images mImageData;

    private TextView mName;
    private TextView mEmail;
    private TextView mContactNumber;

    private LinearLayout mButtons;

    private UserData mUserData;

    private Button mSaveAsDraft;
    private Button mSubmit;

    private EditText mAddress;
    private EditText mRemarks;
    private EditText mPolicyNo;
    private EditText mPlateNo;
    private EditText mConduction;

    private String mLocationAddress;
    private double mLattitude;
    private double mLongitude;

    private String draftPK;

    String motorId;
    String fullName;
    String email;
    String mobileNo;
    String location;
    String longitude;
    String latitude;
    String dateTime;
    String type;
    String remarks;
    String policyNo;
    String plateNo;
    String conductionStickerNo;
    String userName;

    ArrayList<Bitmap> mBitmaps = new ArrayList<Bitmap>();
    ArrayList<String> mFileNames = new ArrayList<String>();

    private Camera mCamera;
    private String mImageName;

    private Dialog mDialog;

    private ImageView mBackButton;

    String wantPermission = Manifest.permission.READ_PHONE_STATE;

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mNoContent = findViewById(R.id.noContent);

        mBackButton = findViewById(R.id.img_backbutton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mDialog = createLoadingDialog();

        mName = (TextView) findViewById(R.id.name);
        mEmail = (TextView) findViewById(R.id.email);
        mContactNumber = (TextView) findViewById(R.id.number);

        mButtons = (LinearLayout) findViewById(R.id.buttonLayout);

        mAddress = (EditText) findViewById(R.id.address);
        mRemarks = (EditText) findViewById(R.id.remarks);
        mPolicyNo = (EditText) findViewById(R.id.policyNo);
        mPlateNo = (EditText) findViewById(R.id.plateNo);
        mConduction = (EditText) findViewById(R.id.conductionStickerNo);

        mSaveAsDraft = (Button) findViewById(R.id.btnSaveAsDraft);
        mSubmit = (Button) findViewById(R.id.btnSubmit);

        mRecyclerView = findViewById(R.id.imageListView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(ClaimsActivity.this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                ClaimsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ImagesAdapter(mData, ClaimsActivity.this);


        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.fpg_orange));
        mSwipeRefresh.setDistanceToTriggerSync(450);// in dips
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(true);
                //preparing data all again when ever pull to refresh is active
                getImages(draftPK);
            }

        });


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictures();
            }
        });

        mUserData = new UserData(PreferenceManager.getDefaultSharedPreferences(ClaimsActivity.this));
        mName.setText(":  " + mUserData.getFirstName() + " " + mUserData.getLastName());
        mEmail.setText(":  " + mUserData.getEmail());
        userName = ":  " +  mUserData.getUsername();

        if (!checkPermission(wantPermission)) {
            requestPermission(wantPermission);
        } else {
            mContactNumber.setText(":  " + getPhone());
            Log.d(TAG, "Phone number: " + getPhone());
        }

        Bundle bundle = getIntent().getExtras();

        if (bundle == null){
            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                GetLocation.confirmLocation(ClaimsActivity.this);
            } else {
                mLattitude = GetLocation.getLattitude(mLocationManager, ClaimsActivity.this);
                mLongitude = GetLocation.getLongitude(mLocationManager, ClaimsActivity.this);
                mLocationAddress = GetLocation.getExactAddress(mLattitude, mLongitude, ClaimsActivity.this);
            }
            draftPK = "";
            mRemarks.setText("");
            mSaveAsDraft.setVisibility(View.VISIBLE);
//            mSaveAsDraft.setText(getResources().getString(R.string.edit));

            DBHelper dbHelper = new DBHelper(ClaimsActivity.this);
            dbHelper.open();
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String formattedDate = df.format(c);

            fullName = mUserData.getFirstName() + " " + mUserData.getLastName();
            email = mUserData.getEmail();
            longitude = String.valueOf(mLongitude);
            latitude = String.valueOf(mLattitude);
            dateTime = formattedDate;
            type = "FORM";
            mAddress.setText(mLocationAddress);

        } else {
            draftPK = bundle.getString("draftsCount");
            mRemarks.setText(bundle.getString("remarks"));

            mSaveAsDraft.setVisibility(View.VISIBLE);
            mSaveAsDraft.setText(getResources().getString(R.string.edit));

            motorId = bundle.getString("id");
            fullName = bundle.getString("fullName");
            email = bundle.getString("email");
            mobileNo = bundle.getString("mobileNo");
            location = bundle.getString("location");
            longitude = bundle.getString("longitude");
            latitude = bundle.getString("latitude");
            dateTime = bundle.getString("dateTime");
            type = bundle.getString("type");
            remarks = bundle.getString("remarks");
            policyNo = bundle.getString("policyNo");
            plateNo = bundle.getString("plateNo");
            conductionStickerNo = bundle.getString("conductionStickerNo");

            mPolicyNo.setText(policyNo);
            mPlateNo.setText(plateNo);
            mAddress.setText(location);
            mConduction.setText(conductionStickerNo);
            mRemarks.setText(remarks);
        }

        getImages(draftPK);
//        settingDummyData(draftPK);

        mSaveAsDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                draftFunction();
//                DBHelper dbHelper = new DBHelper(ClaimsActivity.this);
//                dbHelper.open();
//
//                if (mSaveAsDraft.getText().toString().contentEquals(getString(R.string.edit))){
//                    //edit on sqlite
//                    boolean isUpdated = dbHelper.updateMotorDraftRow(motorId, mPolicyNo.getText().toString(), mPlateNo.getText().toString(),
//                                            mConduction.getText().toString(), mRemarks.getText().toString(), mAddress.getText().toString(),
//                                            longitude, latitude);
//
//                    if (isUpdated){
//                        Toast.makeText(ClaimsActivity.this, "Successfully editted", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(ClaimsActivity.this, DraftsActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Toast.makeText(ClaimsActivity.this, "Unable to edit", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    mUserData.setDrafts(true);
//                    //first image that has been uploaded
//                    mImageData = mData.get(0);
//                    Date c = Calendar.getInstance().getTime();
//                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                    String formattedDate = df.format(c);
//                    String fullName = mUserData.getFirstName() + " " + mUserData.getLastName();
//                    boolean isInserted = dbHelper.insertMotorDetails(mUserData.getEmail(), fullName, mContactNumber.getText().toString(), mAddress.getText().toString(),
//                            String.valueOf(mLongitude), String.valueOf(mLattitude), formattedDate, mImageData.getBitmap(), mRemarks.getText().toString(), mUserData.getDraftsCount(),
//                            "Form", mPolicyNo.getText().toString(), mPlateNo.getText().toString(), mConduction.getText().toString());
//
//                    if (isInserted){
//                        Intent intent = new Intent(ClaimsActivity.this, DraftsActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Toast.makeText(ClaimsActivity.this.getApplicationContext(), "Can't add in drafts", Toast.LENGTH_LONG).show();
//                    }
//                }
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DefaultDialog.Builder(ClaimsActivity.this)
                        .message(ClaimsActivity.this.getResources().getString(R.string.before_submit_msg))
                        .detail(ClaimsActivity.this.getResources().getString(R.string.before_submit_detail))
                        .positiveAction(ClaimsActivity.this.getResources().getString(R.string.confirm), new DefaultDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String et) {
                                mDialog.show();

                                String draft = draftKey();

                                mBitmaps.clear();
                                mFileNames.clear();

                                String[] information = {mUserData.getLastName(),
                                        mUserData.getFirstName(),
                                        mUserData.getEmail(),
                                        getPhone(),
//                                        mContactNumber.getText().toString(),
//                                        "0123456789",
                                        mPolicyNo.getText().toString(),
                                        "",
                                        mPlateNo.getText().toString(),
                                        mConduction.getText().toString(),
                                        mAddress.getText().toString(),
                                        longitude,
                                        latitude,
                                        dateTime,
                                        type,
                                        mRemarks.getText().toString(),
                                        mUserData.getId()};

                                mDBHelper = new DBHelper(ClaimsActivity.this);
                                mDBHelper.open();

                                mBitmaps.addAll(mDBHelper.retrieveImagesToUpload(draft));
                                mFileNames.addAll(mDBHelper.retrieveImageNames(draft));
                                mDBHelper.close();

                                postMotorClaim(information);

                                dialog.dismiss();
                            }
                        })
                        .negativeAction(getApplicationContext().getResources().getString(R.string.cancel), new DefaultDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String et) {
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show();
            }
        });
    }

    public void takePictures(){
        mImageName = "Image" + System.currentTimeMillis();
        mCamera = new Camera.Builder()
                .resetToCorrectOrientation(true)
                .setTakePhotoRequestCode(0)
                .setDirectory("FPG")
                .setName(mImageName)
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)
                .build(ClaimsActivity.this);

        try {
            mCamera.takePicture();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(ClaimsActivity.this, android.R.style.Theme_Black);
        View view = LayoutInflater.from(ClaimsActivity.this).inflate(R.layout.progress_bar, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        return dialog;
    }

    private void draftFunction(){
        DBHelper dbHelper = new DBHelper(ClaimsActivity.this);
        dbHelper.open();

        if (mSaveAsDraft.getText().toString().contentEquals(getString(R.string.edit))){
            //edit on sqlite
            boolean isUpdated = dbHelper.updateMotorDraftRow(motorId, mPolicyNo.getText().toString(), mPlateNo.getText().toString(),
                    mConduction.getText().toString(), mRemarks.getText().toString(), mAddress.getText().toString(),
                    longitude, latitude);

            if (isUpdated){
                Toast.makeText(ClaimsActivity.this, "Successfully editted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ClaimsActivity.this, DraftsActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(ClaimsActivity.this, "Unable to edit", Toast.LENGTH_SHORT).show();
            }

        } else {
            mUserData.setDrafts(true);
            //first image that has been uploaded
            mImageData = mData.get(0);
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String formattedDate = df.format(c);
            String fullName = mUserData.getFirstName() + " " + mUserData.getLastName();
            boolean isInserted = dbHelper.insertMotorDetails(mUserData.getEmail(), fullName, mContactNumber.getText().toString(), mAddress.getText().toString(),
                    String.valueOf(mLongitude), String.valueOf(mLattitude), formattedDate, mImageData.getBitmap(), mRemarks.getText().toString(), mUserData.getDraftsCount(),
                    "Form", mPolicyNo.getText().toString(), mPlateNo.getText().toString(), mConduction.getText().toString());

            if (isInserted){
                Intent intent = new Intent(ClaimsActivity.this, DraftsActivity.class);
                startActivity(intent);
                Toast.makeText(ClaimsActivity.this.getApplicationContext(), "Successfully added to drafts", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(ClaimsActivity.this.getApplicationContext(), "Can't add in drafts", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO){
            Bitmap bitmap = mCamera.getCameraBitmap();
            if (bitmap != null){
                //upload image in SQLite
                uploadImage();
            } else {
                Toast.makeText(this, "Picture not taken", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImage(){

        String draft = draftKey();

        DBHelper dbHelper = new DBHelper(this);
        dbHelper.open();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String formattedDate = df.format(c);
        boolean isInserted = dbHelper.inserImagesDetails(mCamera.getCameraBitmap(), draft, formattedDate, mImageName);

        if (isInserted){
            Toast.makeText(this, R.string.upload_complete, Toast.LENGTH_LONG).show();
            getImages(draft);
        } else {
            Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_LONG).show();
        }

    }



    private void postMotorClaim(final String[] info){
        Cloud.motorRegister(info, new Cloud.ResultListener() {
            @Override
            public void onResult(JSONObject result) {
                int returnCode;
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject = result;
                    returnCode = Integer.parseInt(jsonObject.get("code").toString());
                } catch (JSONException e){
                    returnCode = Cloud.DefaultReturnCode.INTERNAL_SERVER_ERROR;
                    e.printStackTrace();
                }

                if (returnCode == Cloud.DefaultReturnCode.INTERNAL_SERVER_ERROR){
                    new DefaultDialog.Builder(ClaimsActivity.this)
                            .message(ClaimsActivity.this.getResources().getString(R.string.internet_connection_error))
                            .detail(ClaimsActivity.this.getResources().getString(R.string.internet_connection_detail))
                            .positiveAction(ClaimsActivity.this.getResources().getString(R.string.retry), new DefaultDialog.OnClickListener() {
                                @Override
                                public void onClick(Dialog dialog, String et) {
                                    //retry
                                    postMotorClaim(info);
                                    dialog.dismiss();
                                }
                            })
                            .negativeAction(ClaimsActivity.this.getResources().getString(R.string.save_as_draft), new DefaultDialog.OnClickListener() {
                                @Override
                                public void onClick(Dialog dialog, String et) {
                                    draftFunction();
                                }
                            })
                            .build()
                            .show();
                } else {
                    if (returnCode != Cloud.DefaultReturnCode.SUCCESS){
                        //FAIL
                        try {
                            mDialog.dismiss();
                            String message = jsonObject.getString("message");
                            Toast.makeText(ClaimsActivity.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //SUCCESS
                        try {
                            String mobileId = jsonObject.getString("mobile_id");
                            Cloud.upload(mBitmaps, mFileNames, mobileId,ClaimsActivity.this,  motorId, mDialog, draftKey());

                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mContactNumber.setText(":  " + getPhone());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!mSaveAsDraft.getText().toString().contentEquals(getResources().getString(R.string.edit))){
            mDBHelper = new DBHelper(ClaimsActivity.this);
            mDBHelper.open();
            mDBHelper.deleteImages(draftKey());
            mDBHelper.close();
        }
    }

    private String draftKey(){
        UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(ClaimsActivity.this));
        String draft = "";

        if (mSaveAsDraft.getText().toString().contentEquals(getResources().getString(R.string.edit))){
            draft = draftPK;
        } else {
            draft = userData.getDraftsCount();
        }
        return draft;
    }

    private void requestPermission(String permission){
        if (ActivityCompat.shouldShowRequestPermissionRationale(ClaimsActivity.this, permission)){
            Toast.makeText(ClaimsActivity.this, "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(ClaimsActivity.this, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Phone number: " + getPhone());
                } else {
                    Toast.makeText(ClaimsActivity.this,"Permission Denied. We can't get phone number.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private String getPhone() {
        TelephonyManager phoneMgr = (TelephonyManager) ClaimsActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(ClaimsActivity.this, wantPermission) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return phoneMgr.getLine1Number();
    }

    private boolean checkPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(ClaimsActivity.this, permission);
            if (result == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public void getImages(String fileID){
        mData.clear();

        mDBHelper = new DBHelper(ClaimsActivity.this);

        mDBHelper.open();

        mData.addAll(mDBHelper.retrieveAllImagesDetails(fileID));

        if (mData.size() != 0){
            mButtons.setVisibility(View.VISIBLE);
            mNoContent.setVisibility(View.INVISIBLE);
        } else {
            mButtons.setVisibility(View.INVISIBLE);
            mNoContent.setVisibility(View.VISIBLE);
        }

        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefresh.setRefreshing(false);

        mDBHelper.close();

    }
}
