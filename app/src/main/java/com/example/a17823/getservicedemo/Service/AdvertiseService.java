package com.example.a17823.getservicedemo.Service;

import com.example.a17823.getservicedemo.entities.LoadViewBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 清风明月 on 2018/6/8.
 */

public interface AdvertiseService {
    @GET("getAdvertise")
    Call<LoadViewBean>getState();
}
