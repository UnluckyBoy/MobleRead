package com.example.a17823.getservicedemo.Service;

import com.example.a17823.getservicedemo.entities.BOOK;
import com.example.a17823.getservicedemo.entities.LoadViewBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 清风明月 on 2018/6/8.
 */

public interface AdvertiseService {
    @POST("readhistory")
    Call<LoadViewBean>getState(@Query("account") String account);
}
