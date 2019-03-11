package com.example.a17823.getservicedemo.Api;

import com.example.a17823.getservicedemo.Service.SearchBookService;

import retrofit2.Retrofit;

/**
 * Created by 清风明月 on 2018/6/6.
 */

public class SearchBookApi extends WebApi{

    //String url="http://1oz9819419.iask.in:44216/MobleRead/";
    //String url="http://192.168.16.111:8080/UserService/";
    String url="http://1oz9819419.iask.in:44216/MobleRedServer/BookController/";
    Retrofit retrofit=getApi(url);
    @Override
    public <T> T getService() {
        return (T) retrofit.create(SearchBookService.class);
    }
}
