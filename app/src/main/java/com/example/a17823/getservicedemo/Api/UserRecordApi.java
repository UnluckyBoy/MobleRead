package com.example.a17823.getservicedemo.Api;

import com.example.a17823.getservicedemo.Service.UserRecordService;

import retrofit2.Retrofit;

/**
 * Created by 清风明月 on 2019/3/2.
 */

public class UserRecordApi extends WebApi{
    String url="http://1oz9819419.iask.in:44216/MobleRedServer/UserController/";
    Retrofit retrofit=getApi(url);
    @Override
    public <T> T getService() {
        return (T)retrofit.create(UserRecordService.class);
    }
}
