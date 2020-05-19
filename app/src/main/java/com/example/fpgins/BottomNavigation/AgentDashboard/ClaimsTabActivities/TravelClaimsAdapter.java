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

import com.example.fpgins.BottomNavigation.AgentDashboard.AgentsActivities.TravelDetailsActivity;
import com.example.fpgins.DataModel.TravelData;
import com.example.fpgins.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TravelClaimsAdapter extends RecyclerView.Adapter<TravelClaimsAdapter.TravelViewHolder> {

    private ArrayList<TravelData> mTravelList;
    private ArrayList<TravelData> mCopyList;
    private Context mContext;

    public class TravelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView1;
        public TextView mTextView2;
        public RelativeLayout mRelative;

        public TravelViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mRelative = itemView.findViewById(R.id.relative_policy);

            mRelative.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            TravelData travelData = mTravelList.get(getLayoutPosition());

            Intent intent = new Intent(mContext, TravelDetailsActivity.class);
            intent.putExtra("platenum", travelData.getmTravelPlateNo());
            intent.putExtra("chassisnum", travelData.getmTravelChassisNo());
            intent.putExtra("carmake", travelData.getmTravelCarMake());
            intent.putExtra("carValue", travelData.getmTravelCarValue());
            intent.putExtra("carYear", travelData.getmTravelCarYear());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public TravelClaimsAdapter(ArrayList<TravelData> mTravelList, Context context) {
        this.mTravelList = mTravelList;
        this.mContext = context;
        mCopyList = mTravelList;
    }

    @Override
    public TravelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.motor_item_list,
                parent, false);
        TravelViewHolder evh = new TravelViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(TravelViewHolder holder, int position) {
        TravelData currentItem = mTravelList.get(position);
        holder.mTextView1.setText(currentItem.getmTravelHolder());
        holder.mTextView2.setText(currentItem.getmTravelType());
    }

    @Override
    public int getItemCount() {
        return mTravelList.size();
    }

    public void filterList(ArrayList<TravelData> filteredList) {
        mTravelList = filteredList;
        notifyDataSetChanged();
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<TravelData> filteredResults = null;
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
                mTravelList = (ArrayList<TravelData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    protected ArrayList<TravelData> getFilteredResults(String constraint){
        ArrayList<TravelData> results = new ArrayList<>();
        String[] content = constraint.split(",");

        String value = content[0];
        String year = content[1];

        String answer = constraint.substring(constraint.indexOf("["),constraint.indexOf("]"));

        String replace = answer.replaceAll("^\\[|]$", "");
        String replace1 = replace.replace("]","");
        List<String> makeList = new ArrayList<String>(Arrays.asList(replace1.split(",")));

        for (TravelData item : mCopyList) {
            for (int counter = 0; counter < makeList.size(); counter++) {
                String make = makeList.get(counter);
                if (item.getmTravelCarYear().contains(year.trim())
                        && item.getmTravelCarMake().toLowerCase().contains(make.trim())) {
                    results.add(item);
                }
            }
        }
        return results;
    }
}