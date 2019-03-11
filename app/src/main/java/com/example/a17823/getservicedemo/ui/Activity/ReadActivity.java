package com.example.a17823.getservicedemo.ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
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
import com.example.a17823.getservicedemo.ui.view.ReadBookView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
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
    private int position;
    private ReadBookView readBookView;
    private BufferedReader reader;
    CharBuffer buffer = CharBuffer.allocate(5000);//加载5000个字
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
        //loadPage(0);
    }

    protected void initData(String key){
        //bookView=(TextView) findViewById(R.id.read);
        readBookView=findViewById(R.id.read_view);
        //bookname=findViewById(R.id.book_readtitle);
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
                    //bookname.setText(title);
                    setTitle(title);
                    //bookView.setText(file);
                    loadBook(file);
                    loadPage(0);
                    //bookView.setMovementMethod(ScrollingMovementMethod.getInstance());
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

    /**
     * 将电子书都一部分到缓冲区
     */
    private void loadBook(String book) {
        /*AssetManager assets = getAssets();
        try {
            InputStream in = assets.open("documents/The Golden Compass.txt");
            Charset charset = CharsetDetector.detect(in);
            reader = new BufferedReader(new InputStreamReader(in, charset));
            reader.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            InputStream   bookcode   =   new ByteArrayInputStream(book.getBytes("UTF-8"));
            //Charset charset=
            reader = new BufferedReader(new InputStreamReader(bookcode));
            reader.read(buffer);
            //Log.i("缓冲区：",reader.readLine());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从指定位置开始载入一页
     */
    private void loadPage(int position) {
        buffer.position(position);
        readBookView.setText(buffer);
    }

}
