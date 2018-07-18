package com.example.a17823.getservicedemo.ui.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a17823.getservicedemo.Api.AdvertiseApi;
import com.example.a17823.getservicedemo.Api.RecommendeApi;
import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.Service.AdvertiseService;
import com.example.a17823.getservicedemo.Service.RecommendeServise;
import com.example.a17823.getservicedemo.adapter.LoadallAdapter;
import com.example.a17823.getservicedemo.adapter.MainAdpter;
import com.example.a17823.getservicedemo.entities.BOOK;
import com.example.a17823.getservicedemo.entities.LoadViewBean;
import com.example.a17823.getservicedemo.ui.Fragment.Activity.ReadActivity;
import com.example.a17823.getservicedemo.ui.Fragment.Activity.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import me.wangyuwei.banner.BannerEntity;
import me.wangyuwei.banner.BannerView;
import me.wangyuwei.banner.OnBannerClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 17823 on 2017/12/23.
 */

public class MainFragment extends Fragment{

    private View view;
    private RecyclerView mRecyclerView;
    private List<BOOK> book=new ArrayList<>();
    private BannerView mBanner;
    private List<BannerEntity> bannerEntities = new ArrayList<>();
    private List<LoadViewBean> viewBeans=new ArrayList<>();
    private ImageView imageView;

    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view!=null){
            ViewGroup parent=(ViewGroup) view.getParent();
            if(parent!=null){
                parent.removeView(view);
            }
        }else{
            view = inflater.inflate(R.layout.mainfragment, container, false);
            Bundle bundle = getArguments();
            String agrs1 = bundle.getString("agrs1");

            Run_main_data(view);
            Run_main_booklist(view);
            //initRefresh();
        }
        return view;
    }

    private void Run_main_booklist(final View view) {
        mRecyclerView=view.findViewById(R.id.main_list);
        imageView=view.findViewById(R.id.errorImage);
        RecommendeApi recommendeApi=new RecommendeApi();
        RecommendeServise recommendeServise=recommendeApi.getService();
        Call<BOOK> viewBeanCall=recommendeServise.getState();
        viewBeanCall.enqueue(new Callback<BOOK>() {
            @Override
            public void onResponse(Call<BOOK> call, Response<BOOK> response) {
                if(response.body().getList()!=null){
                    Log.i("在Recommende的if","ok");
                    for(int i=0;i<response.body().getList().size();i++){
                        book.add(response.body().getList().get(i));
                    }
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                            mRecyclerView.setLayoutManager(linearLayoutManager);
                            final MainAdpter mainAdpter=new MainAdpter(getActivity(),book);
                            mainAdpter.SetOnItemClickListener(new MainAdpter.OnItemClickListener(){
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent=new Intent(getActivity(), ReadActivity.class);
                                    intent.putExtra("book_id",book.get(position).getB_ID());
                                    intent.putExtra("book_name", book.get(position).getB_Name());
                                    startActivity(intent);
                                }

                                @Override
                                public void onItemLongClick(View view, int position) {

                                }
                            });
                            mRecyclerView.setAdapter(mainAdpter);
                        }
                    },1000);
                }else{
                    //mDatas.add(null);
                    Log.i("在Recommende的else","error");
                }
            }

            @Override
            public void onFailure(Call<BOOK> call, Throwable t) {
                //Toast.makeText(getActivity(),"网络错误！",Toast.LENGTH_SHORT).show();
                imageView.setImageResource(R.drawable.notdata);
                Log.i("在Recommende的failure","error");
            }
        });
    }

    private void Run_main_data(final View view){
        mBanner=view.findViewById(R.id.bannerView);

        AdvertiseApi advertiseApi=new AdvertiseApi();
        AdvertiseService advertiseService=advertiseApi.getService();
        Call<LoadViewBean> viewBeanCall=advertiseService.getState();

        viewBeanCall.enqueue(new Callback<LoadViewBean>() {
            @Override
            public void onResponse(Call<LoadViewBean> call, final Response<LoadViewBean> response) {
                if(response.body()!=null){
                    Log.i("广告轮播if","emmmmmmmmm");
                    for (int i = 0; i<response.body().getViewBean().size(); i++) {
                        viewBeans.add(response.body().getViewBean().get(i));
                        BannerEntity bannerEntity = new BannerEntity();
                        //bannerEntity.imageUrl = response.body().getViewBean().get(i).getPic_url();
                        bannerEntity.imageUrl ="http://1oz9819419.iask.in:44216/getImage"+viewBeans.get(i).getPic_url();
                        bannerEntity.title = response.body().getViewBean().get(i).getTitle();
                        bannerEntities.add(bannerEntity);
                    }
                    if (bannerEntities.size()!=0)
                        mBanner.setEntities(bannerEntities);
                    mBanner.setOnBannerClickListener(new OnBannerClickListener() {
                        @Override
                        public void onClick(int position) {
                            Log.i("点击广告位", String.valueOf(position));
                            Intent intent=new Intent(getActivity(), WebViewActivity.class);
                            intent.putExtra("Url",viewBeans.get(position).getHead());
                            startActivity(intent);
                        }
                    });
                }
                Log.i("广告轮播if外","emmmmmmmmm");
            }

            @Override
            public void onFailure(Call<LoadViewBean> call, Throwable t) {

            }
        });
    }
    /*private void initRefresh() {
        //刷新界面
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipLay);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refreshFruits();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Run_main_data(view);
                        Run_main_booklist(view);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1500);
            }
        });
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}