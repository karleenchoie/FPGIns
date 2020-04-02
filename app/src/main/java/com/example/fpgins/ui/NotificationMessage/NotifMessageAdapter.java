package com.example.fpgins.ui.NotificationMessage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fpgins.DataModel.NotificationList;
import com.example.fpgins.R;

import java.util.List;

public class NotifMessageAdapter extends RecyclerView.Adapter<NotifMessageAdapter.ViewHolder> {

    private List<NotificationList> mData;
    private Context mContext;
    private NotificationList mNotifData;

    public NotifMessageAdapter(List<NotificationList> list, Context context){
        mData = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mNotifData = mData.get(position);

//        holder.imageView.setImageDrawable(mNotifData.getImageDrawable());

        Glide.with(mContext)
                .asDrawable()
                .placeholder(R.drawable.default_image)
                .load(mNotifData.getImageDrawable())
                .into(holder.imageView);

        holder.title.setText(mNotifData.getTitle());
        holder.description.setText(mNotifData.getDescription());
        holder.date.setText(mNotifData.getDate());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView title;
        public TextView description;
        public TextView date;
        public LinearLayout linearLayout;
        public LinearLayout clickLinear;
        public LinearLayout lh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.logo);
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            description = (TextView) itemView.findViewById(R.id.tvDesc);
            date = (TextView) itemView.findViewById(R.id.tvDate);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.topLinearLayout);
            clickLinear = (LinearLayout) itemView.findViewById(R.id.linearClicked);
            lh = (LinearLayout) itemView.findViewById(R.id.lh);

            linearLayout.setOnClickListener(this);
            clickLinear.setOnClickListener(this);
            lh.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            NotificationList data = mData.get(getLayoutPosition());

            Intent intent = new Intent(mContext, NotifMessage.class);

            intent.putExtra("title", data.getTitle());
            intent.putExtra("date", data.getDate());
            intent.putExtra("time", data.getTime());
            intent.putExtra("pictures", data.getPictures());
            intent.putExtra("content", data.getContent());
            intent.putExtra("link", data.getLink());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            mContext.startActivity(intent);
        }
    }

}
