package com.example.a17823.getservicedemo.Service;

import com.example.a17823.getservicedemo.entities.IsTrueBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 清风明月 on 2019/3/3.
 */

public interface UpHotService {
    @POST("userRead")
    Call<IsTrueBean> getState(
            @Query("name") String name
    );
}
