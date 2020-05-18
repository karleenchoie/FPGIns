package com.example.fpgins.BottomNavigation.Settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.example.fpgins.BottomSheetDialog.BottomSheetMaterialDialog;
import com.example.fpgins.CircularImageView;
import com.example.fpgins.CreatePassword;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Login.Login;
import com.example.fpgins.Login.Session.UserSessionManager;
import com.example.fpgins.Network.Cloud;
import com.example.fpgins.Network.ImageUploaderUtility.DownloadImageTask;
import com.example.fpgins.R;
import com.mvc.imagepicker.ImagePicker;

public class SettingsFragment extends Fragment {

    private UserSessionManager mSession;
    private ImageView mBack;
    private CircularImageView mAccountPicture;
    private ImageView mEditPic;
    private TextView mPersonalInfo, mFullName;
    private TextView mChangePassword, mAccountType;
    private TextView mLogout, mPolicy;
    private Dialog mDialog;
    private UserData mUserData;
    private final int SELECT_PICTURE = 1;
    private String mSelectedImagePath;
    private BottomSheetMaterialDialog bottomSheetMaterialDialog;
    String accountCode = "";
    String accountType = "";
    String mPath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_settings, container, false);
        initialize(root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(SettingsFragment.this)
                .asDrawable()
                .placeholder(R.drawable.default_image)
                .load(mUserData.getPhoto())
                .into(mAccountPicture);

        mFullName.setText(mUserData.getFirstName()+ " " + mUserData.getLastName());
    }

    private void initialize(View view){

        mFullName = view.findViewById(R.id.tvName);
        mPersonalInfo = view.findViewById(R.id.txt_personalInformation);
        mChangePassword = view.findViewById(R.id.txt_changePassword);
        mAccountType = view.findViewById(R.id.txt_AcctType);
        mPolicy = view.findViewById(R.id.txt_policy);
        mLogout = view.findViewById(R.id.txt_logout);
        mAccountPicture = view.findViewById(R.id.img_accountPic);
        mEditPic = view.findViewById(R.id.img_editAccountPic);
        mDialog = createLoadingDialog();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        mUserData = new UserData(PreferenceManager.getDefaultSharedPreferences(getContext()));
        accountCode = mUserData.getAccountCode();
        mFullName.setText(mUserData.getFirstName()+ " " + mUserData.getLastName());

        if (accountCode.equals("AGT")){
            accountType = "AGENT";
        } else {
            accountType = "CLIENT";
        }

        Glide.with(SettingsFragment.this)
                .asDrawable()
                .placeholder(R.drawable.default_image)
                .load(mUserData.getPhoto())
                .into(mAccountPicture);

        mAccountType.setText(accountType+ " | " + mUserData.getUsername());

        mAccountPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogView = inflater.inflate(R.layout.image_viewing, null);
                ImageView img1 = (ImageView) dialogView.findViewById(R.id.picture);
                TextView title = (TextView) dialogView.findViewById(R.id.txtFirst);
                ImageView close = dialogView.findViewById(R.id.img_close);
                title.setVisibility(View.GONE);
                Glide.with(getContext())
                        .asBitmap()
                        .placeholder(R.drawable.default_image)
                        .load(mUserData.getPhoto())
                        .into(img1);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alertDialog!=null && alertDialog.isShowing()){
                            alertDialog.dismiss();
                        }
                    }
                });
                alertDialog.setView(dialogView);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });


        mPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalInformation.class);
                startActivity(intent);
            }
        });

        mPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClientPolicyActivity.class);
                startActivity(intent);
            }
        });

        mChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreatePassword.class);
                startActivity(intent);
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetMaterialDialog == null){
                    Logout();
                }else {
                    bottomSheetMaterialDialog.dismiss();
                    Logout();
                }
            }
        });

        mEditPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.pickImage(SettingsFragment.this, "Select your image:");

            }
        });

//        mShareButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                    shareIntent.setType("text/plain");
//                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "FPG Mobile\n");
//                    String shareMessage = "You can click this link for installation of FPG Mobile app\n\nLink";
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//                    startActivity(Intent.createChooser(shareIntent, "Choose One"));
//                } catch(Exception e) {
//                    e.getMessage();
//                }
//            }
//        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0){
            //do nothing
        } else {
            mDialog.show();
            Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), requestCode, resultCode, data);
            Cloud.uploadProfilePicture(mUserData.getEmail(), mUserData.getUsername(), bitmap, getActivity(), mAccountPicture, mDialog);
            onResume();
        }
    }

    private Dialog createLoadingDialog() {
        Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Black);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.progress_bar, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        return dialog;
    }
    private void Logout(){

        bottomSheetMaterialDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.logout_sure))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.logout) ,new BottomSheetMaterialDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        mSession = UserSessionManager.getInstance(getActivity());
                        mSession.clearPrefs();
                        Intent i = new Intent(getActivity(), Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
                        getActivity().finish();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new BottomSheetMaterialDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        bottomSheetMaterialDialog.show();
    }


}
