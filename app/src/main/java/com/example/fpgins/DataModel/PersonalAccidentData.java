package com.example.fpgins.DataModel;

public class PersonalAccidentData {

    private String mPAHolder;
    private String mPAType;
    private String mPAPlateNo;
    private String mPAChassisNo;
    private String mPACarMake;
    private String mPACarValue;
    private String mPACarYear;

    public PersonalAccidentData(String mPAHolder, String mPAType, String mPAPlateNo, String mPAChassisNo, String mPACarMake, String mPACarValue, String mPACarYear) {
        this.mPAHolder = mPAHolder;
        this.mPAType = mPAType;
        this.mPAPlateNo = mPAPlateNo;
        this.mPAChassisNo = mPAChassisNo;
        this.mPACarMake = mPACarMake;
        this.mPACarValue = mPACarValue;
        this.mPACarYear = mPACarYear;
    }

    public String getmPAHolder() {
        return mPAHolder;
    }

    public void setmPAHolder(String mPAHolder) {
        this.mPAHolder = mPAHolder;
    }

    public String getmPAType() {
        return mPAType;
    }

    public void setmPAType(String mPAType) {
        this.mPAType = mPAType;
    }

    public String getmPAPlateNo() {
        return mPAPlateNo;
    }

    public void setmPAPlateNo(String mPAPlateNo) {
        this.mPAPlateNo = mPAPlateNo;
    }

    public String getmPAChassisNo() {
        return mPAChassisNo;
    }

    public void setmPAChassisNo(String mPAChassisNo) {
        this.mPAChassisNo = mPAChassisNo;
    }

    public String getmPACarMake() {
        return mPACarMake;
    }

    public void setmPACarMake(String mPACarMake) {
        this.mPACarMake = mPACarMake;
    }

    public String getmPACarValue() {
        return mPACarValue;
    }

    public void setmPACarValue(String mPACarValue) {
        this.mPACarValue = mPACarValue;
    }

    public String getmPACarYear() {
        return mPACarYear;
    }

    public void setmPACarYear(String mPACarYear) {
        this.mPACarYear = mPACarYear;
    }
}
