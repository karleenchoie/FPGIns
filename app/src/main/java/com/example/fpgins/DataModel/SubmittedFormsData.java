package com.example.fpgins.DataModel;

public class SubmittedFormsData {

    private String id, claim , policy, datesubmitted, status, transactionId, statusName;

    public String getId() {
        return id;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getDatesubmitted() {
        return datesubmitted;
    }

    public void setDatesubmitted(String datesubmitted) {
        this.datesubmitted = datesubmitted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getStatusName() {
        return statusName;
    }

    public SubmittedFormsData(String id, String claim, String policy, String datesubmitted, String status, String transactionId, String statusName) {
        this.id = id;
        this.claim = claim;
        this.policy = policy;
        this.datesubmitted = datesubmitted;
        this.status = status;
        this.transactionId = transactionId;
        this.statusName = statusName;
    }
}
