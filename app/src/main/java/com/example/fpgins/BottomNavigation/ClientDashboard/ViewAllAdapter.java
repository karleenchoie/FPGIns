package com.example.fpgins.BottomNavigation.ClientDashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fpgins.DataModel.FirstSlideMenuData;
import com.example.fpgins.R;
import com.example.fpgins.RoundedCornerImageView;

import java.util.List;

public class ViewAllAdapter extends BaseAdapter {

    private List<FirstSlideMenuData> mData;
    private LayoutInflater mInflater;
    private Context mContext;

    public ViewAllAdapter (List<FirstSlideMenuData> list, Context context){
        this.mData = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.item_dashboard_promo, null);
        RoundedCornerImageView roundedCornerImageView = convertView.findViewById(R.id.imageUploaded);
        TextView title = convertView.findViewById(R.id.tvTitle);
        TextView description = convertView.findViewById(R.id.tvDesc);

        title.setText(mData.get(position).getTitle());
        description.setText(mData.get(position).getDescription());

        Glide.with(convertView)
                .asBitmap()
                .placeholder(R.drawable.default_image)
                .load(mData.get(position).getId())
                .into(roundedCornerImageView);

        return convertView;
    }
}
