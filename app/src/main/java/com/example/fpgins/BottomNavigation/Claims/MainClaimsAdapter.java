package com.example.fpgins.BottomNavigation.Claims;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpgins.BottomNavigation.Partners.PartnersAdapter;
import com.example.fpgins.BottomNavigation.Products.ProductsFragment;
import com.example.fpgins.DataModel.MainClaimsData;
import com.example.fpgins.DataModel.PartnersData;
import com.example.fpgins.DataModel.ProductsData;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Network.ImageUploaderUtility.DownloadImageTask;
import com.example.fpgins.R;

import java.util.List;

public class MainClaimsAdapter extends RecyclerView.Adapter<MainClaimsAdapter.MyViewHolder> {

//    private List<MainClaimsData> mainClaimsList;
//    private Context mContext;
//    public static String urlWeb = "https://www.fpgins.com/ph/products/motor";

    private List<ProductsData> productsDataList;
    private Context mContext;

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

            ProductsData productsData = productsDataList.get(getLayoutPosition());
            Intent intent = new Intent(mContext, ProductsFragment.class);
            intent.putExtra("product_url", productsData.getLink());
            mContext.startActivity(intent);

//            if (getLayoutPosition() == 0){
//                urlWeb = "https://www.fpgins.com/ph/products/motor";
//                Intent intent = new Intent(mContext, ProductsFragment.class);
//                intent.putExtra("product_url", urlWeb);
//                mContext.startActivity(intent);
//            } if (getLayoutPosition() == 1){
//                urlWeb = "https://www.fpgins.com/ph/products/travel";
//                Intent intent = new Intent(mContext, ProductsFragment.class);
//                intent.putExtra("product_url", urlWeb);
//                mContext.startActivity(intent);
////            } if (getLayoutPosition() == 2){
////                urlWeb = "https://www.google.com/";
////                Intent intent = new Intent(mContext, ProductsFragment.class);
////                intent.putExtra("product_url", urlWeb);
////                mContext.startActivity(intent);
//            }else {
//                //do nothing
//            }

        }
    }


    public MainClaimsAdapter(List<ProductsData> productsData, Context context) {
        this.productsDataList = productsData;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_claims_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainClaimsAdapter.MyViewHolder holder, int position) {
        ProductsData productsData = productsDataList.get(position);
        holder.title.setText(productsData.getTitle());
        holder.details.setText(productsData.getOffice_country_name());
        String partnersPic = productsData.getCompanyPic().trim();

        UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(mContext));
        String url = userData.getPhoto();
//
//        Glide.with(mContext)
//                .asBitmap()
//                .placeholder(R.drawable.default_image)
//                .load(partnersData.getCompanyPic())
//                .into(holder.image);
        new DownloadImageTask(holder.image,partnersPic);
    }

    @Override
    public int getItemCount() {
        return productsDataList.size();
    }
}