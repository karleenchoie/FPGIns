package com.example.fpgins.DataModel;

import android.graphics.Bitmap;

public class Images {

    String id;
    String custCode;
    Bitmap bitmap;
    String imagename;
    String dateTaken;

    public String getId() {
        return id;
    }

    public String getCustCode() {
        return custCode;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }


    public String getDateTaken() {
        return dateTaken;
    }

    public String getImageName() {
        return imagename;
    }

    public Images(String id, String custCode, Bitmap bitmap, String imagename, String dateTaken){
        this.id = id;
        this.custCode = custCode;
        this.bitmap = bitmap;
        this.imagename = imagename;
        this.dateTaken = dateTaken;
    }
}
