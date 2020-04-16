package com.example.fpgins.ui.BranchLocator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.fpgins.DataModel.BranchData;
import com.example.fpgins.R;

import java.util.HashMap;
import java.util.List;

public class BranchExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<BranchData> headOffice;
    private HashMap<BranchData, List<BranchData>> branchOffice;


    public BranchExpandableListAdapter(List<BranchData> headOffice, HashMap<BranchData, List<BranchData>> branchOffice, Context context){
        this.mContext = context;
        this.headOffice = headOffice;
        this.branchOffice = branchOffice;
    }

    @Override
    public int getGroupCount() {
        return this.headOffice.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (this.branchOffice.get(this.headOffice.get(groupPosition)) == null){
            return 0;
        } else {
            return this.branchOffice.get(this.headOffice.get(groupPosition)).size();
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_branch_file, null);
        }

        TextView branchOffice = convertView.findViewById(R.id.txt_branchName2);
        TextView address = convertView.findViewById(R.id.txt_branchAddress2);
        TextView number = convertView.findViewById(R.id.txt_branchNumber2);
        TextView email = convertView.findViewById(R.id.txt_branchEmail2);

        branchOffice.setText(getChild(groupPosition, childPosition).name);
        address.setText(getChild(groupPosition, childPosition).address);
        number.setText(getChild(groupPosition, childPosition).contact_no + " Fax: " +
                getChild(groupPosition, childPosition).fax_no);
        email.setText(getChild(groupPosition, childPosition).email);

        return convertView;
    }

    @Override
    public BranchData getGroup(int groupPosition) {
        return this.headOffice.get(groupPosition);
    }

    @Override
    public BranchData getChild(int groupPosition, int childPosition) {
        return this.branchOffice.get(this.headOffice.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_mainoffice_file, null);
        }

        TextView headOffice = convertView.findViewById(R.id.txt_branchName);
        TextView address = convertView.findViewById(R.id.txt_branchAddress);
        TextView number = convertView.findViewById(R.id.txt_branchNumber);
        TextView email = convertView.findViewById(R.id.txt_branchEmail);

        headOffice.setText(getGroup(groupPosition).office_country_name);
        address.setText(getGroup(groupPosition).address);
        number.setText(getGroup(groupPosition).contact_no + " Fax: " + getGroup(groupPosition).fax_no);
        email.setText(getGroup(groupPosition).email);

        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
