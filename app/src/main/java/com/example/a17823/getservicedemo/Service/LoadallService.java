package com.example.a17823.getservicedemo.Service;

import com.example.a17823.getservicedemo.entities.LoadallBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 清风明月 on 2018/6/4.
 */

public interface LoadallService {
    @GET("loadAll")
    Call<LoadallBean> getState();
}
