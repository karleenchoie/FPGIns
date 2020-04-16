package com.example.fpgins.BottomNavigation.ClientDashboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.example.fpgins.DataModel.BannerData;
import com.example.fpgins.Network.ImageUploaderUtility.DownloadImageTask;
import com.example.fpgins.R;
import com.example.fpgins.RoundedCornerImageView;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {

    private ArrayList<String> Images;
    private ArrayList<String> Links;
    private LayoutInflater inflater;
    private Context context;
    private String url;

    public BannerAdapter(ArrayList<String> images, ArrayList<String> links, Context context) {
        Images = images;
        Links = links;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return Images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View imageLayout = inflater.inflate(R.layout.notification_imageslider, container, false);

        assert imageLayout != null;
        RoundedCornerImageView imageView = imageLayout.findViewById(R.id.image);
        url = Links.get(position).trim();
        new DownloadImageTask(imageView, Images.get(position));
        new DownloadImageTask(imageView, Images.get(position));
        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(browserIntent);
                }catch (Exception e){
                    Toast.makeText(context, "PLEASE CHECK URL IF VALID", Toast.LENGTH_LONG).show();
                }
            }
        });

        container.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

    @Nullable
    @Override
    public Parcelable saveState() {
        return null;
    }
}
