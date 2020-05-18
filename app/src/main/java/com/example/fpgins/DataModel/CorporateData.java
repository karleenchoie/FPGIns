package com.example.fpgins.DataModel;

public class CorporateData {

    private String mCorporateHolder;
    private String mCorporateType;
    private String mCorporatePlateNo;
    private String mCorporateChassisNo;
    private String mCorporateCarMake;
    private String mCorporateCarValue;
    private String mCorporateCarYear;

    public CorporateData(String mCorporateHolder, String mCorporateType, String mCorporatePlateNo, String mCorporateChassisNo, String mCorporateCarMake, String mCorporateCarValue, String mCorporateCarYear) {
        this.mCorporateHolder = mCorporateHolder;
        this.mCorporateType = mCorporateType;
        this.mCorporatePlateNo = mCorporatePlateNo;
        this.mCorporateChassisNo = mCorporateChassisNo;
        this.mCorporateCarMake = mCorporateCarMake;
        this.mCorporateCarValue = mCorporateCarValue;
        this.mCorporateCarYear = mCorporateCarYear;
    }

    public String getmCorporateHolder() {
        return mCorporateHolder;
    }

    public void setmCorporateHolder(String mCorporateHolder) {
        this.mCorporateHolder = mCorporateHolder;
    }

    public String getmCorporateType() {
        return mCorporateType;
    }

    public void setmCorporateType(String mCorporateType) {
        this.mCorporateType = mCorporateType;
    }

    public String getmCorporatePlateNo() {
        return mCorporatePlateNo;
    }

    public void setmCorporatePlateNo(String mCorporatePlateNo) {
        this.mCorporatePlateNo = mCorporatePlateNo;
    }

    public String getmCorporateChassisNo() {
        return mCorporateChassisNo;
    }

    public void setmCorporateChassisNo(String mCorporateChassisNo) {
        this.mCorporateChassisNo = mCorporateChassisNo;
    }

    public String getmCorporateCarMake() {
        return mCorporateCarMake;
    }

    public void setmCorporateCarMake(String mCorporateCarMake) {
        this.mCorporateCarMake = mCorporateCarMake;
    }

    public String getmCorporateCarValue() {
        return mCorporateCarValue;
    }

    public void setmCorporateCarValue(String mCorporateCarValue) {
        this.mCorporateCarValue = mCorporateCarValue;
    }

    public String getmCorporateCarYear() {
        return mCorporateCarYear;
    }

    public void setmCorporateCarYear(String mCorporateCarYear) {
        this.mCorporateCarYear = mCorporateCarYear;
    }
}
