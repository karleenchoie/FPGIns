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

import com.example.fpgins.BottomNavigation.AgentDashboard.AgentsActivities.HomeDetailsActivity;
import com.example.fpgins.DataModel.HomeData;
import com.example.fpgins.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeClaimsAdapter extends RecyclerView.Adapter<HomeClaimsAdapter.HomeViewHolder> {

    private ArrayList<HomeData> mHomeList;
    private ArrayList<HomeData> mCopyList;
    private Context mContext;

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView1;
        public TextView mTextView2;
        public RelativeLayout mRelative;

        public HomeViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mRelative = itemView.findViewById(R.id.relative_policy);

            mRelative.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            HomeData motorData = mHomeList.get(getLayoutPosition());

            Intent intent = new Intent(mContext, HomeDetailsActivity.class);
            intent.putExtra("platenum", motorData.getmHomePlateNo());
            intent.putExtra("chassisnum", motorData.getmHomeChassisNo());
            intent.putExtra("carmake", motorData.getmHomeCarMake());
            intent.putExtra("carValue", motorData.getmHomeCarValue());
            intent.putExtra("carYear", motorData.getmHomeCarYear());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public HomeClaimsAdapter(ArrayList<HomeData> mHomeList, Context context) {
        this.mHomeList = mHomeList;
        this.mContext = context;
        mCopyList = mHomeList;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.motor_item_list,
                parent, false);
        HomeViewHolder evh = new HomeViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        HomeData currentItem = mHomeList.get(position);
        holder.mTextView1.setText(currentItem.getmHomeHolder());
        holder.mTextView2.setText(currentItem.getmHomeType());
    }

    @Override
    public int getItemCount() {
        return mHomeList.size();
    }

    public void filterList(ArrayList<HomeData> filteredList) {
        mHomeList = filteredList;
        notifyDataSetChanged();
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<HomeData> filteredResults = null;
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
                mHomeList = (ArrayList<HomeData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    protected ArrayList<HomeData> getFilteredResults(String constraint){
        ArrayList<HomeData> results = new ArrayList<>();
        String[] content = constraint.split(",");


        String value = content[0];
        String year = content[1];

        String answer = constraint.substring(constraint.indexOf("["),constraint.indexOf("]"));

        String replace = answer.replaceAll("^\\[|]$", "");
        String replace1 = replace.replace("]","");
        List<String> makeList = new ArrayList<String>(Arrays.asList(replace1.split(",")));

        for (HomeData item : mCopyList) {
            for (int counter = 0; counter < makeList.size(); counter++) {
                String make = makeList.get(counter);
                if (item.getmHomeCarYear().contains(year.trim())
                        && item.getmHomeCarMake().toLowerCase().contains(make.trim())) {
                    results.add(item);
                }
            }
        }
        return results;
    }
}