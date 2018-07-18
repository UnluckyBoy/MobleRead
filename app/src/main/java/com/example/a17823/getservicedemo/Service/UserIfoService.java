package com.example.a17823.getservicedemo.Service;

import com.example.a17823.getservicedemo.entities.UserBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 清风明月 on 2018/6/29.
 */

public interface UserIfoService {
    @POST("GetIfo")
    Call<UserBean> getState(@Query("userKey") String userKey);
}
