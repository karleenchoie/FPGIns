package com.example.fpgins.BottomNavigation.AgentDashboard.ClaimsTabActivities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.BottomNavigation.AgentDashboard.AgentsActivities.PADetailsActivity;
import com.example.fpgins.DataModel.PersonalAccidentData;
import com.example.fpgins.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonalAccidentClaimsAdapter extends RecyclerView.Adapter<PersonalAccidentClaimsAdapter.PAViewHolder> {

    private ArrayList<PersonalAccidentData> mPAList;
    private ArrayList<PersonalAccidentData> mCopyList;
    private Context mContext;

    public class PAViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView1;
        public TextView mTextView2;
        public RelativeLayout mRelative;

        public PAViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mRelative = itemView.findViewById(R.id.relative_policy);

            mRelative.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            PersonalAccidentData motorData = mPAList.get(getLayoutPosition());

            Intent intent = new Intent(mContext, PADetailsActivity.class);
            intent.putExtra("platenum", motorData.getmPAPlateNo());
            intent.putExtra("chassisnum", motorData.getmPAChassisNo());
            intent.putExtra("carmake", motorData.getmPACarMake());
            intent.putExtra("carValue", motorData.getmPACarValue());
            intent.putExtra("carYear", motorData.getmPACarYear());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public PersonalAccidentClaimsAdapter(ArrayList<PersonalAccidentData> mMotorList, Context context) {
        this.mPAList = mMotorList;
        this.mContext = context;
        mCopyList = mMotorList;
    }

    @Override
    public PAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.motor_item_list,
                parent, false);
        PAViewHolder evh = new PAViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(PAViewHolder holder, int position) {
        PersonalAccidentData currentItem = mPAList.get(position);
        holder.mTextView1.setText(currentItem.getmPAHolder());
        holder.mTextView2.setText(currentItem.getmPAType());
    }

    @Override
    public int getItemCount() {
        return mPAList.size();
    }

    public void filterList(ArrayList<PersonalAccidentData> filteredList) {
        mPAList = filteredList;
        notifyDataSetChanged();
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<PersonalAccidentData> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = mCopyList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mPAList = (ArrayList<PersonalAccidentData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    protected ArrayList<PersonalAccidentData> getFilteredResults(String constraint){
        ArrayList<PersonalAccidentData> results = new ArrayList<>();
        String[] content = constraint.split(",");

        String value = content[0];
        String year = content[1];

        String answer = constraint.substring(constraint.indexOf("["),constraint.indexOf("]"));

        String replace = answer.replaceAll("^\\[|]$", "");
        String replace1 = replace.replace("]","");
        List<String> makeList = new ArrayList<String>(Arrays.asList(replace1.split(",")));

        for (PersonalAccidentData item : mCopyList) {
            for (int counter = 0; counter < makeList.size(); counter++) {
                String make = makeList.get(counter);
                if (item.getmPACarYear().contains(year.trim())
                        && item.getmPACarMake().toLowerCase().contains(make.trim())) {
                    results.add(item);
                }
            }
        }
        return results;
    }
}