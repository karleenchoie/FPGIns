package com.example.fpgins.DataModel;

public class MotorData {

    private String mPolicyHolder;
    private String mPolicyType;
    private String mPolicyPlateNo;
    private String mPolicyChassisNo;
    private String mPolicyCarMake;
    private String mPolicyCarValue;
    private String mPolicyCarYear;

    public MotorData(String mPolicyHolder, String mPolicyType, String mPolicyPlateNo, String mPolicyChassisNo, String mPolicyCarMake, String mPolicyCarValue, String mPolicyCarYear) {
        this.mPolicyHolder = mPolicyHolder;
        this.mPolicyType = mPolicyType;
        this.mPolicyPlateNo = mPolicyPlateNo;
        this.mPolicyChassisNo = mPolicyChassisNo;
        this.mPolicyCarMake = mPolicyCarMake;
        this.mPolicyCarValue = mPolicyCarValue;
        this.mPolicyCarYear = mPolicyCarYear;
    }

    public String getmPolicyHolder() {
        return mPolicyHolder;
    }

    public void setmPolicyHolder(String mPolicyHolder) {
        this.mPolicyHolder = mPolicyHolder;
    }

    public String getmPolicyType() {
        return mPolicyType;
    }

    public void setmPolicyType(String mPolicyType) {
        this.mPolicyType = mPolicyType;
    }

    public String getmPolicyPlateNo() {
        return mPolicyPlateNo;
    }

    public void setmPolicyPlateNo(String mPolicyPlateNo) {
        this.mPolicyPlateNo = mPolicyPlateNo;
    }

    public String getmPolicyChassisNo() {
        return mPolicyChassisNo;
    }

    public void setmPolicyChassisNo(String mPolicyChassisNo) {
        this.mPolicyChassisNo = mPolicyChassisNo;
    }

    public String getmPolicyCarMake() {
        return mPolicyCarMake;
    }

    public void setmPolicyCarMake(String mPolicyCarMake) {
        this.mPolicyCarMake = mPolicyCarMake;
    }

    public String getmPolicyCarValue() {
        return mPolicyCarValue;
    }

    public void setmPolicyCarValue(String mPolicyCarValue) {
        this.mPolicyCarValue = mPolicyCarValue;
    }

    public String getmPolicyCarYear() {
        return mPolicyCarYear;
    }

    public void setmPolicyCarYear(String mPolicyCarYear) {
        this.mPolicyCarYear = mPolicyCarYear;
    }
}
