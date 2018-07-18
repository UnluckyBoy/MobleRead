package com.example.a17823.getservicedemo.ui.Fragment.Activity;

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
import android.widget.Toast;

import com.example.a17823.getservicedemo.Api.SearchBookApi;
import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.Service.SearchBookService;
import com.example.a17823.getservicedemo.adapter.SearchAdapter;
import com.example.a17823.getservicedemo.entities.LoadallBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 清风明月 on 2018/6/3.
 */

public class SearchBookActivity extends AppCompatActivity{

    private String name=null;
    private List<LoadallBean> searchlist=new ArrayList<>();
    private ImageView imageView;
    private RecyclerView searchView;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_book);
        Intent intent=getIntent();
        name=intent.getStringExtra("bookname");
        //Toast.makeText(this,key,Toast.LENGTH_SHORT).show();
        initData(name);
    }

    public void initData(String name) {
        searchView=(RecyclerView) findViewById(R.id.search_list);
        imageView=findViewById(R.id.errorImage);
        textView=findViewById(R.id.bookName);
        textView.setText("以下为关于"+name+"的书籍：");
        SearchBookApi searchBookApi=new SearchBookApi();
        SearchBookService searchBookService=searchBookApi.getService();
        Call<LoadallBean> call=searchBookService.getState(name);
        call.enqueue(new Callback<LoadallBean>() {
            @Override
            public void onResponse(Call<LoadallBean> call, Response<LoadallBean> response) {
                if(!response.body().equals(null)){
                    Log.i("type","okokokokokok");
                    for(int i=0;i<response.body().getBooklist().size();i++){
                        searchlist.add(response.body().getBooklist().get(i));
                    }
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SearchBookActivity.this);
                            searchView.setLayoutManager(linearLayoutManager);
                            SearchAdapter searchAdapter=new SearchAdapter(SearchBookActivity.this,searchlist);
                            searchAdapter.SetOnItemClickListener(new SearchAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent=new Intent(SearchBookActivity.this, ReadActivity.class);
                                    intent.putExtra("book_id",searchlist.get(position).getB_ID());
                                    intent.putExtra("book_name", searchlist.get(position).getB_Name());
                                    startActivity(intent);
                                }

                                @Override
                                public void onItemLongClick(View view, int position) {

                                }
                            });
                            searchView.setAdapter(searchAdapter);
                        }
                    },1000);
                }else{
                    //imageView.setImageResource(R.drawable.notdata);
                }
            }

            @Override
            public void onFailure(Call<LoadallBean> call, Throwable t) {
                imageView.setImageResource(R.drawable.notdata);
            }
        });
    }
}
