package com.example.a17823.getservicedemo.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.a17823.getservicedemo.Api.SearchBookApi;
import com.example.a17823.getservicedemo.Api.UserRecordApi;
import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.Service.SearchBookService;
import com.example.a17823.getservicedemo.Service.UserRecordService;
import com.example.a17823.getservicedemo.adapter.RecordAdapter;
import com.example.a17823.getservicedemo.adapter.SearchAdapter;
import com.example.a17823.getservicedemo.entities.LoadallBean;
import com.example.a17823.getservicedemo.entities.RecordBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 清风明月 on 2018/6/3.
 */

public class UserReadActivity extends AppCompatActivity{

    private String user;
    private List<RecordBean> Recordlist=new ArrayList<>();
    private RecyclerView RecordView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_read);

        Intent intent=getIntent();
        user=intent.getStringExtra("user");//获取MyFragment传过来的用户account.
        Log.i("UserReadActivity",user);

        InitRecord(user);

    }
    //加载用户阅读记录
    public void InitRecord(String user){

        RecordView=findViewById(R.id.Record_list);
        //获取阅读记录关键字接口
        UserRecordApi userRecordApi=new UserRecordApi();
        final UserRecordService userRecordService=userRecordApi.getService();
        Call<RecordBean> recordBeanCall=userRecordService.getState(user);
        recordBeanCall.enqueue(new Callback<RecordBean>() {
            @Override
            public void onResponse(Call<RecordBean> call, Response<RecordBean> response) {
                if (!(response.body().getRecord().equals(null))) {
                    //获取书籍数据
                    for (int i = 0; i < response.body().getRecord().size(); i++) {
                        Recordlist.add(response.body().getRecord().get(i));
                    }
                    InitHolder(Recordlist);
                }
            }

            @Override
            public void onFailure(Call<RecordBean> call, Throwable t) {

            }
        });
    }

    //绑定数据给itemlist
    public void InitHolder(final List<RecordBean> list){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(UserReadActivity.this);
                RecordView.setLayoutManager(linearLayoutManager);
                RecordAdapter recordAdapter=new RecordAdapter(UserReadActivity.this,list);
                recordAdapter.SetOnItemClickListener(new RecordAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent=new Intent(UserReadActivity.this, ReadActivity.class);
                        intent.putExtra("book_id",list.get(position).getId());
                        intent.putExtra("book_name", list.get(position).getName());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                RecordView.setAdapter(recordAdapter);
            }
        },1000);
    }
}
