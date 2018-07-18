package com.example.a17823.getservicedemo.Service;

import com.example.a17823.getservicedemo.entities.IsTrueBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by 清风明月 on 2018/6/30.
 */

public interface UpHeadService {
    @Multipart
    @POST("UpHead")
    Call<IsTrueBean> getState(
            @Query("account") String account,
            @Part MultipartBody.Part file);
}
