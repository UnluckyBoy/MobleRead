package com.example.a17823.getservicedemo.Service;

import com.example.a17823.getservicedemo.entities.IsTrueBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 清风明月 on 2019/3/11.
 */

public interface IsReadService {
    @POST("isRecord")
    Call<IsTrueBean>getState(@Query("account") String account,@Query("name") String name);
}
