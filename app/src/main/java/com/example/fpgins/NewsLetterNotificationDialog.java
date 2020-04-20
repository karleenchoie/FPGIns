package com.example.fpgins;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NewsLetterNotificationDialog extends Dialog {

    private ImageButton mImageButtonClose;
    private TextView mViewMore;
    private RoundedCornerImageView mImageButtonCoverBanner;
    private TextView mNews;
    private String mNotificationId;
    private String mAccountTypeId;
    private String mAccountId;

    private String title;
    private String date;
    private String time;
    private String pictures;
    private String content;
    private String link;


    public NewsLetterNotificationDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_news_letter);
        mImageButtonClose = findViewById(R.id.imageButtonClose);
        mImageButtonCoverBanner = findViewById(R.id.imageButtonCoverBanner);
        mNews = findViewById(R.id.information);
        mViewMore = findViewById(R.id.btn_viewMore);
    }

    public static class Builder {
        private NewsLetterNotificationDialog dialog;
        public Builder(Context context) {
            dialog = new NewsLetterNotificationDialog(context);
        }

        public Builder ids (String notificationId, String notifRecipientId, String accountId){
            dialog.setNotificationId(notificationId);
            dialog.setNotificationRecipientId(notifRecipientId);
            dialog.setAccountId(accountId);
            return this;
        }

        public Builder viewMoreInfo(String title, String date, String time, ArrayList<String> pictures, String content, String link){
            dialog.setTitle(title);
            dialog.setDate(date);
            dialog.setTime(time);
            dialog.setPictures(pictures);
            dialog.setContent(content);
            dialog.setLink(link);
            return this;
        }

        public Builder image (int value){
            dialog.setImageResource(value);
            return this;
        }

        public Builder image (Bitmap bitmap){
            dialog.setImageResource(bitmap);
            return this;
        }

        public Builder image (Context context, String url){
            dialog.setImageResource(context, url);
            return this;
        }

        public Builder newsText(int id){
            dialog.setNewsText(id);
            return this;
        }

        public Builder newsText(String value){
            dialog.setNewsText(value);
            return this;
        }

        public Builder viewMoreButton(ViewMoreOnClickListener listener){
            dialog.setViewMoreAction(listener);
            return this;
        }

        public Builder dismissButton(OnClickListener listener){
            dialog.setButtonCloseAction(listener);
            return this;
        }

        public NewsLetterNotificationDialog build() {
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            return dialog;
        }

    }

    public interface OnClickListener {
        void onClick(Dialog dialog, String notificationId, String notificationRecipientId, String accountId);
    }

    public interface ViewMoreOnClickListener {
        void onClick(Dialog dialog, String notificationId, String notificationRecipientId, String accountId,
                     String title, String date, String time, ArrayList<String> pictures, String content, String link);
    }

    public void setImageResource(int id){
        mImageButtonCoverBanner.setImageResource(id);
    }

    public void setImageResource(Bitmap image){
        mImageButtonCoverBanner.setImageBitmap(image);
    }

    public void setImageResource(Context context, String url){
        Glide.with(context)
                .asBitmap()
                .placeholder(R.drawable.default_image)
                .load(url)
                .into(mImageButtonCoverBanner);
    }

    public void setNewsText(String msg){
        mNews.setText(msg);
    }

    public void  setNewsText(int id){
        mNews.setText(id);
    }

    public void setViewMoreAction(final ViewMoreOnClickListener listener){
        mViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(NewsLetterNotificationDialog.this, getNotificationId(), getAccountTypeId(), getAccountId(),
                        getTitle(), getDate(), getTime(), getPictures(), getContent(), getLink());
            }
        });
    }

    public void setButtonCloseAction(final OnClickListener listener){
        mImageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(NewsLetterNotificationDialog.this, getNotificationId(), getAccountTypeId(), getAccountId());
            }
        });
    }

    public void setNotificationId(String mNotificationId) {
        this.mNotificationId = mNotificationId;
    }

    public void setNotificationRecipientId(String mAccountTypeId) {
        this.mAccountTypeId = mAccountTypeId;
    }

    public void setAccountId(String mAccountId) {
        this.mAccountId = mAccountId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPictures(ArrayList<String> pictures) {
        Gson gson = new Gson();
        String json = gson.toJson(pictures);
        this.pictures = json;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNotificationId() {
        return mNotificationId;
    }

    public String getAccountTypeId() {
        return mAccountTypeId;
    }

    public String getAccountId() {
        return mAccountId;
    }

    //essentials

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public ArrayList<String> getPictures() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(pictures, type);
    }

    public String getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }
}
