package com.example.fpgins.DataModel;

import android.graphics.drawable.Drawable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NotificationList {

    String imageDrawable;
    String title;
    String description;
    String date;

    String time;
    String pictures;
    String content;
    String link;

    public String getImageDrawable() {
        return imageDrawable;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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

    public NotificationList(String image, String title, String desc, String date, String time, ArrayList<String> pictures, String content, String link){
        this.imageDrawable = image;
        this.title = title;
        this.description = desc;
        this.date = date;
        this.time = time;

        Gson gson = new Gson();
        String json = gson.toJson(pictures);
        this.pictures = json;

        this.content = content;
        this.link = link;
    }
}
