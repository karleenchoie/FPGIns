package com.example.fpgins.DataModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BannerData {
    private String id;
    private String title;
    private String link;
    private String date;
    private String banner_location_name;
    private String pictures;

    public BannerData(String id, String title, String link, String date, String banner_location_name, ArrayList<String> pictures) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.date = date;
        this.banner_location_name = banner_location_name;

        Gson gson = new Gson();
        String json = gson.toJson(pictures);
        this.pictures = json;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBanner_location_name() {
        return banner_location_name;
    }

    public void setBanner_location_name(String banner_location_name) {
        this.banner_location_name = banner_location_name;
    }

    public ArrayList<String> getPictures() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(pictures, type);
    }

}
