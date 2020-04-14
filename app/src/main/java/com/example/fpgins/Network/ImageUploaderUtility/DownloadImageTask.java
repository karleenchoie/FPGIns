package com.example.fpgins.Network.ImageUploaderUtility;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fpgins.R;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DownloadImageTask {

    private ImageView[] images;
    private int downloadingImage = R.drawable.default_image_banner;
    private int oopsImage = R.drawable.default_image_banner;

    public DownloadImageTask(ImageView imageView,  String url, int resImageError, int resImageLoading) {
        oopsImage = resImageError;
        downloadingImage = resImageLoading;
        downloadImageTask(imageView, url);
    }

    public DownloadImageTask(ImageView imageView, String url) {
        downloadImageTask(imageView, url);
    }

    public DownloadImageTask(ImageView imageView, String url, String path) {
        downloadImageTask_save_pic(imageView, url, path);
    }

    private void downloadImageTask(final ImageView imageView, String url) {
        try {
            String encodeUrl = encodeFileName(url);

            if (encodeUrl == null || encodeUrl.length() == 0) {
                encodeUrl = "errorPath";
            }

            Glide.with(imageView.getContext())
                    .asBitmap()
                    .load(encodeUrl)
                    .error(oopsImage)
                    .placeholder(downloadingImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(imageView);
        } catch (Exception e) {
//            Log.e("DownloadImageTask","download image fail!!!");
        }
    }
    private void downloadImageTask_save_pic(final ImageView imageView, String url, final String Path) {
        try {
            String encodeUrl = encodeFileName(url);

            if (encodeUrl == null || encodeUrl.length() == 0) {
                encodeUrl = "errorPath";
            }

            if(encodeUrl.contains(".png")||encodeUrl.contains(".jpg")) {

                Glide.with(imageView.getContext())
                        .asBitmap()
                        .load(encodeUrl)
                        .error(oopsImage)
                        .placeholder(downloadingImage)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            try {
                                imageView.setImageBitmap(resource);
                                //File f = Environment.getDownloadCacheDirectory();
                                String path = Path ;

                                FileOutputStream out = new FileOutputStream(path);
                                resource.compress(Bitmap.CompressFormat.PNG, 100, out);
                                out.flush();
                                out.close();
                            } catch(Exception e) {
                                Log.e("picE",e.toString());
                            }
                        }
                        }
                );
            }
            else
            {
                Glide.with(imageView.getContext())
                        .asBitmap()
                        .load(encodeUrl)
                        .error(oopsImage)
                        .placeholder(downloadingImage)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter().into(imageView);
            }

        } catch (Exception e) {
            Log.e("DownloadImageTask","download image fail!!!");
        }
    }



    //for PorfileDialog to Download user profile image, just download image once for background and head
    // add new function, parameters ImageView Array to set same image
    public DownloadImageTask(String url, ImageView... imageViews) {
        try {
            String encodeUrl = encodeFileName(url);

            if (encodeUrl == null || encodeUrl.length() == 0) {
                encodeUrl = "errorPath";
            }

            if ( imageViews == null )
                return;

            images = imageViews;

            Glide.with(imageViews[0].getContext())
                    .asBitmap()
                    .load(encodeUrl)
                    .error(oopsImage)
                    .placeholder(downloadingImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(target);
        } catch (Exception e) {
//            Log.e("DownloadImageTask","download image fail!!!");
        }
    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            for (ImageView img : images)
            {
                img.setImageBitmap(resource);
            }
        }
    };

    private String encodeFileName(String url) {
        String[] data = url.split("/");

        String encodedUrl = "";

        for(int i = 0; i < data.length; i++) {
            if(i == data.length - 1) {
                try {
                    encodedUrl += URLEncoder.encode(data[i], "utf-8").replaceAll("\\+","%20").replace("%25","%");//" "(空格)encode→"+" so 再轉成"%20"， "%"encond→"%25" so 再轉成"%"
                } catch (UnsupportedEncodingException e) {
                    encodedUrl += data[i];
                }
            }
            else {
                encodedUrl += data[i] + "/";
            }
        }
        return encodedUrl;
    }
}
