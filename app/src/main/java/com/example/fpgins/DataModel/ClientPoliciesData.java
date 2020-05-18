package com.example.fpgins.DataModel;

import java.util.ArrayList;

public class ClientPoliciesData {

    private String mPolicyNumber;
    private String mCertificateNo;
    private String mInceptionDate;
    private String mExpiryDate;
    private String mAssuredID;
    private String mAssuredName;
    private String mIntermediaryID;
    private ArrayList<VehicleDetailsData> mVehicleDetail;

    public ClientPoliciesData(String mPolicyNumber, String mCertificateNo,
                              String mInceptionDate, String mExpiryDate, String mAssuredID,
                              String mAssuredName, String mIntermediaryID,
                              ArrayList<VehicleDetailsData> mVehicleDetail) {

        this.mPolicyNumber = mPolicyNumber;
        this.mCertificateNo = mCertificateNo;
        this.mInceptionDate = mInceptionDate;
        this.mExpiryDate = mExpiryDate;
        this.mAssuredID = mAssuredID;
        this.mAssuredName = mAssuredName;
        this.mIntermediaryID = mIntermediaryID;
        this.mVehicleDetail = mVehicleDetail;
    }

    public String getmPolicyNumber() {
        return mPolicyNumber;
    }

    public void setmPolicyNumber(String mPolicyNumber) {
        this.mPolicyNumber = mPolicyNumber;
    }

    public String getmCertificateNo() {
        return mCertificateNo;
    }

    public void setmCertificateNo(String mCertificateNo) {
        this.mCertificateNo = mCertificateNo;
    }

    public String getmInceptionDate() {
        return mInceptionDate;
    }

    public void setmInceptionDate(String mInceptionDate) {
        this.mInceptionDate = mInceptionDate;
    }

    public String getmExpiryDate() {
        return mExpiryDate;
    }

    public void setmExpiryDate(String mExpiryDate) {
        this.mExpiryDate = mExpiryDate;
    }

    public String getmAssuredID() {
        return mAssuredID;
    }

    public void setmAssuredID(String mAssuredID) {
        this.mAssuredID = mAssuredID;
    }

    public String getmAssuredName() {
        return mAssuredName;
    }

    public void setmAssuredName(String mAssuredName) {
        this.mAssuredName = mAssuredName;
    }

    public String getmIntermediaryID() {
        return mIntermediaryID;
    }

    public void setmIntermediaryID(String mIntermediaryID) {
        this.mIntermediaryID = mIntermediaryID;
    }

    public ArrayList<VehicleDetailsData> getmVehicleDetail() {
        return mVehicleDetail;
    }

    public void setmVehicleDetail(ArrayList<VehicleDetailsData> mVehicleDetail) {
        this.mVehicleDetail = mVehicleDetail;
    }
}
