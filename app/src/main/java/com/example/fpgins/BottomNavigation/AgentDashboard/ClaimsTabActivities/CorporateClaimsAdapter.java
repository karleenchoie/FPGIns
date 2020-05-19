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

import com.example.fpgins.BottomNavigation.AgentDashboard.AgentsActivities.CorporateDetailsActivity;
import com.example.fpgins.DataModel.CorporateData;
import com.example.fpgins.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CorporateClaimsAdapter extends RecyclerView.Adapter<CorporateClaimsAdapter.CorporateViewHolder> {

    private ArrayList<CorporateData> mCorpoList;
    private ArrayList<CorporateData> mCopyList;
    private Context mContext;

    public class CorporateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView1;
        public TextView mTextView2;
        public RelativeLayout mRelative;

        public CorporateViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mRelative = itemView.findViewById(R.id.relative_policy);

            mRelative.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            CorporateData motorData = mCorpoList.get(getLayoutPosition());

            Intent intent = new Intent(mContext, CorporateDetailsActivity.class);
            intent.putExtra("platenum", motorData.getmCorporatePlateNo());
            intent.putExtra("chassisnum", motorData.getmCorporateChassisNo());
            intent.putExtra("carmake", motorData.getmCorporateCarMake());
            intent.putExtra("carValue", motorData.getmCorporateCarValue());
            intent.putExtra("carYear", motorData.getmCorporateCarYear());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public CorporateClaimsAdapter(ArrayList<CorporateData> mCorpoList, Context context) {
        this.mCorpoList = mCorpoList;
        this.mContext = context;
        mCopyList = mCorpoList;
    }

    @Override
    public CorporateClaimsAdapter.CorporateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.motor_item_list,
                parent, false);
        CorporateClaimsAdapter.CorporateViewHolder evh = new CorporateClaimsAdapter.CorporateViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(CorporateClaimsAdapter.CorporateViewHolder holder, int position) {
        CorporateData currentItem = mCorpoList.get(position);
        holder.mTextView1.setText(currentItem.getmCorporateHolder());
        holder.mTextView2.setText(currentItem.getmCorporateType());
    }

    @Override
    public int getItemCount() {
        return mCorpoList.size();
    }

    public void filterList(ArrayList<CorporateData> filteredList) {
        mCorpoList = filteredList;
        notifyDataSetChanged();
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<CorporateData> filteredResults = null;
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
                mCorpoList = (ArrayList<CorporateData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    protected ArrayList<CorporateData> getFilteredResults(String constraint){
        ArrayList<CorporateData> results = new ArrayList<>();
        String[] content = constraint.split(",");

        String value = content[0];
        String year = content[1];

        String answer = constraint.substring(constraint.indexOf("["),constraint.indexOf("]"));

        String replace = answer.replaceAll("^\\[|]$", "");
        String replace1 = replace.replace("]","");
        List<String> makeList = new ArrayList<String>(Arrays.asList(replace1.split(",")));

        for (CorporateData item : mCopyList) {
            for (int counter = 0; counter < makeList.size(); counter++) {
                String make = makeList.get(counter);
                if (item.getmCorporateCarYear().contains(year.trim())
                        && item.getmCorporateCarMake().toLowerCase().contains(make.trim())) {
                    results.add(item);
                }
            }
        }
        return results;
    }
}