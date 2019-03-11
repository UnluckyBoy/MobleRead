package com.example.a17823.getservicedemo.Service;

import com.example.a17823.getservicedemo.entities.RecordBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 清风明月 on 2019/3/2.
 */

public interface UserRecordService {
    @POST("record")
    Call<RecordBean> getState(
            @Query("account") String account
    );
}
