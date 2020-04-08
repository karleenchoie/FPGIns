package com.example.fpgins.Network;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.example.fpgins.BottomNavigation.Claims.ClaimsActivity;
import com.example.fpgins.BottomNavigation.Claims.SubmittedFormsActivity;
import com.example.fpgins.BottomNavigation.Settings.SettingsFragment;
import com.example.fpgins.CircularImageView;
import com.example.fpgins.DataModel.MotorsDraft;
import com.example.fpgins.DataModel.UserData;
import com.example.fpgins.Login.Login;
import com.example.fpgins.R;
import com.example.fpgins.SQLiteDB.DBHelper;
import com.example.fpgins.SQLiteDB.Utility;
import com.example.fpgins.Utility.DefaultDialog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cloud {

    public static class DefaultReturnCode{
        public static final int SUCCESS = 200;
        public static final int CREATED = 201;
        public static final int NO_CONTENT = 204;
        public static final int FAILED = 400;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int INTERNAL_SERVER_ERROR = 500;
    }

    //DEMO STAGE
    public static final String DOMAIN_NAME = "http://10.52.254.58/eclaims";
    //CMS
    public static final String DOMAIN_NAME_CMS = "http://10.52.254.58/control";
//    public static final String DOMAIN_NAME = "http://10.52.2.58/eclaims/account";

    public static final int REQUEST_CONNECTION_TIMEOUT = 10;

    public interface ResultListener {
        void onResult(JSONObject result);
    }

    //========================================= LOGIN ===========================================//

    public static void login(final String email, final String password, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = login(email, password);
                    String ret = jsonObject.get("code").toString();

                    if (Integer.parseInt(ret) != DefaultReturnCode.SUCCESS){
                        return jsonObject;
                    }

                    return jsonObject;
                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject login(String email, String password) throws IOException, JSONException {
        String url = HttpUrl.parse(DOMAIN_NAME + "/account/login").newBuilder()
                .addQueryParameter("email", email)
                .addQueryParameter("password", password)
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //====================================== REGISTRATION ========================================//

    public static void registerAccount(final String[] info, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = registerAccount(info);
                    String ret = jsonObject.get("code").toString();

                    if (Integer.parseInt(ret) != DefaultReturnCode.SUCCESS){
                        return jsonObject;
                    }

                    return jsonObject;
                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject registerAccount(String[] information) throws IOException, JSONException {

        String url = HttpUrl.parse(DOMAIN_NAME + "/account/register").newBuilder()
                .addQueryParameter("account_code", information[0])
                .addQueryParameter("email", information[1])
                .addQueryParameter("password", information[2])
                .addQueryParameter("first_name", information[3])
                .addQueryParameter("last_name", information[4])
                .addQueryParameter("mobile_no", information[5])
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());

    }

    //======================================== RESEND =============================================//

    public static void resendVerification(final String email, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = resendVerification(email);
                    String ret = jsonObject.get("code").toString();

                    if (Integer.parseInt(ret) != DefaultReturnCode.SUCCESS){
                        return jsonObject;
                    }

                    return jsonObject;
                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject object) {
                listener.onResult(object);
            }
        }.execute();
    }

    private static JSONObject resendVerification(String email) throws IOException, JSONException {

        String url = HttpUrl.parse(DOMAIN_NAME + "/account/resend-validation").newBuilder()
                .addQueryParameter("email", email)
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());

    }

    //=================================== FORGOT PASSWORD =========================================//

    public static void forgotPassword(final String email, final ResultListener listener){
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {
                try {
                    JSONObject jsonObject = forgotPassword(email);
                    String ret = jsonObject.get("code").toString();

                    if (Integer.parseInt(ret) != DefaultReturnCode.SUCCESS){
                        return jsonObject;
                    }

                    return jsonObject;
                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject object) {
                listener.onResult(object);
            }
        }.execute();
    }

    private static JSONObject forgotPassword(String email) throws IOException, JSONException {

        String url = HttpUrl.parse(DOMAIN_NAME + "/account/forgot-password").newBuilder()
                .addQueryParameter("email", email)
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());

    }

    //===================================== NEW PASSWORD ==========================================//

    public static void newPassword(final String[] info, final ResultListener listener){
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {
                try {
                    JSONObject jsonObject = newPassword(info);
                    String ret = jsonObject.get("code").toString();

                    if (Integer.parseInt(ret) != DefaultReturnCode.SUCCESS){
                        return jsonObject;
                    }

                    return jsonObject;
                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject object) {
                listener.onResult(object);
            }
        }.execute();
    }

    private static JSONObject newPassword(String[] information) throws IOException, JSONException {

        String url = HttpUrl.parse(DOMAIN_NAME + "/account/new-password").newBuilder()
                .addQueryParameter("email", information[0])
                .addQueryParameter("code", information[1])
                .addQueryParameter("authentication", information[2])
                .addQueryParameter("password_new", information[3])
                .addQueryParameter("password_confirm", information[4])
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());

    }

    //===================================== CHANGE PASSWORD ========================================//

    public static void changePassword(final String[] info, final ResultListener resultListener){
        new AsyncTask<String, String, JSONObject>(){

            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = changePassword(info);
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            public void onPostExecute(JSONObject object){
                resultListener.onResult(object);
            }
        }.execute();
    }

    private static JSONObject changePassword(final String[] information) throws IOException, JSONException {
        String url = HttpUrl.parse(DOMAIN_NAME + "/account/change-password").newBuilder()
                .addQueryParameter("email", information[0])
                .addQueryParameter("username", information[1])
                .addQueryParameter("password_new", information[2])
                .addQueryParameter("password_confirm", information[3])
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //===================================== MOTOR REGISTER ========================================//

    public static void motorRegister(final String[] info, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = motorRegister(info);
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject object) {
                listener.onResult(object);
            }
        }.execute();
    }

    private static JSONObject motorRegister(final String[] information) throws IOException, JSONException {

        String url = HttpUrl.parse(DOMAIN_NAME + "/motor/register").newBuilder()
                .addQueryParameter("last_name", information[0])
                .addQueryParameter("first_name", information[1])
                .addQueryParameter("email", information[2])
                .addQueryParameter("mobile_no", information[3])
                .addQueryParameter("policy_no", information[4])
                .addQueryParameter("claim_no", information[5])
                .addQueryParameter("plate_no", information[6])
                .addQueryParameter("conduction_sticker_no", information[7])
                .addQueryParameter("location", information[8])
                .addQueryParameter("longitude", information[9])
                .addQueryParameter("latitude", information[10])
                .addQueryParameter("send_when", information[11])
                .addQueryParameter("type", information[12])
                .addQueryParameter("remarks", information[13])
                .addQueryParameter("created_by", information[14])
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //======================================== CLAIMS LIST ========================================//

    public static void getSubmittedClaims(final String accountId, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = getSubmittedClaims(accountId);
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject object) {
                listener.onResult(object);
            }
        }.execute();
    }

    private static JSONObject getSubmittedClaims (final String accountId)throws IOException, JSONException {
        String url = HttpUrl.parse(DOMAIN_NAME + "/motor/mobile-all").newBuilder()
                .addQueryParameter("account_id", accountId)
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //====================================== CLAIMS INFO =========================================//

    public static void getClaimsInfo(final String claimNo, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = getClaimsInfo(claimNo);
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject getClaimsInfo (final String claimNo)throws IOException, JSONException {
        String url = HttpUrl.parse(DOMAIN_NAME + "/motor/mobile-manage").newBuilder()
                .addQueryParameter("claim_no", claimNo)
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //================================== PERSONAL INFORMATION ====================================//

    public static void updatePersonalInfo (final String[] info, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>(){

            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = updatePersonalInfo(info);
                    return jsonObject;
                }catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject object) {
                listener.onResult(object);
            }
        }.execute();
    }

    private static JSONObject updatePersonalInfo (final String[] strings)throws IOException, JSONException{
        String url = HttpUrl.parse(DOMAIN_NAME + "/account/update-profile").newBuilder()
                .addQueryParameter("email", strings[0])
                .addQueryParameter("username", strings[1])
                .addQueryParameter("mobile_no", strings[2])
                .addQueryParameter("last_name", strings[3])
                .addQueryParameter("first_name", strings[4])
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //================================== UPDATE PROFILE INFO ======================================//

    public static void uploadProfilePicture(String email, String username, final Bitmap bitmap, final Context context,
                                            final CircularImageView circularImageView, final Dialog dialog){

        List<MultipartBody.Part> parts = new ArrayList<MultipartBody.Part>();
        File file = new File(context.getCacheDir(),  username + ".jpg");


        try {
            byte[] bitmapdata = Utility.getBytes(RotateBitmap(bitmap, 90));
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            parts.add(prepareFilePart("files", file));
        } catch (IOException e){
            e.printStackTrace();
        }


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(DOMAIN_NAME + "/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UploadImage upload = retrofit.create(UploadImage.class);

        Call<ResponseBody> call = upload.uploadProfilePicture(email, username, parts.get(0));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String code = jsonObject.get("code").toString();
                        JSONArray jsonArray = jsonObject.getJSONArray("record");

                        JSONObject obj = jsonArray.getJSONObject(0);
                        String fileUrl = obj.getString("photo");

                        UserData userData = new UserData(PreferenceManager.getDefaultSharedPreferences(context));
                        userData.clearProfilePic();
                        userData.setPhoto(fileUrl);

                        Glide.with(context)
                                .asBitmap()
                                .placeholder(R.drawable.default_image)
                                .load(RotateBitmap(bitmap, 90))
                                .into(circularImageView);

                        dialog.dismiss();
                        Toast.makeText(context, R.string.profile_picture_updated, Toast.LENGTH_SHORT).show();
                    }  catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //======================================= SEND MESSAGE ========================================//

    public static void sendMessage (final String[] info, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = sendMessage(info);
                    return jsonObject;
                }catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject sendMessage (final String[] strings)throws IOException, JSONException{
        String url = HttpUrl.parse(DOMAIN_NAME + "/communication/message-manage").newBuilder()
                .addQueryParameter("claim_no", strings[0])
                .addQueryParameter("account_id", strings[1])
                .addQueryParameter("message", strings[2])
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //================================== RETRIEVE MESSAGES =======================================//

    public static void getAllMessages(final String claimNo, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = getAllMessages(claimNo);
                    return jsonObject;
                }catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject getAllMessages (final String claimNo)throws IOException, JSONException{
        String url = HttpUrl.parse(DOMAIN_NAME + "/communication/message-all").newBuilder()
                .addQueryParameter("claim_no", claimNo)
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }


    //================================== TWO-WAY VERIFICATION ====================================//

    public static void twoWayVerification (final String[] info, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = twoWayVerification(info);
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject object) {
                listener.onResult(object);
            }
        }.execute();
    }

    private static JSONObject twoWayVerification(final String[] information) throws IOException, JSONException {
        String url = HttpUrl.parse(DOMAIN_NAME + "/account/verify").newBuilder()
                .addQueryParameter("email", information[0])
                .addQueryParameter("code", information[1])
                .addQueryParameter("authentication", information[2])
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //========================================== UPLOAD ===========================================//

    public static void upload(ArrayList<Bitmap> bitmaps, final ArrayList<String> names, final String mobileId,
                              final Context context, final String motorId, final Dialog dialog, final String draftKey){

        List<MultipartBody.Part> parts = new ArrayList<MultipartBody.Part>();

        for (int i = 0; i < bitmaps.size(); i++){

            File f = new File(context.getCacheDir(), names.get(i) + ".jpeg");
            byte[] bitmapdata = Utility.getBytes(bitmaps.get(i));

            try {
                //write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                parts.add(prepareFilePart("files[]", f));
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(DOMAIN_NAME + "/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UploadImage upload = retrofit.create(UploadImage.class);

        Call<ResponseBody> call = upload.uploadPhotos(mobileId, parts);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(final Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                //Uploaded

                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String code = jsonObject.get("code").toString();
                        if (Integer.parseInt(code) == DefaultReturnCode.SUCCESS){
                            new DefaultDialog.Builder(context)
                                    .imageResource(ContextCompat.getDrawable(context, R.drawable.green_check), View.VISIBLE)
                                    .message(context.getResources().getString(R.string.successfully_sent_msg))
                                    .detail(context.getResources().getString(R.string.successfully_sent_detail))
                                    .negativeAction(context.getResources().getString(R.string.ok), new DefaultDialog.OnClickListener() {
                                        @Override
                                        public void onClick(Dialog dialog, String et) {
                                            dialog.dismiss();
                                            //when successfully uploaded it will delete the record in sqlite
                                            DBHelper dbHelper = new DBHelper(context);
                                            dbHelper.open();
                                            dbHelper.deleteClaimsDraft(motorId);
                                            dbHelper.deleteImages(draftKey);
                                            dbHelper.close();
                                            Intent intent = new Intent(context, SubmittedFormsActivity.class);
                                            context.startActivity(intent);
                                            ((Activity) context).finish();
                                        }
                                    })
                                    .build()
                                    .show();

                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }




    //******************************************* CMS **********************************************//

    //===================================== NOTIFICATION-ALL ======================================//

    public static void notificationAll(final String accountCode, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = notificationAll(accountCode);
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject notificationAll(String accountCode) throws IOException, JSONException {
        String url = HttpUrl.parse(DOMAIN_NAME_CMS + "/notification/all").newBuilder()
                .addQueryParameter("account_code", accountCode)
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //===================================== NEW NOTIFICATION =======================================//

    public static void newNotification(final String accountId, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = newNotification(accountId);
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject newNotification(String accountId) throws IOException, JSONException {
        String url = HttpUrl.parse(DOMAIN_NAME_CMS + "/notification/latest").newBuilder()
                .addQueryParameter("account_id", accountId)
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //================================== DISMISS NOTIFICATION =====================================//

    public static void dismissNotification(final String[] info, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = dismissNotification(info);
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject dismissNotification(final String[] information) throws IOException, JSONException {
        String url = HttpUrl.parse(DOMAIN_NAME_CMS + "/notification/dismiss").newBuilder()
                .addQueryParameter("notification_id", information[0])
                .addQueryParameter("notification_recipient_id", information[1])
                .addQueryParameter("account_id", information[2])
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //========================================= PARTNERS ==========================================//

    public static void getAllPartners(final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = getAllPartners();
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject getAllPartners() throws IOException, JSONException{
        String url = DOMAIN_NAME_CMS + "/partner/all";

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //========================================== BANNER ===========================================//

    public static void getBanner(final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = getBanner();
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject getBanner()throws IOException, JSONException{
        String url = DOMAIN_NAME_CMS + "/banner/all";

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //====================================== NEWS AND EVENTS ======================================//

    public static void getNewsAndEvents(final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = getNewsAndEvents();
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject getNewsAndEvents() throws IOException, JSONException {
        String url = DOMAIN_NAME_CMS + "/event/all";

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    public static void getSpecificNewsAndEvents(final String articleId, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = getSpecificNewsAndEvents(articleId);
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject getSpecificNewsAndEvents(String articleId) throws IOException, JSONException {
        String url = HttpUrl.parse(DOMAIN_NAME_CMS + "/events/view").newBuilder()
                .addQueryParameter("article_id", articleId)
                .build().toString();;

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //======================================= OFFICE ADDRESS ======================================//

    public static void getOfficeAddress(final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = getOfficeAddress();
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject getOfficeAddress() throws IOException, JSONException {
        String url = DOMAIN_NAME_CMS + "/support/office";

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //=========================== FREQUENTLY ASKED QUESTIONS (FAQ) ================================//

    public static void getFAQ(final ResultListener listener) {
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = getFAQ();
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject getFAQ() throws IOException, JSONException {
        String url = DOMAIN_NAME_CMS + "/support/faq";

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //================================= INQUIRY (Contact-Us) ======================================//

    public static void manageInquiry(final int accountId, final int departmentId, final String policyNo, final String message, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = manageInquiry(accountId, departmentId, policyNo, message);
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject manageInquiry(int accountId, int departmentId, String policyNo, String message) throws IOException, JSONException{
        String url = HttpUrl.parse(DOMAIN_NAME_CMS + "/events/view").newBuilder()
                .addQueryParameter("account_id ", String.valueOf(accountId))
                .addQueryParameter("department_id ", String.valueOf(departmentId))
                .addQueryParameter("policy_no", policyNo)
                .addQueryParameter("message", message)
                .build().toString();;

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //======================================= SOS Alert ===========================================//

    public static void sosAlert(final int accountId, final String location, final String longitude, final String latitude, final String mobileNo, final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = sosAlert(accountId, location, longitude, latitude, mobileNo);
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject sosAlert(int accountId, String location, String longitude, String latitude, String mobileNo) throws IOException, JSONException {
        String url = HttpUrl.parse(DOMAIN_NAME_CMS + "/support/sos-manage").newBuilder()
                .addQueryParameter("account_id ", String.valueOf(accountId))
                .addQueryParameter("location ", location)
                .addQueryParameter("longitude", longitude)
                .addQueryParameter("latitude", latitude)
                .addQueryParameter("mobile_no", mobileNo)
                .build().toString();

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //====================================== DEPARTMENT ===========================================//

    public static void getAllDepartment(final ResultListener listener) {
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = getAllDepartment();
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject getAllDepartment() throws IOException, JSONException {
        String url = DOMAIN_NAME_CMS + "/master/department";

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //========================================= SETUP =============================================//

    public static void getSystemConfig(final ResultListener listener){
        new AsyncTask<String, String, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... strings) {
                try {
                    JSONObject jsonObject = getSystemConfig();
                    return jsonObject;

                } catch (Exception e){
                    return resultException(e);
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                listener.onResult(jsonObject);
            }
        }.execute();
    }

    private static JSONObject getSystemConfig() throws IOException, JSONException {
        String url = DOMAIN_NAME_CMS + "/system/setup";

        OkHttpClient client = defaultHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return new JSONObject(response.body().string());
    }

    //=============================================================================================//

    @NonNull
    private static MultipartBody.Part prepareFilePart(String partName, File file) {

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private static JSONObject resultException(Exception e) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Failed", DefaultReturnCode.INTERNAL_SERVER_ERROR);
            jsonObject.put("Return Message", e.getMessage());
        } catch (JSONException e1){
            Log.e("Error: %s", e1.getMessage());
        }

        return jsonObject;
    }

    private static OkHttpClient defaultHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(REQUEST_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(trustAllSslSocketFactory, (X509TrustManager)trustAllCerts[0])
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
    }

    public static final TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };

    public static final SSLContext trustAllSslContext;

    static {
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    public static final SSLSocketFactory trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();

}
