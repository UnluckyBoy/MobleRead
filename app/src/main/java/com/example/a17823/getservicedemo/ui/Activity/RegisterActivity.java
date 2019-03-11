package com.example.a17823.getservicedemo.ui.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a17823.getservicedemo.Api.RegisterApi;
import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.Service.RegisterService;
import com.example.a17823.getservicedemo.entities.IsTrueBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 17823 on 2018/2/3.
 */

public class RegisterActivity extends AppCompatActivity{
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anctivity_register);
        Run_OK();
        Run_cacel();
    }

    public void Run_OK(){
        Button ok_btn=(Button)findViewById(R.id.OK);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = (EditText) findViewById(R.id.use_register);
                EditText pwd = (EditText) findViewById(R.id.pwd_register);
                EditText Ispwd = (EditText) findViewById(R.id.pwd_Isok);
                final String user_name = name.getText().toString();
                final String user_pwd = pwd.getText().toString();
                final String ispwd=Ispwd.getText().toString();
                if (!(ispwd.equals(user_pwd))) {
                    Toast.makeText(RegisterActivity.this,
                            "前后输入密码错误！",Toast.LENGTH_SHORT).show();
                }
                else {
                    /*Toast.makeText(RegisterActivity.this,"前后输入密码错误！",Toast.LENGTH_SHORT).show();*/
                    RegisterApi userApi = new RegisterApi();
                    RegisterService registerService = userApi.getService();
                    Call<IsTrueBean> call_Register = registerService.getState(user_name, user_pwd);
                    call_Register.enqueue(new Callback<IsTrueBean>() {
                        @Override
                        public void onResponse(Call<IsTrueBean> call, Response<IsTrueBean> response) {
                            if (response.body().getResult().equals("true")) {
                                Log.i("Register","ok");
                                Toast.makeText(RegisterActivity.this,
                                        "恭喜注册成功！", Toast.LENGTH_SHORT).show();
                                Intent go_login_intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                go_login_intent.putExtra("U_id", user_name);
                                startActivity(go_login_intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "此号已注册！",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<IsTrueBean> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this,
                                    "网络连接错误！请检查网络。。。", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        });
    }

    public void Run_cacel(){
        Button cacel=(Button)findViewById(R.id.Cacel);
        cacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog=new AlertDialog.Builder(RegisterActivity.this).create();
                alertDialog.setTitle("系统提示");
                alertDialog.setIcon(R.drawable.warning);
                alertDialog.setMessage("是否放弃注册？");
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialog.show();
            }
        });

    }

    public void onBackPressed() {
        /*Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();*/
        AlertDialog alertDialog=new AlertDialog.Builder(this).create();
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
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.show();
    }
}
