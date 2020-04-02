package com.example.fpgins.Network;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UploadImage {

    @Multipart
    @POST("motor/upload-multiple-files/")
    Call<ResponseBody> uploadPhotos(
            @Query("mobile_id") String mobileId,
            @Part List<MultipartBody.Part> files
    );

    @Multipart
    @POST("account/update-profile/")
    Call<ResponseBody> uploadProfilePicture(
            @Query("email") String email,
            @Query("username") String username,
            @Part MultipartBody.Part files
    );
}
