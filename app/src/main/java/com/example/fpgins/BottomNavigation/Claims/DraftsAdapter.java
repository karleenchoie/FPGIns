package com.example.fpgins.BottomNavigation.Claims;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.DataModel.MotorsDraft;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.R;
import com.example.fpgins.SQLiteDB.DBHelper;
import com.example.fpgins.Utility.DefaultDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DraftsAdapter extends RecyclerView.Adapter<DraftsAdapter.ViewHolder> {

    private List<MotorsDraft> mData;
    private Context mContext;
    private MotorsDraft mDraftsData;

    public DraftsAdapter (List<MotorsDraft> list, Context context){
        mData = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_motor_draft_file, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mDraftsData = mData.get(position);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = format.parse(mDraftsData.getDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String day = (String) android.text.format.DateFormat.format("dd", date);
        String month = (String) android.text.format.DateFormat.format("MMM", date);
        String year = (String) android.text.format.DateFormat.format("yyyy", date);

        holder.detail.setText("Address");
        holder.detail2.setText("Remarks");

        holder.month.setText(month);
        holder.day.setText(day);
        holder.year.setText(year);

        holder.address.setText(mDraftsData.getLocationAddress());
        holder.remarks.setText(mDraftsData.getRemarks());

    }

    @Override
    public int getItemCount() {
        return mData.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView address;
        public TextView remarks;
        public TextView month;
        public TextView day;
        public TextView year;
        public TextView detail;
        public TextView detail2;
        public ImageView dottedButton;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            month = itemView.findViewById(R.id.month);
            day = itemView.findViewById(R.id.day);
            year = itemView.findViewById(R.id.year);
            address = (TextView) itemView.findViewById(R.id.draftAddress);
            remarks = (TextView) itemView.findViewById(R.id.draftRemarks);
            dottedButton = (ImageView) itemView.findViewById(R.id.dottedButton);
            detail = (TextView) itemView.findViewById(R.id.tvLabelDetail1);
            detail2 = (TextView) itemView.findViewById(R.id.tvLabelDetail2);
            relativeLayout = itemView.findViewById(R.id.relativeParent);
            relativeLayout.setOnClickListener(this);

            dottedButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            final MotorsDraft drafts = mData.get(getLayoutPosition());

            if (v.getId() == R.id.dottedButton){
                new DefaultDialog.Builder(mContext)
                        .message("Are you sure you want to delete?")
                        .detail("")
                        .negativeAction("No", new DefaultDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String et) {
                                dialog.dismiss();
                            }
                        })
                        .positiveAction("Yes", new DefaultDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, String et) {
                                DBHelper dbHelper = new DBHelper(mContext);
                                dbHelper.open();
                                dbHelper.deleteClaimsDraft(drafts.getClaims_motor_id());
                                notifyDataSetChanged();
                                ((DraftsActivity) mContext).getData(drafts.getEmail());
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show();
            } else {
                UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(mContext));

                userData.setDraftsCount(drafts.getImageList());

                Intent intent = new Intent(mContext, ClaimsActivity.class);

                intent.putExtra("draftsCount", drafts.getImageList());

                intent.putExtra("id", drafts.getClaims_motor_id());
                intent.putExtra("fullName", drafts.getFullName());
                intent.putExtra("email", drafts.getEmail());
                intent.putExtra("mobileNo", drafts.getMobileNo());
                intent.putExtra("location", drafts.getLocationAddress());
                intent.putExtra("longitude", drafts.getLongitude());
                intent.putExtra("latitude", drafts.getLattitude());
                intent.putExtra("dateTime", drafts.getDateTime());
                intent.putExtra("type", drafts.getType());
                intent.putExtra("remarks", drafts.getRemarks());
                intent.putExtra("policyNo", drafts.getPolicyNo());
                intent.putExtra("plateNo", drafts.getPlateNo());
                intent.putExtra("conductionStickerNo", drafts.getConductionStickerNo());

                mContext.startActivity(intent);
            }
//
//            ClaimsActivity claimsFragment = new ClaimsActivity();
//            claimsFragment.setArguments(mBundleFragment);
//            mFragment.getActivity().getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.nav_fragment, claimsFragment)
//                    .addToBackStack(null)
//                    .commit();
        }
    }

}
