package com.example.a17823.getservicedemo.Service;

import com.example.a17823.getservicedemo.entities.BOOK;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 清风明月 on 2018/6/20.
 */

public interface RecommendeServise {
    @GET("Recommende")
    Call<BOOK>getState();
}
