package com.example.a17823.getservicedemo.OtherController;

import android.util.Log;

import com.example.a17823.getservicedemo.Api.UpHotApi;
import com.example.a17823.getservicedemo.Service.UpHotService;
import com.example.a17823.getservicedemo.entities.IsTrueBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 清风明月 on 2019/3/3.
 */

//该类为实时更新阅读量的封装类
public class UpBookHot {

    public void UpHot(String bookname){
        UpHotApi upHotApi=new UpHotApi();
        UpHotService upHotService=upHotApi.getService();
        Call<IsTrueBean> HotCall=upHotService.getState(bookname);
        HotCall.enqueue(new Callback<IsTrueBean>() {
            @Override
            public void onResponse(Call<IsTrueBean> call, Response<IsTrueBean> response) {
                if(response.body().equals("true")){
                    Log.i("实时更新成功",response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<IsTrueBean> call, Throwable t) {

            }
        });
    }
}
