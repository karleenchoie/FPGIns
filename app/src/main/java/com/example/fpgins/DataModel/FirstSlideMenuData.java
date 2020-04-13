package com.example.fpgins.DataModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FirstSlideMenuData {
    private String id;
    private String title;
    private String description;
    private String link;
    private String postDate;
    private String categoryName;
    private String pictures;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<String> getPictures() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(pictures, type);
    }

    public FirstSlideMenuData(String id, String title, String description, String link, String postDate, String categoryName,
                              ArrayList<String> pictures){
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.postDate = postDate;
        this.categoryName = categoryName;

        Gson gson = new Gson();
        String json = gson.toJson(pictures);
        this.pictures = json;
    }
}
