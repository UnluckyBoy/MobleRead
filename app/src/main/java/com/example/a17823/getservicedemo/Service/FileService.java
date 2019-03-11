package com.example.a17823.getservicedemo.Service;

import com.example.a17823.getservicedemo.entities.Book_File;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 清风明月 on 2018/6/20.
 */

public interface FileService {
        @POST("loadFile")
        Call<Book_File> getState(@Query("BookKey") String BookKey);
}
