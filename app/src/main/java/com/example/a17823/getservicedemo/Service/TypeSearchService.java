package com.example.a17823.getservicedemo.Service;

import com.example.a17823.getservicedemo.entities.LoadallBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 清风明月 on 2018/6/20.
 */

public interface TypeSearchService {
    @POST("TypeSearch")
    Call<LoadallBean> getState(@Query("type") String type);
}
