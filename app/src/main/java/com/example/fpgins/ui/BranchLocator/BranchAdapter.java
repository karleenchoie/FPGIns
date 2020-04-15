package com.example.fpgins.ui.BranchLocator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.DataModel.BranchData;
import com.example.fpgins.R;

import java.util.List;


public class BranchAdapter extends RecyclerView.Adapter{

private static final int VIEW_TYPE_MAIN_OFFICE = 1;
private static final int VIEW_TYPE_BRANCH_OFFICE = 2;
private List<BranchData> branchDataList;
private Context mContext;

public BranchAdapter(List<BranchData> branchDataList, Context context) {
        this.branchDataList = branchDataList;
        this.mContext = context;
        }

@Override
public int getItemViewType(int position) {
        BranchData branchData = branchDataList.get(position);

        if (branchData.getOffice_type_name().equals("Head Office")){
        return VIEW_TYPE_MAIN_OFFICE;
        } else {
        return VIEW_TYPE_BRANCH_OFFICE;
        }
        }

@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MAIN_OFFICE){
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mainoffice_file, parent, false);
        return new HeadOfficeHolder(view);
        } else if (viewType == VIEW_TYPE_BRANCH_OFFICE){
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_branch_file, parent, false);
        return new BranchOfficeHolder(view);
        }

        return null;
        }

@Override
public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BranchData branchData = branchDataList.get(position);

        switch (holder.getItemViewType()){
        case VIEW_TYPE_MAIN_OFFICE:
        ((HeadOfficeHolder) holder).bind(branchData);
        break;
        case VIEW_TYPE_BRANCH_OFFICE:
        ((BranchOfficeHolder) holder).bind(branchData);
        break;
        }
        }

private class HeadOfficeHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView address;
    TextView number;
    TextView email;

    public HeadOfficeHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.txt_branchName);
        address = itemView.findViewById(R.id.txt_branchAddress);
        number = itemView.findViewById(R.id.txt_branchNumber);
        email = itemView.findViewById(R.id.txt_branchEmail);
    }

    void bind (BranchData branchData){
        name.setText(branchData.getOffice_country_name());
        address.setText(branchData.getAddress());
        number.setText(branchData.getContact_no());
        address.setText(branchData.getAddress());
    }
}

private class BranchOfficeHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView address;
    TextView number;
    TextView email;

    public BranchOfficeHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.txt_branchName2);
        address = itemView.findViewById(R.id.txt_branchAddress2);
        number = itemView.findViewById(R.id.txt_branchNumber2);
        email = itemView.findViewById(R.id.txt_branchEmail2);
    }

    void bind (BranchData branchData){
        name.setText(branchData.getOffice_country_name());
        address.setText(branchData.getAddress());
        number.setText(branchData.getContact_no());
        address.setText(branchData.getAddress());
    }
}

    @Override
    public int getItemCount() {
        return branchDataList.size();
    }
}

