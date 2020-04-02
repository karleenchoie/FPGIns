package com.example.fpgins.DataModel;

public class FirstSlideMenuData {
    private String image;
    private String title;
    private String description;

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public FirstSlideMenuData(String image, String title, String description){
        this.image = image;
        this.title = title;
        this.description = description;
    }
}
