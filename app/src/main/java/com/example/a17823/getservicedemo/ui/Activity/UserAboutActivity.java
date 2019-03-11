package com.example.a17823.getservicedemo.ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.a17823.getservicedemo.R;

/**
 * Created by 清风明月 on 2018/6/3.
 */

public class UserAboutActivity extends AppCompatActivity{

    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_about);
        init();
    }

    public void init(){
        button=findViewById(R.id.about_url);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserAboutActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
