package com.example.a17823.getservicedemo.Api;

import com.example.a17823.getservicedemo.Service.UpnameService;

import retrofit2.Retrofit;

/**
 * Created by 清风明月 on 2018/6/29.
 */

public class UpNameApi extends WebApi{
    String url="http://1oz9819419.iask.in:44216/UserService/";
    Retrofit retrofit=getApi(url);
    @Override
    public <T> T getService() {
        return (T)retrofit.create(UpnameService.class);
    }
}
