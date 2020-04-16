package com.example.fpgins.ui.BranchLocator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.example.fpgins.R;

public class BranchLocator extends ExpandableListActivity {
    private static final String arrGroupElements[] = { "Philippines", "Indonesia", "Thailand"};

    private static final String arrChildElements[][] = {
            { "Philippines A","Philippines B", "Philippines C" },
            { "Indonesia A","Indonesia B" },
            { "Thailand A"},
            { "Details4 A","Details4 B", "Details4 C" },
            { "Details5 A","Details5 B", "Details5 C" },
            { "Details6 A","Details6 B", "Details6 C" },
            { "Details7" },
            { "Details8" },
            { "Details9" }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_locator);

        setListAdapter(new ExpandableListAdapter(this));
    }
    public class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context myContext;
        public ExpandableListAdapter(Context context) {
            myContext = context;
        }
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) myContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(
                        R.layout.item_branch_file, null);
            }
            TextView yourSelection = (TextView) convertView
                    .findViewById(R.id.txt_branchName2);
            yourSelection
                    .setText(arrChildElements[groupPosition][childPosition]);
            return convertView;
        }
        @Override
        public int getChildrenCount(int groupPosition) {
            return arrChildElements[groupPosition].length;
        }
        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }
        @Override
        public int getGroupCount() {
            return arrGroupElements.length;
        }
        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) myContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(
                        R.layout.item_mainoffice_file, null);
            }
            TextView groupName = (TextView) convertView
                    .findViewById(R.id.txt_branchName);
            groupName.setText(arrGroupElements[groupPosition]);
            return convertView;
        }
        @Override
        public boolean hasStableIds() {
            return false;
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
