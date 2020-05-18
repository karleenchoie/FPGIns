package com.example.fpgins.DataModel;

public class TravelData {

    private String mTravelHolder;
    private String mTravelType;
    private String mTravelPlateNo;
    private String mTravelChassisNo;
    private String mTravelCarMake;
    private String mTravelCarValue;
    private String mTravelCarYear;

    public TravelData(String mTravelHolder, String mTravelType, String mTravelPlateNo, String mTravelChassisNo, String mTravelCarMake, String mTravelCarValue, String mTravelCarYear) {
        this.mTravelHolder = mTravelHolder;
        this.mTravelType = mTravelType;
        this.mTravelPlateNo = mTravelPlateNo;
        this.mTravelChassisNo = mTravelChassisNo;
        this.mTravelCarMake = mTravelCarMake;
        this.mTravelCarValue = mTravelCarValue;
        this.mTravelCarYear = mTravelCarYear;
    }

    public String getmTravelHolder() {
        return mTravelHolder;
    }

    public void setmTravelHolder(String mTravelHolder) {
        this.mTravelHolder = mTravelHolder;
    }

    public String getmTravelType() {
        return mTravelType;
    }

    public void setmTravelType(String mTravelType) {
        this.mTravelType = mTravelType;
    }

    public String getmTravelPlateNo() {
        return mTravelPlateNo;
    }

    public void setmTravelPlateNo(String mTravelPlateNo) {
        this.mTravelPlateNo = mTravelPlateNo;
    }

    public String getmTravelChassisNo() {
        return mTravelChassisNo;
    }

    public void setmTravelChassisNo(String mTravelChassisNo) {
        this.mTravelChassisNo = mTravelChassisNo;
    }

    public String getmTravelCarMake() {
        return mTravelCarMake;
    }

    public void setmTravelCarMake(String mTravelCarMake) {
        this.mTravelCarMake = mTravelCarMake;
    }

    public String getmTravelCarValue() {
        return mTravelCarValue;
    }

    public void setmTravelCarValue(String mTravelCarValue) {
        this.mTravelCarValue = mTravelCarValue;
    }

    public String getmTravelCarYear() {
        return mTravelCarYear;
    }

    public void setmTravelCarYear(String mTravelCarYear) {
        this.mTravelCarYear = mTravelCarYear;
    }
}
