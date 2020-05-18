package com.example.fpgins.DataModel;

public class HomeData {

    private String mHomeHolder;
    private String mHomeType;
    private String mHomePlateNo;
    private String mHomeChassisNo;
    private String mHomeCarMake;
    private String mHomeCarValue;
    private String mHomeCarYear;

    public HomeData(String mHomeHolder, String mHomeType, String mHomePlateNo, String mHomeChassisNo, String mHomeCarMake, String mHomeCarValue, String mHomeCarYear) {
        this.mHomeHolder = mHomeHolder;
        this.mHomeType = mHomeType;
        this.mHomePlateNo = mHomePlateNo;
        this.mHomeChassisNo = mHomeChassisNo;
        this.mHomeCarMake = mHomeCarMake;
        this.mHomeCarValue = mHomeCarValue;
        this.mHomeCarYear = mHomeCarYear;
    }

    public String getmHomeHolder() {
        return mHomeHolder;
    }

    public void setmHomeHolder(String mHomeHolder) {
        this.mHomeHolder = mHomeHolder;
    }

    public String getmHomeType() {
        return mHomeType;
    }

    public void setmHomeType(String mHomeType) {
        this.mHomeType = mHomeType;
    }

    public String getmHomePlateNo() {
        return mHomePlateNo;
    }

    public void setmHomePlateNo(String mHomePlateNo) {
        this.mHomePlateNo = mHomePlateNo;
    }

    public String getmHomeChassisNo() {
        return mHomeChassisNo;
    }

    public void setmHomeChassisNo(String mHomeChassisNo) {
        this.mHomeChassisNo = mHomeChassisNo;
    }

    public String getmHomeCarMake() {
        return mHomeCarMake;
    }

    public void setmHomeCarMake(String mHomeCarMake) {
        this.mHomeCarMake = mHomeCarMake;
    }

    public String getmHomeCarValue() {
        return mHomeCarValue;
    }

    public void setmHomeCarValue(String mHomeCarValue) {
        this.mHomeCarValue = mHomeCarValue;
    }

    public String getmHomeCarYear() {
        return mHomeCarYear;
    }

    public void setmHomeCarYear(String mHomeCarYear) {
        this.mHomeCarYear = mHomeCarYear;
    }
}
