package com.example.a17823.getservicedemo.ui.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.ui.view.PageController.Config;
import com.example.a17823.getservicedemo.ui.view.PageWidget;


/**
 * Created by 清风明月 on 2019/3/6.
 */

public class Read extends Activity{

    private PageWidget pageWidget;
    private Config config;
    private int screenWidth, screenHeight;
    private Boolean isShow = false;
    //Bitmap mCurPageBitmap, mNextPageBitmap;
    //Canvas mCurPageCanvas, mNextPageCanvas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("跳转成功","");
        setContentView(R.layout.readpage);
        //pageWidget.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        //获取屏幕宽高
        WindowManager manage = getWindowManager();
        Display display = manage.getDefaultDisplay();
        Point displaysize = new Point();
        display.getSize(displaysize);
        screenWidth = displaysize.x;
        screenHeight = displaysize.y;
        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //pageWidget.setPageMode(config.getPageMode());

    }
}
