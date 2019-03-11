package com.example.a17823.getservicedemo.ui.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.ui.Fragment.MainFragment;
import com.example.a17823.getservicedemo.ui.Fragment.MoreFragment;
import com.example.a17823.getservicedemo.ui.Fragment.MyFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;
    private String TAG = MainActivity.class.getSimpleName();
    private MainFragment mMainFragment;
    private MyFragment myFragment;
    private MoreFragment moreFragment;
    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Run();
        setDefaultFragment();
    }

    public void Run(){
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bar_main);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.manage, "推荐").setActiveColor(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.book, "书城").setActiveColor(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.user, "我").setActiveColor(R.color.blue))
                .setFirstSelectedPosition(lastSelectedPosition )
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    private void setDefaultFragment() {
        Intent intent=getIntent();
        String id=intent.getStringExtra("U_id");
        //Log.i("MainActivity中的id",id);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if(id==null){
            mMainFragment = MainFragment.newInstance("");
        }else{
            mMainFragment = MainFragment.newInstance(id);
        }
        transaction.replace(R.id.tb, mMainFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        Intent intent=getIntent();
        String id=intent.getStringExtra("U_id");

        FragmentManager fm = this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (mMainFragment == null) {
                    mMainFragment = MainFragment.newInstance(id);
                }
                transaction.replace(R.id.tb, mMainFragment);
                //this.finish();
                break;
            case 1:
                if (moreFragment == null) {
                    moreFragment = MoreFragment.newInstance(id);
                }
                transaction.replace(R.id.tb, moreFragment);
                //this.finish();
                break;
            case 2:
                if (myFragment == null) {
                    myFragment = MyFragment.newInstance(id);
                }
                transaction.replace(R.id.tb, myFragment);
                //this.finish();
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onBackPressed() {
        if(!mBackKeyPressed){
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
                }, 2000);
        }
        else{//退出程序
            this.finish();
            System.exit(0);
        }
    }
}