package com.example.fpgins.BottomNavigation.Partners;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fpgins.BottomNavigation.Products.ProductsFragment;
import com.example.fpgins.DataModel.PartnersData;
import com.example.fpgins.Network.ImageUploaderUtility.DownloadImageTask;
import com.example.fpgins.R;

import java.util.List;

public class PartnersAdapter extends RecyclerView.Adapter<PartnersAdapter.MyViewHolder> {

    private List<PartnersData> partnersDataList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView details, title;
        public ImageView image;
        public LinearLayout linearLayout;


        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.txtComapany);
            details = view.findViewById(R.id.txtDesc);
            image = view.findViewById(R.id.imgPhoto);
            linearLayout = view.findViewById(R.id.linearMain);

            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            PartnersData partnersData = partnersDataList.get(getLayoutPosition());

            Intent intent = new Intent(mContext, PartnersPage.class);
            intent.putExtra("url", partnersData.getLink());
            mContext.startActivity(intent);
        }
    }


    public PartnersAdapter(List<PartnersData> partnersDataList, Context contex) {
        this.partnersDataList = partnersDataList;
        this.mContext = contex;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partners_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PartnersData partnersData = partnersDataList.get(position);
        holder.title.setText(partnersData.getTitle());
        holder.details.setText(partnersData.getContent());
        Glide.with(mContext)
                .asBitmap()
                .placeholder(R.drawable.default_image)
                .load(partnersData.getCompanyPic())
                .into(holder.image);
//        new DownloadImageTask(holder.image, partnersData.getCompanyPic());
    }

    @Override
    public int getItemCount() {
        return partnersDataList.size();
    }
}