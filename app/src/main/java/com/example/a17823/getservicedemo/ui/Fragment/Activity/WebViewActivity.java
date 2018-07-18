package com.example.a17823.getservicedemo.ui.Fragment.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a17823.getservicedemo.R;

/**
 * Created by 清风明月 on 2018/6/28.
 */

public class WebViewActivity extends Activity{
    private String head;
    private WebView webView=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        //LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        //LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.webview, null);
        Intent intent=getIntent();
        head=intent.getStringExtra("Url");
        initData(head);
    }

    private void initData(final String head) {
        webView=findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);//设置webview属性，运行js脚本
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //webView.getSettings().setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("拦截","233333");
                if(url.equals(null)) {
                    //Toast.makeText(WebViewActivity.this,"广告链接为空",Toast.LENGTH_SHORT).show();
                    url="www.baidu.com";
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            /**
             * 不支持js弹窗，通过监听处理
             */
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
                localBuilder.setMessage(message).setPositiveButton("确定",null);
                localBuilder.setCancelable(false);
                localBuilder.create().show();

                result.confirm();
                return true;
                //return super.onJsAlert(view, url, message, result);
            }

            //获取网页title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

        });

        webView.loadUrl(head);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
        webView=null;
    }
}
