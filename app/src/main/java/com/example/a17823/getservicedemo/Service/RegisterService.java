package com.example.a17823.getservicedemo.Service;

import com.example.a17823.getservicedemo.entities.IsTrueBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 17823 on 2018/5/9.
 */

public interface RegisterService {
    @POST("Register")
    Call<IsTrueBean> getState(
            @Query("account") String Account,
            @Query("Pwd") String Pwd
    );
}
