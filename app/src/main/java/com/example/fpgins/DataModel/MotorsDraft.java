package com.example.fpgins.DataModel;

import android.graphics.Bitmap;

public class MotorsDraft {

    String claims_motor_id;
    String email;
    String fullName;
    String mobileNo;
    String locationAddress;
    String longitude;
    String lattitude;
    String dateTime;
    Bitmap imageTile;
    String remarks;
    String imageList;
    String type;
    String policyNo;
    String plateNo;
    String conductionStickerNo;


    public String getClaims_motor_id() {
        return claims_motor_id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLattitude() {
        return lattitude;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Bitmap getImageTile() {
        return imageTile;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getType() {
        return type;
    }

    public String getImageList() {
        return imageList;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public String getConductionStickerNo() {
        return conductionStickerNo;
    }


    public MotorsDraft(String id, String email, String fullName, String mobileNo, String locationAddress, String longitude,
                       String lattitude, String dateTime, Bitmap imageTile, String remarks, String imageList, String type,
                       String policyNo, String plateNo, String conductionStickerNo){
        this.claims_motor_id = id;
        this.email = email;
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.locationAddress = locationAddress;
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.dateTime = dateTime;
        this.imageTile = imageTile;
        this.remarks = remarks;
        this.imageList = imageList;
        this.type = type;
        this.policyNo = policyNo;
        this.plateNo = plateNo;
        this.conductionStickerNo = conductionStickerNo;
    }

}
