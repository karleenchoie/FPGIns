package com.example.fpgins.BottomNavigation.Claims;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fpgins.BottomNavigation.Claims.TabLayout.TabularActivity;
import com.example.fpgins.DataModel.MotorsDraft;
import com.example.fpgins.DataModel.SubmittedFormsData;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SubmittedFormsAdapter extends RecyclerView.Adapter<SubmittedFormsAdapter.ViewHolder> {

    private List<SubmittedFormsData> submittedFormsList;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View statusColor;
        public TextView month;
        public TextView date;
        public TextView year;
        public TextView claimNo;
        public TextView policyNo;
        public ImageButton statusImage;
        public TextView statusText;
        public LinearLayout linearLayout;

        public ViewHolder(View view){
            super(view);
//            statusColor = view.findViewById(R.id.colorStatus);
//            month = view.findViewById(R.id.month);
//            day = view.findViewById(R.id.day);
//            year = view.findViewById(R.id.year);
            claimNo = view.findViewById(R.id.tvTitle);
            policyNo = view.findViewById(R.id.tvDesc);
            date = view.findViewById(R.id.tvDate);
//            statusImage = view.findViewById(R.id.iconStatus);
            linearLayout = view.findViewById(R.id.clickLayout);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SubmittedFormsData submittedFormsData = submittedFormsList.get(getLayoutPosition());
            UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(context));
            userData.setSelectedClaimNo(submittedFormsData.getClaim());
            Intent intent = new Intent(context, TabularActivity.class);
            context.startActivity(intent);
        }
    }

    public SubmittedFormsAdapter(List<SubmittedFormsData> submittedFormsList, Context context) {
        this.submittedFormsList = submittedFormsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.submitted_forms_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubmittedFormsData submittedFormsData = submittedFormsList.get(position);


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = format.parse(submittedFormsData.getDatesubmitted());
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        String day = (String) android.text.format.DateFormat.format("dd", date);
//        String month = (String) android.text.format.DateFormat.format("MMM", date);
//        String year = (String) android.text.format.DateFormat.format("yyyy", date);
//
//        holder.month.setText(month);
//        holder.day.setText(day);
//        holder.year.setText(year);

        SimpleDateFormat output = new SimpleDateFormat("MMMM dd, yyyy");
        String y = output.format(date);

        holder.claimNo.setText(submittedFormsData.getClaim());
        holder.policyNo.setText(submittedFormsData.getPolicy());
        holder.date.setText(y);
    }


    @Override
    public int getItemCount() {
        return submittedFormsList.size();
    }
}
