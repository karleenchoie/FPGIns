package com.example.fpgins.BottomNavigation.Claims;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.BottomNavigation.Products.ProductsFragment;
import com.example.fpgins.DataModel.MainClaimsData;
import com.example.fpgins.R;

import java.util.List;

public class MainClaimsAdapter extends RecyclerView.Adapter<MainClaimsAdapter.MyViewHolder> {

    private List<MainClaimsData> mainClaimsList;
    private Context mContext;
    public static String urlWeb = "https://www.fpgins.com/ph/products/motor";

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView details, title;
        public ImageView image;
        public LinearLayout linearLayout;


        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.txtSecond);
            details = view.findViewById(R.id.txtThird);
            image = view.findViewById(R.id.imgMainClaims);
            linearLayout = view.findViewById(R.id.linearMain);

            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (getLayoutPosition() == 0){
                urlWeb = "https://www.fpgins.com/ph/products/motor";
                Intent intent = new Intent(mContext, ProductsFragment.class);
                mContext.startActivity(intent);
            } if (getLayoutPosition() == 1){
                urlWeb = "https://www.fpgins.com/ph/products/travel";
                Intent intent = new Intent(mContext, ProductsFragment.class);
                mContext.startActivity(intent);
            } if (getLayoutPosition() == 2){
                urlWeb = "https://www.fpgins.com/ph/products/personal-accident";
                Intent intent = new Intent(mContext, ProductsFragment.class);
                mContext.startActivity(intent);
            } if (getLayoutPosition() == 3){
                urlWeb = "https://www.fpgins.com/ph/products/home/home-insurance";
                Intent intent = new Intent(mContext, ProductsFragment.class);
                mContext.startActivity(intent);
            } if (getLayoutPosition() == 4){
                urlWeb = "https://www.fpgins.com/ph/corporate/corporate-products";
                Intent intent = new Intent(mContext, ProductsFragment.class);
                mContext.startActivity(intent);
            }else {
                //do nothing
            }

        }
    }


    public MainClaimsAdapter(List<MainClaimsData> mainClaimsList, Context contex) {
        this.mainClaimsList = mainClaimsList;
        this.mContext = contex;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_claims_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MainClaimsData mainClaims = mainClaimsList.get(position);
        holder.title.setText(mainClaims.getTitle());
        holder.details.setText(mainClaims.getDetails());
        holder.image.setImageDrawable(mainClaims.getImage());
    }

    @Override
    public int getItemCount() {
        return mainClaimsList.size();
    }
}