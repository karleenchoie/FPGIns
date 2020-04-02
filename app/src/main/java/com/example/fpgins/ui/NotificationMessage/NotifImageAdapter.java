package com.example.fpgins.ui.NotificationMessage;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.fpgins.R;

import java.util.ArrayList;

public class NotifImageAdapter extends PagerAdapter {

    private ArrayList<String>Images;
    private LayoutInflater inflater;
    private Context context;

    public NotifImageAdapter(ArrayList<String> images, Context context) {
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
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        Glide.with(context)
                .asBitmap()
                .load(Images.get(position))
                .into(imageView);

//        imageView.setImageResource(Images.get(position));
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
