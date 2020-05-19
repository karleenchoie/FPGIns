package com.example.fpgins.BottomNavigation.AgentDashboard.AgentFragments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.BottomNavigation.AgentDashboard.AgentsActivities.MotorDetailsActivity;
import com.example.fpgins.DataModel.ClientNameData;
import com.example.fpgins.R;

import java.util.ArrayList;

public class ClientNamesAdapter extends RecyclerView.Adapter<ClientNamesAdapter.ClientNameViewHolder> {

    private ArrayList<ClientNameData> mClientList;
    private Context mContext;

    public class ClientNameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView4;
        public RelativeLayout mRelative;

        public ClientNameViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.name);
            mTextView2 = itemView.findViewById(R.id.address);
            mTextView4 = itemView.findViewById(R.id.policy);
            mRelative = itemView.findViewById(R.id.relative_clientName);

            mRelative.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            ClientNameData clientNameData = mClientList.get(getLayoutPosition());
            Intent intent = new Intent(mContext, ClientNameDetails.class);
            intent.putExtra("adclientname", clientNameData.getName());
            intent.putExtra("adclientid", clientNameData.getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);

        }
    }

    public ClientNamesAdapter(ArrayList<ClientNameData> motorList, Context context) {
        mClientList = motorList;
        this.mContext = context;
    }

    @Override
    public ClientNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_name_item,
                parent, false);
        ClientNameViewHolder evh = new ClientNameViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ClientNameViewHolder holder, int position) {
        ClientNameData currentItem = mClientList.get(position);

        holder.mTextView1.setText(currentItem.getName());
        holder.mTextView2.setText(currentItem.getId());
    }

    @Override
    public int getItemCount() {
        return mClientList.size();
    }

    public void filterList(ArrayList<ClientNameData> filteredList) {
        mClientList = filteredList;
        notifyDataSetChanged();
    }
}