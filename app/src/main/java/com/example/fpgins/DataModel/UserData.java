package com.example.fpgins.DataModel;

import android.content.SharedPreferences;

public class UserData {

    private final String ID = "id";
    private final String EMAIL = "email";
    private final String USERNAME = "username";
    private final String FIRST_NAME = "first_name";
    private final String LAST_NAME = "last_name";
    private final String ACCT_CODE = "acct_code";
    private final String PHOTO = "photo";
    private final String CONTACT_NO = "contact_no";

    private final String SELECTED_CLAIM_NO = "claim_no";

    private final boolean ACTIVITY_RUNNING = true;

    private final String DRAFTS_COUNT = "drafts_count";
    private final boolean DRAFTS = true;

    private SharedPreferences sharedPreferences;

    public UserData(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public String getId() {
        return sharedPreferences.getString(ID, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, null);
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME, null);
    }

    public String getFirstName() {
        return sharedPreferences.getString(FIRST_NAME, null);
    }

    public String getLastName() {
        return sharedPreferences.getString(LAST_NAME, null);
    }

    public String getAccountCode() {
        return sharedPreferences.getString(ACCT_CODE, null);
    }

    public String getDraftsCount(){
        return sharedPreferences.getString(DRAFTS_COUNT, null);
    }

    public String getSelectedClaimNo() {
        return sharedPreferences.getString(SELECTED_CLAIM_NO, null);
    }

    public boolean isDrafts() {
        return sharedPreferences.getBoolean(String.valueOf(DRAFTS), false);
    }

    public String getPhoto() {
        return sharedPreferences.getString(PHOTO, null);
    }

    public String getContactNo() {
        return sharedPreferences.getString(CONTACT_NO, null);
    }

    public boolean isActivityRunning(){
        return sharedPreferences.getBoolean(String.valueOf(ACTIVITY_RUNNING), false);
    }

    //Setting values
    public void setId(String value){
        sharedPreferences.edit()
                .putString(ID, value)
                .apply();
    }

    public void setEmail(String value){
        sharedPreferences.edit()
                .putString(EMAIL, value)
                .apply();
    }

    public void setUsername(String value){
        sharedPreferences.edit()
                .putString(USERNAME, value)
                .apply();
    }

    public void setFirstName(String value){
        sharedPreferences.edit()
                .putString(FIRST_NAME, value)
                .apply();
    }

    public void setLastName(String value){
        sharedPreferences.edit()
                .putString(LAST_NAME, value)
                .apply();
    }

    public void setAccountCode(String value){
        sharedPreferences.edit()
                .putString(ACCT_CODE, value)
                .apply();
    }

    public void setDraftsCount(String value){
        sharedPreferences.edit()
                .putString(DRAFTS_COUNT, value)
                .apply();
    }

    public void setDrafts(boolean value){
        sharedPreferences.edit()
                .putBoolean(String.valueOf(DRAFTS), value)
                .apply();
    }

    public void setSelectedClaimNo(String value){
        sharedPreferences.edit()
                .putString(SELECTED_CLAIM_NO, value)
                .apply();
    }

    public void isActivityRunning(boolean value){
        sharedPreferences.edit()
                .putBoolean(String.valueOf(ACTIVITY_RUNNING), value)
                .apply();
    }

    public void setPhoto(String value){
        sharedPreferences.edit()
                .putString(PHOTO, value)
                .apply();
    }

    public void setContactNo(String value){
        sharedPreferences.edit()
                .putString(CONTACT_NO, value)
                .apply();
    }

    public void clearData(){
        String clear = "";
        sharedPreferences.edit()
                .putString(ID, clear)
                .putString(EMAIL, clear)
                .putString(USERNAME, clear)
                .putString(FIRST_NAME, clear)
                .putString(LAST_NAME, clear)
                .putString(ACCT_CODE, clear)
                .putString(PHOTO, clear)
                .putString(CONTACT_NO, clear)
                .apply();
    }

    public void clearProfilePic(){
        sharedPreferences.edit()
                .putString(PHOTO, "");
    }
}
