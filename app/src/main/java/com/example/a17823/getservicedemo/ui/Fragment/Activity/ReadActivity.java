package com.example.a17823.getservicedemo.ui.Fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a17823.getservicedemo.Api.FileApi;
import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.Service.FileService;
import com.example.a17823.getservicedemo.entities.Book_File;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 清风明月 on 2018/6/19.
 */

public class ReadActivity extends Activity{

    private String key=null,keyId=null;
    private TextView bookView=null,bookname=null;
    private List<Book_File> text=new ArrayList<>();
    private String file=null,title=null;
    //private URL url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readbook);
        Intent intent=getIntent();
        key=intent.getStringExtra("book_name");
        keyId=intent.getStringExtra("book_id");
        //Toast.makeText(this,key,Toast.LENGTH_SHORT).show();
        initData(key);
    }

    protected void initData(String key){
        bookView=(TextView) findViewById(R.id.read);
        bookname=findViewById(R.id.book_readtitle);
        FileApi fileApi=new FileApi();
        FileService fileService=fileApi.getService();
        Call<Book_File> fileCall=fileService.getState(keyId);
        fileCall.enqueue(new Callback<Book_File>() {
            @Override
            public void onResponse(Call<Book_File> call, Response<Book_File> response) {
                if(response.body()!=null){
                    Log.i("读书","2333333");
                    file=response.body().getFILE().get(0).getBook_file();
                    title=response.body().getFILE().get(0).getBook_name();
                    bookname.setText(title);
                    bookView.setText(file);
                    bookView.setMovementMethod(ScrollingMovementMethod.getInstance());
                }else {
                    Toast.makeText(ReadActivity.this,"暂无数据",Toast.LENGTH_SHORT).show();
                    Intent intent_back=new Intent(ReadActivity.this,MainActivity.class);
                    startActivity(intent_back);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Book_File> call, Throwable t) {

            }
        });
    }
}
