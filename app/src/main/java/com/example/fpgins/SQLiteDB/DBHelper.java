package com.example.fpgins.SQLiteDB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import com.example.fpgins.DataModel.Images;
import com.example.fpgins.DataModel.MotorsDraft;

import java.util.ArrayList;

public class DBHelper {

    public static final String ID = "id";
    public static final String CUST_CODE = "customerCode";
    public static final String IMG_FILE_NAME = "image";
    public static final String IMG_NAME = "imgname";
    public static final String DATE_TAKEN = "date";

    //MOTOR
    public static final String CLAIMS_MOTOR_ID = "motor_id";
    public static final String EMAIL = "email";
    public static final String FULL_NAME = "full_name";
    public static final String MOBILE_NO = "mobile_no";
    public static final String LOCATION_ADDRESS = "address";
    public static final String LONGITUDE = "longitude";
    public static final String LATTITUDE = "lattitude";
    public static final String DATE_TIME = "date_time";
    public static final String IMAGE_TILE = "image_tile";
    public static final String REMARKS = "remarks";
    public static final String IMAGES_LIST_NAME = "images_list_name";
    public static final String TYPE = "type";
    public static final String CLAIM_NO = "claim_no";
    public static final String PLATE_NO = "plate_no";
    public static final String CONDUCTION_STICKER_NO = "conduction_sticker_no";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "ImagesDB.db";
    private static final int DATABASE_VERSION = 1;

    //IMAGE
    private static final String IMAGES_TABLE = "images_table";

    //MOTORS
    private static final String CLAIMS_MOTOR_TABLE = "claim_motor_table";

    private final Context mCtx;

    private static final String CREATE_IMAGES_TABLE = "create table "
            + IMAGES_TABLE + " (" + ID
            + " integer primary key autoincrement, "
            + CUST_CODE + " text not null, "
            + IMG_FILE_NAME + " blob not null, "
            + IMG_NAME + " text not null unique, "
            + DATE_TAKEN + " text not null );";

    private static final String CREATE_CLAIMS_MOTOR_TABLE = "create table "
            + CLAIMS_MOTOR_TABLE + " (" + CLAIMS_MOTOR_ID
            + " integer primary key autoincrement, "
            + EMAIL + " text not null, "
            + FULL_NAME + " text not null, "
            + MOBILE_NO + " text not null, "
            + LOCATION_ADDRESS + " text not null, "
            + LONGITUDE + " text not null, "
            + LATTITUDE + " text not null, "
            + DATE_TIME + " text not null, "
            + IMAGE_TILE + " blob not null, "
            + REMARKS + " text not null, "
            + IMAGES_LIST_NAME + " text not null unique, "
            + CLAIM_NO + " text not null, "
            + PLATE_NO + " text not null, "
            + CONDUCTION_STICKER_NO + " text not null, "
            + TYPE + " text not null );";



    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_IMAGES_TABLE);
            db.execSQL(CREATE_CLAIMS_MOTOR_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + IMAGES_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + CLAIMS_MOTOR_TABLE);
            onCreate(db);
        }
    }

    public void Reset() {
        mDbHelper.onUpgrade(this.mDb, 1, 1);
    }

    public DBHelper(Context ctx) {
        mCtx = ctx;
        mDbHelper = new DatabaseHelper(mCtx);
    }

    public DBHelper open() throws SQLException {
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public boolean inserImagesDetails(Bitmap image, String custCode, String date, String imageName){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CUST_CODE, custCode);
        cv.put(IMG_FILE_NAME, Utility.getBytes(image));
        cv.put(IMG_NAME, imageName);
        cv.put(DATE_TAKEN, date);
        long result = db.insert(IMAGES_TABLE, null, cv);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean insertMotorDetails(String email, String fullName, String mobileNo, String locationAddress, String longitude,
                                      String lattitude, String dateTime, Bitmap imageTile, String remarks, String draftCount, String type,
                                      String claimNo, String plateNo, String conductionStickerNo){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EMAIL, email);
        cv.put(FULL_NAME, fullName);
        cv.put(MOBILE_NO, mobileNo);
        cv.put(LOCATION_ADDRESS, locationAddress);
        cv.put(LONGITUDE, longitude);
        cv.put(LATTITUDE, lattitude);
        cv.put(DATE_TIME, dateTime);
        cv.put(IMAGE_TILE, Utility.getBytes(imageTile));
        cv.put(REMARKS, remarks);
        cv.put(IMAGES_LIST_NAME, draftCount);
        cv.put(TYPE, type);
        cv.put(CLAIM_NO, claimNo);
        cv.put(PLATE_NO, plateNo);
        cv.put(CONDUCTION_STICKER_NO, conductionStickerNo);

        long result = db.insert(CLAIMS_MOTOR_TABLE, null, cv);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<MotorsDraft> retrieveAllMotorDraftsDetails(String userEmail) throws SQLException {
        mDb = mDbHelper.getReadableDatabase();
        String sqlQuery = "SELECT * FROM " + CLAIMS_MOTOR_TABLE + " WHERE " + EMAIL + "=" + "'" + userEmail + "'";
        Cursor cursor = mDb.rawQuery(sqlQuery, null);
        ArrayList<MotorsDraft> motorsDraftsList = new ArrayList<MotorsDraft>();

        if (cursor.moveToFirst()){
            do {
                String id = cursor.getString(cursor.getColumnIndex(CLAIMS_MOTOR_ID));
                String email = cursor.getString(cursor.getColumnIndex(EMAIL));
                String fullName = cursor.getString(cursor.getColumnIndex(FULL_NAME));
                String mobileNo = cursor.getString(cursor.getColumnIndex(MOBILE_NO));
                String locationAddress = cursor.getString(cursor.getColumnIndex(LOCATION_ADDRESS));
                String longitude = cursor.getString(cursor.getColumnIndex(LONGITUDE));
                String lattitude = cursor.getString(cursor.getColumnIndex(LATTITUDE));
                String dateTime = cursor.getString(cursor.getColumnIndex(DATE_TIME));
                byte[] imageTile = cursor.getBlob(cursor.getColumnIndex(IMAGE_TILE));
                String remarks = cursor.getString(cursor.getColumnIndex(REMARKS));
                String imageList = cursor.getString(cursor.getColumnIndex(IMAGES_LIST_NAME));
                String type = cursor.getString(cursor.getColumnIndex(TYPE));
                String claimNo = cursor.getString(cursor.getColumnIndex(CLAIM_NO));
                String plateNo = cursor.getString(cursor.getColumnIndex(PLATE_NO));
                String conductionStickerNo = cursor.getString(cursor.getColumnIndex(CONDUCTION_STICKER_NO));
                motorsDraftsList.add(new MotorsDraft(id, email, fullName, mobileNo, locationAddress, longitude, lattitude,
                                    dateTime, Utility.getPhoto(imageTile), remarks, imageList, type, claimNo, plateNo, conductionStickerNo));
            } while (cursor.moveToNext());
        }

        return motorsDraftsList;
    }

    public boolean deleteMotorDraft(String id, String imageList){
        mDb = mDbHelper.getWritableDatabase();
        return mDb.delete(CLAIMS_MOTOR_TABLE, CLAIMS_MOTOR_ID + "=? AND " + IMAGES_LIST_NAME + "=?", new String[]{id, imageList}) > 0;
    }

    public boolean updateMotorDraftRow(String id, String claimNo, String plateNo, String conductionNo,  String remarks, String address, String longitude, String lattitude){
        mDb = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CLAIM_NO, claimNo);
        cv.put(PLATE_NO, plateNo);
        cv.put(CONDUCTION_STICKER_NO, conductionNo);
        cv.put(LOCATION_ADDRESS, address);
        cv.put(LONGITUDE, longitude);
        cv.put(LATTITUDE, lattitude);
        cv.put(REMARKS, remarks);
        return mDb.update(CLAIMS_MOTOR_TABLE, cv, CLAIMS_MOTOR_ID + "=" + id, null) > 0;
    }

    public ArrayList<Images> retrieveAllImagesDetails(String draft) throws SQLException {

        mDb = mDbHelper.getReadableDatabase();
        //It should have a query for images to display by CUST_CODE
        String sqlQuery = "SELECT * FROM " + IMAGES_TABLE + " WHERE " + CUST_CODE + "= '" + draft + "'";


        Cursor cursor = mDb.rawQuery(sqlQuery, null);
        ArrayList<Images> imagesList = new ArrayList<Images>();
        if (cursor.moveToFirst()) {

            do {
                String id = cursor.getString(cursor.getColumnIndex(ID));
                String custCode = cursor.getString(cursor.getColumnIndex(CUST_CODE));
                byte[] blob = cursor.getBlob(cursor.getColumnIndex(IMG_FILE_NAME));
                String imageName = cursor.getString(cursor.getColumnIndex(IMG_NAME));
                String date = cursor.getString(cursor.getColumnIndex(DATE_TAKEN));
                imagesList.add(new Images(id, custCode, Utility.getPhoto(blob), imageName, date));
            } while (cursor.moveToNext());
        }
        return imagesList;
    }

    public void deleteClaimsDraft(String id){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + CLAIMS_MOTOR_TABLE + " WHERE " + CLAIMS_MOTOR_ID  + "=" + "'"+ id + "'");
    }

    public boolean updateRow(String id, String imgName){
        mDb = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(IMG_NAME, imgName);
        return mDb.update(IMAGES_TABLE, cv, ID + "=" + id, null) > 0;
    }

    public boolean deleteRow(String id, String imgName){
        mDb = mDbHelper.getWritableDatabase();
        return mDb.delete(IMAGES_TABLE, ID + "=? AND " + IMG_NAME + "=?", new String[]{id, imgName}) > 0;
    }

    public ArrayList<Bitmap> retrieveImagesToUpload(String pk){
        mDb = mDbHelper.getReadableDatabase();

        String sqlQuery = "SELECT * FROM " + IMAGES_TABLE + " WHERE " + CUST_CODE + "= '" + pk + "'";

        ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
        Cursor cursor = mDb.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()){
            do {
                byte[] blob = cursor.getBlob(cursor.getColumnIndex(IMG_FILE_NAME));
                bitmaps.add(Utility.getPhoto(blob));
            } while (cursor.moveToNext());
        }

        return bitmaps;
    }

    public void deleteImages(String draft){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + IMAGES_TABLE + " WHERE " + CUST_CODE  + "=" + "'"+ draft + "'");
    }

    public int retrieveLastCount(){
        mDb = mDbHelper.getWritableDatabase();

        String sqlQuery = "SELECT * FROM " + IMAGES_TABLE;

        int count = 0;

        Cursor cursor = mDb.rawQuery(sqlQuery, null);
        if (cursor.moveToLast()){
            String draftsCount = cursor.getString(cursor.getColumnIndex(CUST_CODE));
            char lastChar = draftsCount.charAt(draftsCount.length() - 1);
            count = Integer.parseInt(String.valueOf(lastChar));
        }

        return count;
    }

    public ArrayList<String> retrieveImageNames(String pk){
        mDb = mDbHelper.getReadableDatabase();

        String sqlQuery = "SELECT * FROM " + IMAGES_TABLE + " WHERE " + CUST_CODE + "= '" + pk + "'";

        ArrayList<String> fileName = new ArrayList<String>();
        Cursor cursor = mDb.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()){
            do {
                String imageName = cursor.getString(cursor.getColumnIndex(IMG_NAME));
                fileName.add(imageName);
            } while (cursor.moveToNext());
        }

        return fileName;
    }

}
