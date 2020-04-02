package com.example.fpgins.BottomNavigation.AgentDashboard.AgentFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.DataModel.ClientNameData;
import com.example.fpgins.R;

import java.util.ArrayList;

public class ClientNamesAdapter extends RecyclerView.Adapter<ClientNamesAdapter.ClientNameViewHolder> {
    private ArrayList<ClientNameData> mClientList;

    public static class ClientNameViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView4;

        public ClientNameViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.name);
            mTextView2 = itemView.findViewById(R.id.address);
            mTextView4 = itemView.findViewById(R.id.policy);

        }
    }

    public ClientNamesAdapter(ArrayList<ClientNameData> motorList) {
        mClientList = motorList;
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

        holder.mTextView1.setText(currentItem.getClientName());
        holder.mTextView2.setText(currentItem.getClientAddress());
        holder.mTextView4.setText(currentItem.getClientPolicy());
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