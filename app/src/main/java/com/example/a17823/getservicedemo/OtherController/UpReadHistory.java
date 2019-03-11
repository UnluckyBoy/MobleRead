package com.example.a17823.getservicedemo.OtherController;

import android.util.Log;

import com.example.a17823.getservicedemo.Api.UpHistoryApi;
import com.example.a17823.getservicedemo.Service.UpHistoryService;
import com.example.a17823.getservicedemo.entities.IsTrueBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 清风明月 on 2019/3/11.
 */

//添加用户阅读记录的封装
public class UpReadHistory {
    public void UpHistory(String userId,String bookname){
        UpHistoryApi upHistoryApi=new UpHistoryApi();
        UpHistoryService upHistoryService=upHistoryApi.getService();
        Call<IsTrueBean> call=upHistoryService.getState(userId,bookname);
        call.enqueue(new Callback<IsTrueBean>() {
            @Override
            public void onResponse(Call<IsTrueBean> call, Response<IsTrueBean> response) {
                if(response.body().getResult().equals("true")){
                    Log.i("添加成功","");
                }
            }

            @Override
            public void onFailure(Call<IsTrueBean> call, Throwable t) {

            }
        });
    }
}
