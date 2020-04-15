package com.example.fpgins.BottomNavigation.ClientDashboard;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.example.fpgins.Network.ImageUploaderUtility.DownloadImageTask;
import com.example.fpgins.R;
import com.example.fpgins.RoundedCornerImageView;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {

    private ArrayList<String> Images;
    private LayoutInflater inflater;
    private Context context;

    public BannerAdapter(ArrayList<String> images, Context context) {
        Images = images;
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
    public Object instantiateItem(ViewGroup container, int position) {
        View imageLayout = inflater.inflate(R.layout.notification_imageslider, container, false);

        assert imageLayout != null;
        RoundedCornerImageView imageView = imageLayout.findViewById(R.id.image);
        new DownloadImageTask(imageView, Images.get(position));
        new DownloadImageTask(imageView, Images.get(position));

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
