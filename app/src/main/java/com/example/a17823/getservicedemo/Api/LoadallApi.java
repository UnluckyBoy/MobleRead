package com.example.a17823.getservicedemo.Api;

import com.example.a17823.getservicedemo.Service.LoadallService;

import retrofit2.Retrofit;

/**
 * Created by 清风明月 on 2018/6/4.
 */

public class LoadallApi extends WebApi{
    //String url="http://1oz9819419.iask.in:44216/MobleRead/";
    //String url="http://localhost:8080/MobleRead/";
    String url="http://1oz9819419.iask.in:44216/MobleRedServer/BookController/";
    Retrofit retrofit=getApi(url);
    @Override
    public <T> T getService() {
        return (T) retrofit.create(LoadallService.class);
    }
}
