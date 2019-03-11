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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a17823.getservicedemo.Api.TypeSearchApi;
import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.Service.TypeSearchService;
import com.example.a17823.getservicedemo.adapter.TypeSearchAdapter;
import com.example.a17823.getservicedemo.entities.LoadallBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 清风明月 on 2018/6/3.
 */

public class TypeSearchActivity extends AppCompatActivity{
    private String type_key=null;
    private List<LoadallBean> typelist=new ArrayList<>();
    private RecyclerView typeView;
    private ImageView imageView;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        type_key=intent.getStringExtra("type");
        setContentView(R.layout.type_search);

        //Toast.makeText(this,type_key,Toast.LENGTH_SHORT).show();
        initData(type_key);
    }

    private void initData(final String type_key) {
        typeView=(RecyclerView) findViewById(R.id.type_list);
        imageView=findViewById(R.id.errorImage);
        textView=findViewById(R.id.bookName);
        textView.setText("以下为关于"+type_key+"的书籍：");
        TypeSearchApi typeSearchApi=new TypeSearchApi();
        TypeSearchService typeSearchService=typeSearchApi.getService();
        Call<LoadallBean> call=typeSearchService.getState(type_key);
        call.enqueue(new Callback<LoadallBean>() {
            @Override
            public void onResponse(Call<LoadallBean> call, Response<LoadallBean> response) {
                if(response.body()!=null){
                    Log.i("type","okokokokokok");
                    for(int i=0;i<response.body().getBooklist().size();i++){
                        typelist.add(response.body().getBooklist().get(i));
                    }
                    InitSearchData(typelist);
                }
            }

            @Override
            public void onFailure(Call<LoadallBean> call, Throwable t) {
                imageView.setImageResource(R.drawable.notdata);
            }
        });
    }

    //绑定数据
    public void InitSearchData(final List<LoadallBean> listBean){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(TypeSearchActivity.this);
                typeView.setLayoutManager(linearLayoutManager);
                TypeSearchAdapter typeSearchAdapter=new TypeSearchAdapter(TypeSearchActivity.this,listBean);
                typeSearchAdapter.SetOnItemClickListener(new TypeSearchAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent=new Intent(TypeSearchActivity.this, ReadActivity.class);
                        intent.putExtra("book_id",listBean.get(position).getB_ID());
                        intent.putExtra("book_name", listBean.get(position).getB_Name());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                typeView.setAdapter(typeSearchAdapter);
            }
        },1000);
    }
}
