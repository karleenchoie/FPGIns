package com.example.fpgins.DataModel;

import android.graphics.drawable.Drawable;

public class MainClaimsData {
    private String title, details;
    private Drawable image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public MainClaimsData(String title, String details, Drawable image) {
        this.title = title;
        this.details = details;
        this.image = image;
    }

}
