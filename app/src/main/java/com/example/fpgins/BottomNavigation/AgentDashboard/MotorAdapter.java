package com.example.fpgins.BottomNavigation.AgentDashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.BottomNavigation.AgentDashboard.AgentsActivities.MotorDetailsActivity;
import com.example.fpgins.DataModel.MotorData;
import com.example.fpgins.R;

import java.util.ArrayList;

public class MotorAdapter extends RecyclerView.Adapter<MotorAdapter.MotorViewHolder> {

    private ArrayList<MotorData> mMotorList;
    private Context mContext;

    public class MotorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView1;
        public TextView mTextView2;
        public RelativeLayout mRelative;

        public MotorViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mRelative = itemView.findViewById(R.id.relative_policy);

            mRelative.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            MotorData motorData = mMotorList.get(getLayoutPosition());

            Intent intent = new Intent(mContext, MotorDetailsActivity.class);
            intent.putExtra("platenum", motorData.getmPolicyPlateNo());
            intent.putExtra("chassisnum", motorData.getmPolicyChassisNo());
            intent.putExtra("carmake", motorData.getmPolicyCarMake());
            intent.putExtra("carValue", motorData.getmPolicyCarValue());
            intent.putExtra("carYear", motorData.getmPolicyCarYear());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public MotorAdapter(ArrayList<MotorData> mMotorList, Context context) {
        this.mMotorList = mMotorList;
        this.mContext = context;
    }

    @Override
    public MotorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.motor_item_list,
                parent, false);
        MotorViewHolder evh = new MotorViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(MotorViewHolder holder, int position) {
        MotorData currentItem = mMotorList.get(position);
        holder.mTextView1.setText(currentItem.getmPolicyHolder());
        holder.mTextView2.setText(currentItem.getmPolicyType());
    }

    @Override
    public int getItemCount() {
        return mMotorList.size();
    }

    public void filterList(ArrayList<MotorData> filteredList) {
        mMotorList = filteredList;
        notifyDataSetChanged();
    }
}