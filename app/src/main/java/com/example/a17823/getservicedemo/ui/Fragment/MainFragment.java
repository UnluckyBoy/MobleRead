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

import com.example.a17823.getservicedemo.Api.AdvertiseApi;
import com.example.a17823.getservicedemo.Api.IsFirstApi;
import com.example.a17823.getservicedemo.Api.IsReadApi;
import com.example.a17823.getservicedemo.Api.RecommendeApi;
import com.example.a17823.getservicedemo.OtherController.UpBookHot;
import com.example.a17823.getservicedemo.OtherController.UpReadHistory;
import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.Service.AdvertiseService;
import com.example.a17823.getservicedemo.Service.IsFirstService;
import com.example.a17823.getservicedemo.Service.IsReadService;
import com.example.a17823.getservicedemo.Service.RecommendeServise;
import com.example.a17823.getservicedemo.adapter.MainAdpter;
import com.example.a17823.getservicedemo.entities.BOOK;
import com.example.a17823.getservicedemo.entities.IsTrueBean;
import com.example.a17823.getservicedemo.entities.LoadViewBean;
import com.example.a17823.getservicedemo.ui.Activity.Read;
import com.example.a17823.getservicedemo.ui.Activity.ReadActivity;
import com.example.a17823.getservicedemo.ui.Activity.WebViewActivity;

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
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<BOOK> book=new ArrayList<>();
    private BannerView mBanner;
    private List<BannerEntity> bannerEntities = new ArrayList<>();
    private List<LoadViewBean> viewBeans=new ArrayList<>();
    private List<BOOK> viewBeans_rUser=new ArrayList<>();
    private ImageView imageView;
    String agrs1=null;

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
            agrs1 = bundle.getString("agrs1");
            Run_main_data(view);
            Run_main_booklist(view);
            initRefresh();
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
                //Log.i("首页推荐:",response.body().getBook().get(0).getName());
                if(response.body().getBook()!=null){
                    Log.i("在Recommende的if","ok");
                    for(int i=0;i<response.body().getBook().size();i++){
                        book.add(response.body().getBook().get(i));
                    }
                    InitBookDta(book);
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

    //首页热度书籍绑定数据
    public void InitBookDta(final List<BOOK> bookList){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(linearLayoutManager);
                final MainAdpter mainAdpter=new MainAdpter(getActivity(),bookList);
                mainAdpter.SetOnItemClickListener(new MainAdpter.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view,final int position) {
                        if(agrs1==null){
                            Log.i("MainFragment中的agrs1为空","");
                            Intent intent=new Intent(getActivity(), ReadActivity.class);
                            intent.putExtra("book_id",bookList.get(position).getId());
                            intent.putExtra("book_name", bookList.get(position).getName());
                            startActivity(intent);
                        }else{
                            Log.i("MainFragment中的agrs1不为空",agrs1);
                            //查询该用户是否阅读过点击的书籍
                            IsReadApi isReadApi=new IsReadApi();
                            IsReadService isReadService=isReadApi.getService();
                            Call<IsTrueBean> isTrueBeanCall=isReadService.getState(agrs1,bookList.get(position).getName());
                            isTrueBeanCall.enqueue(new Callback<IsTrueBean>() {
                                @Override
                                public void onResponse(Call<IsTrueBean> call, Response<IsTrueBean> response) {
                                    if(response.body().getResult().equals("true")){
                                        //用户已阅读此书
                                        Log.i("用户"+agrs1,"已阅读过"+bookList.get(position).getName());
                                        Intent intent=new Intent(getActivity(), ReadActivity.class);
                                        intent.putExtra("book_id",bookList.get(position).getId());
                                        intent.putExtra("book_name", bookList.get(position).getName());
                                        startActivity(intent);
                                    }else{
                                        Log.i("用户"+agrs1,"没阅读过"+bookList.get(position).getName());
                                        //更新书籍阅读量且跳转阅读界面
                                        UpBookHot upBookHot=new UpBookHot();
                                        upBookHot.UpHot(bookList.get(position).getName());
                                        //添加书籍到阅读记录
                                        UpReadHistory upReadHistory=new UpReadHistory();
                                        upReadHistory.UpHistory(agrs1,bookList.get(position).getName());
                                        Intent intent=new Intent(getActivity(), ReadActivity.class);
                                        intent.putExtra("book_id",bookList.get(position).getId());
                                        intent.putExtra("book_name", bookList.get(position).getName());
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<IsTrueBean> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                mRecyclerView.setAdapter(mainAdpter);
            }
        },1000);
    }

    //轮播推荐
    private void Run_main_data(View view){
        mBanner=view.findViewById(R.id.bannerView);

        if(agrs1==""){

            Log.i("轮播推荐用户id为空","");

            RecommendeApi recommendeApi=new RecommendeApi();
            RecommendeServise recommendeServise=recommendeApi.getService();
            Call<BOOK> bookCall=recommendeServise.getState();
            bookCall.enqueue(new Callback<BOOK>() {
                @Override
                public void onResponse(Call<BOOK> call, Response<BOOK> response) {
                    if(response.body()!=null){
                        for (int i = 0; i<3; i++) {
                            viewBeans_rUser.add(response.body().getBook().get(i));
                            BannerEntity bannerEntity = new BannerEntity();
                            //bannerEntity.imageUrl = response.body().getBook().get(i).getPic();
                            bannerEntity.imageUrl ="http://1oz9819419.iask.in:44216/getImage"+viewBeans_rUser.get(i).getPic();
                            bannerEntity.title = response.body().getBook().get(i).getName();
                            bannerEntities.add(bannerEntity);
                        }
                        if (bannerEntities.size()!=0)
                            mBanner.setEntities(bannerEntities);
                        mBanner.setOnBannerClickListener(new OnBannerClickListener() {
                            @Override
                            public void onClick(int position) {
                                Log.i("点击广告位", String.valueOf(position));
                                Intent intent=new Intent(getActivity(), ReadActivity.class);
                                //intent.putExtra("Url",viewBeans.get(position).getName());
                                intent.putExtra("book_id",viewBeans_rUser.get(position).getId());
                                intent.putExtra("book_name", viewBeans_rUser.get(position).getName());
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<BOOK> call, Throwable t) {

                }
            });
        }else{
            //agrs1="123";
            Log.i("轮播推荐用户id",agrs1);
            IsFirstApi isFirstApi=new IsFirstApi();
            IsFirstService isFirstService=isFirstApi.getService();
            Call<IsTrueBean> call=isFirstService.getState(agrs1);
            call.enqueue(new Callback<IsTrueBean>() {
                @Override
                public void onResponse(Call<IsTrueBean> call, Response<IsTrueBean> response) {
                    if(response.body().getResult().equals("true")){
                        //用户第一次写入阅读记录
                        RecommendeApi recommendeApi=new RecommendeApi();
                        RecommendeServise recommendeServise=recommendeApi.getService();
                        Call<BOOK> bookCall=recommendeServise.getState();
                        bookCall.enqueue(new Callback<BOOK>() {
                            @Override
                            public void onResponse(Call<BOOK> call, Response<BOOK> response) {
                                if(response.body()!=null){
                                    for (int i = 0; i<3; i++) {
                                        viewBeans_rUser.add(response.body().getBook().get(i));
                                        BannerEntity bannerEntity = new BannerEntity();
                                        //bannerEntity.imageUrl = response.body().getBook().get(i).getPic();
                                        bannerEntity.imageUrl ="http://1oz9819419.iask.in:44216/getImage"+viewBeans_rUser.get(i).getPic();
                                        bannerEntity.title = response.body().getBook().get(i).getName();
                                        bannerEntities.add(bannerEntity);
                                    }
                                    if (bannerEntities.size()!=0)
                                        mBanner.setEntities(bannerEntities);
                                    mBanner.setOnBannerClickListener(new OnBannerClickListener() {
                                        @Override
                                        public void onClick(int position) {
                                            Log.i("点击广告位", String.valueOf(position));
                                            Intent intent=new Intent(getActivity(), ReadActivity.class);
                                            //intent.putExtra("Url",viewBeans.get(position).getName());
                                            intent.putExtra("book_id",viewBeans_rUser.get(position).getId());
                                            intent.putExtra("book_name", viewBeans_rUser.get(position).getName());
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<BOOK> call, Throwable t) {

                            }
                        });
                    }else{
                        AdvertiseApi advertiseApi=new AdvertiseApi();
                        AdvertiseService advertiseService=advertiseApi.getService();
                        Call<LoadViewBean> viewBeanCall=advertiseService.getState(agrs1);

                        viewBeanCall.enqueue(new Callback<LoadViewBean>() {
                            @Override
                            public void onResponse(Call<LoadViewBean> call, final Response<LoadViewBean> response_user) {
                                if(!response_user.body().equals(null)){
                                    Log.i("广告轮播if","emmmmmmmmm");
                                    for (int i = 0; i<response_user.body().getBook().size(); i++) {
                                        viewBeans.add(response_user.body().getBook().get(i));
                                        BannerEntity bannerEntity = new BannerEntity();
                                        bannerEntity.imageUrl ="http://1oz9819419.iask.in:44216/getImage"+viewBeans.get(i).getPic();
                                        bannerEntity.title = response_user.body().getBook().get(i).getName();
                                        bannerEntities.add(bannerEntity);
                                    }
                                    if (bannerEntities.size()!=0)
                                        mBanner.setEntities(bannerEntities);
                                    mBanner.setOnBannerClickListener(new OnBannerClickListener() {
                                        @Override
                                        public void onClick(int position) {
                                            Log.i("点击广告位", String.valueOf(position));
                                            Intent intent=new Intent(getActivity(), ReadActivity.class);
                                            //intent.putExtra("Url",viewBeans.get(position).getName());
                                            intent.putExtra("book_id",viewBeans.get(position).getId());
                                            intent.putExtra("book_name", viewBeans.get(position).getName());
                                            startActivity(intent);
                                        }
                                    });

                                }else{
                                    Log.i("用户尚未阅读任何书籍","emmmmmmmmm");
                                }
                            }

                            @Override
                            public void onFailure(Call<LoadViewBean> call, Throwable t) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<IsTrueBean> call, Throwable t) {

                }
            });
        }
    }

    private void initRefresh() {
        //刷新界面
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipLay);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refreshFruits();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Run_main_booklist(view);
                        //Run_main_data(view);
                        Log.i("刷新数据成功","");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1500);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}