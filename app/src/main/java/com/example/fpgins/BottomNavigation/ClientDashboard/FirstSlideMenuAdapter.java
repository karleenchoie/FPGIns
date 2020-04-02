package com.example.fpgins.BottomNavigation.ClientDashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fpgins.DataModel.FirstSlideMenuData;
import com.example.fpgins.R;
import com.example.fpgins.RoundedCornerImageView;
import com.example.fpgins.ui.NotificationMessage.NotifMessage;

import java.util.List;

public class FirstSlideMenuAdapter extends RecyclerView.Adapter<FirstSlideMenuAdapter.ViewHolder> {

    private List<FirstSlideMenuData> firstSlideMenuData;
    private Context context;

    public FirstSlideMenuAdapter(List<FirstSlideMenuData> list, Context context){
        this.firstSlideMenuData = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_promo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        FirstSlideMenuData data = firstSlideMenuData.get(position);
        Glide.with(context)
                .asBitmap()
                .placeholder(R.drawable.default_image)
                .load(data.getImage())
                .into(holder.roundedCornerImageView);
        holder.title.setText(data.getTitle());
        holder.description.setText(data.getDescription());

    }

    @Override
    public int getItemCount() {
        return firstSlideMenuData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RoundedCornerImageView roundedCornerImageView;
        public TextView title;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            roundedCornerImageView = itemView.findViewById(R.id.imageUploaded);
            title = itemView.findViewById(R.id.tvTitle);
            description = itemView.findViewById(R.id.tvDesc);

            roundedCornerImageView.setOnClickListener(this);
            title.setOnClickListener(this);
            description.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, NotifMessage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

}
