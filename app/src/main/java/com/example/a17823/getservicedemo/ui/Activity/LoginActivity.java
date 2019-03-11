package com.example.a17823.getservicedemo.ui.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a17823.getservicedemo.Api.LoginApi;
import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.Service.LoginService;
import com.example.a17823.getservicedemo.entities.IsTrueBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 17823 on 2018/1/23.
 */

public class LoginActivity extends Activity{
    private String U_id,U_id_get;
    //private String name;
    //String U_pwd="Shuaixiaohai2.";
    private static boolean mBackKeyPressed = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*Intent intent=getIntent();
        U_id_get=intent.getStringExtra("U_id");*/
        Run();
        Run_register();
    }
    public void Run(){
        Button login=findViewById(R.id.login);
        Button cacel=findViewById(R.id.cancel);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Login","login");
                EditText Uid=findViewById(R.id.uId_edit);
                /*if(U_id_get==null){
                    //EditText Uid=findViewById(R.id.uId_edit);
                    name=Uid.getText().toString();
                }else{
                    name=U_id_get;
                    Uid.setHint(U_id_get);
                }*/
                EditText Upwd=findViewById(R.id.uPwd_edit);
                final String name=Uid.getText().toString();
                final String pwd=Upwd.getText().toString();
                LoginApi loginApi=new LoginApi();
                LoginService loginService=loginApi.getService();
                Call<IsTrueBean> call_login=loginService.getState(name,pwd);
                call_login.enqueue(new Callback<IsTrueBean>() {
                    @Override
                    public void onResponse(Call<IsTrueBean> call, Response<IsTrueBean> response) {
                        Log.i("Login","login_ok");
                        if(response.body().getResult().equals("true")){
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("U_id",name);
                            intent.putExtra("U_pwd",pwd);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this,
                                    "账户或密码错误！",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<IsTrueBean> call, Throwable t) {

                        Log.i("Login","login_Failure");
                        Toast.makeText(LoginActivity.this,
                                "网络连接错误！请检查网络。。。",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        cacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog=new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("系统提示");
                alertDialog.setIcon(R.drawable.warning);
                alertDialog.setMessage("是否放弃登录？");
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialog.show();
            }
        });
    }
    public void Run_register(){
        Button button=(Button)findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redister=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(redister);
                finish();
            }
        });
    }
    public void onBackPressed() {
        AlertDialog alertDialog=new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle("系统提示");
        alertDialog.setIcon(R.drawable.warning);
        alertDialog.setMessage("是否放弃登录？");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.show();
    }
}