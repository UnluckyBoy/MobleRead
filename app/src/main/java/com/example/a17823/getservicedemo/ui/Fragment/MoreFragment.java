package com.example.a17823.getservicedemo.ui.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.a17823.getservicedemo.Api.IsReadApi;
import com.example.a17823.getservicedemo.Api.LoadallApi;
import com.example.a17823.getservicedemo.OtherController.UpBookHot;
import com.example.a17823.getservicedemo.OtherController.UpReadHistory;
import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.Service.IsReadService;
import com.example.a17823.getservicedemo.Service.LoadallService;
import com.example.a17823.getservicedemo.adapter.LoadallAdapter;
import com.example.a17823.getservicedemo.entities.IsTrueBean;
import com.example.a17823.getservicedemo.entities.LoadallBean;
import com.example.a17823.getservicedemo.ui.Activity.ReadActivity;
import com.example.a17823.getservicedemo.ui.Activity.SearchBookActivity;
import com.example.a17823.getservicedemo.ui.Activity.TypeSearchActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import com.example.a17823.getservicedemo.util.TypeSearchBook;

/**
 * Created by 17823 on 2017/12/23.
 */

public class MoreFragment extends Fragment {
    private Button xianxia,wuxia,dushi,xuanhuan,lishi,lingyi,game,qihuan,mingzhu,other;
    private View v;
    private ImageButton search_text;
    private EditText editText;
    private RecyclerView book_list;
    private ImageView imageView;
    //List<String> mDatas=new ArrayList<String>();
    private List<LoadallBean> bookBean=new ArrayList<>();
    String agrs1=null;

    public static MoreFragment newInstance(String param1) {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MoreFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        if(v!=null){
            ViewGroup parent=(ViewGroup) v.getParent();
            if(parent!=null){
                parent.removeView(v);
            }
        }else{
            v=inflater.inflate(R.layout.more,container,false);

            Bundle bundle = getArguments();
            agrs1 = bundle.getString("agrs1");
            if(agrs1!=null){
                Log.i("MoreFragment中的agrs1=",agrs1);
            }

            Run_Book(v);
            Run_Loadall(v);
        }
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void Run_Loadall(View v) {
        book_list=(RecyclerView) v.findViewById(R.id.loadall_list);
        imageView=v.findViewById(R.id.errorImage);
        //final String[] name = {null};
        LoadallApi loadallApi=new LoadallApi();
        LoadallService loadallService=loadallApi.getService();
        Call<LoadallBean> loadallBeanCall_list=loadallService.getState();
        loadallBeanCall_list.enqueue(new Callback<LoadallBean>() {
            @Override
            public void onResponse(Call<LoadallBean> call, final Response<LoadallBean> response) {
                if(response.body()!=null){
                    Log.i("Loadall","load...");
                    for(int i=0;i<response.body().getBooklist().size();i++){
                        //mDatas.add(response.body().getBooklist().get(i).getB_Name());
                        bookBean.add(response.body().getBooklist().get(i));
                    }
                    InitBookData(bookBean);
                }
            }

            @Override
            public void onFailure(Call<LoadallBean> call, Throwable t) {
                imageView.setImageResource(R.drawable.notdata);
                //Toast.makeText(getActivity(),"网络错误！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Run_Book(View v){

        if(agrs1!=null){
            Log.i("Fragment中Run_Book的",agrs1);
        }
        editText=(EditText)v.findViewById(R.id.editText);
        search_text=(ImageButton)v.findViewById(R.id.search);
        xianxia=(Button)v.findViewById(R.id.xianxia);
        wuxia=(Button)v.findViewById(R.id.wuxia);
        dushi=(Button)v.findViewById(R.id.dushi);
        xuanhuan=(Button)v.findViewById(R.id.xuanhuan);
        lishi=(Button)v.findViewById(R.id.history);
        lingyi=(Button)v.findViewById(R.id.lingyi);
        game=(Button)v.findViewById(R.id.game);
        qihuan=(Button)v.findViewById(R.id.qihuan);
        mingzhu=(Button)v.findViewById(R.id.mingzhu);
        other=(Button)v.findViewById(R.id.other);
        xianxia.setOnClickListener(new TypeSearchBook());
        wuxia.setOnClickListener(new TypeSearchBook());
        dushi.setOnClickListener(new TypeSearchBook());
        xuanhuan.setOnClickListener(new TypeSearchBook());
        lishi.setOnClickListener(new TypeSearchBook());
        lingyi.setOnClickListener(new TypeSearchBook());
        game.setOnClickListener(new TypeSearchBook());
        qihuan.setOnClickListener(new TypeSearchBook());
        mingzhu.setOnClickListener(new TypeSearchBook());
        other.setOnClickListener(new TypeSearchBook());
        search_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String text=editText.getText().toString();
                Intent intent_searchbook=new Intent(getActivity(), SearchBookActivity.class);
                intent_searchbook.putExtra("bookname",text);
                startActivity(intent_searchbook);
            }
        });
    }

    private class TypeSearchBook implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final int Key = view.getId();
            String typeKey=null;
            boolean getResult = false;
            switch (Key) {
                case R.id.xianxia:
                    //Toast.makeText(view.getContext(), "点击了仙侠按钮", Toast.LENGTH_SHORT).show();
                    typeKey="仙侠";
                    getResult = true;
                    break;
                case R.id.wuxia:
                    typeKey="武侠";
                    getResult = true;
                    break;
                case R.id.dushi:
                    typeKey="都市";
                    getResult = true;
                    break;
                case R.id.xuanhuan:
                    typeKey="玄幻";
                    getResult = true;
                    break;
                case R.id.history:
                    typeKey="历史";
                    getResult = true;
                    break;
                case R.id.lingyi:
                    typeKey="灵异";
                    getResult = true;
                    break;
                case R.id.game:
                    typeKey="游戏";
                    getResult = true;
                    break;
                case R.id.qihuan:
                    typeKey="奇幻";
                    getResult = true;
                    break;
                case R.id.mingzhu:
                    typeKey="名著";
                    getResult = true;
                    break;
                case R.id.other:
                    typeKey="其他";
                    getResult = true;
                    break;
            }
            if(getResult){
                Log.i("goTypeSearch","1111111");
                Intent intent_typeSearch=new Intent(getActivity(), TypeSearchActivity.class);
                intent_typeSearch.putExtra("type",typeKey);
                startActivity(intent_typeSearch);
            }
        }
    }

    //绑定数据
    public void InitBookData(final List<LoadallBean> bookList){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                book_list.setLayoutManager(linearLayoutManager);
                LoadallAdapter loadallAdapter = new LoadallAdapter(getActivity(),bookList);
                loadallAdapter.SetOnItemClickListener(new LoadallAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, final int position) {
                        //if((agrs1!=null)&&(bookList.get(position).getB_Name().equals(getRecord.getRecord(agrs1)))){
                        if(agrs1==null){
                            Log.i("MoreFragment中的agrs1为空","");
                            Intent intent=new Intent(getActivity(), ReadActivity.class);
                            intent.putExtra("book_id",bookList.get(position).getB_ID());
                            intent.putExtra("book_name", bookList.get(position).getB_Name());
                            startActivity(intent);
                        }else{
                            Log.i("MoreFragment中的agrs1不为空",agrs1);
                            //IsRead(agrs1,bookList.get(position).getB_Name());
                            IsReadApi isReadApi=new IsReadApi();
                            IsReadService isReadService=isReadApi.getService();
                            Call<IsTrueBean> isTrueBeanCall=isReadService.getState(agrs1,bookList.get(position).getB_Name());
                            isTrueBeanCall.enqueue(new Callback<IsTrueBean>() {
                                @Override
                                public void onResponse(Call<IsTrueBean> call, Response<IsTrueBean> response) {
                                    if(response.body().getResult().equals("true")){
                                        //用户已阅读此书
                                        Log.i("用户"+agrs1,"已阅读过"+bookList.get(position).getB_Name());
                                        Intent intent=new Intent(getActivity(), ReadActivity.class);
                                        intent.putExtra("book_id",bookList.get(position).getB_ID());
                                        intent.putExtra("book_name", bookList.get(position).getB_Name());
                                        startActivity(intent);
                                    }else{
                                        Log.i("用户"+agrs1,"没阅读过"+bookList.get(position).getB_Name());
                                        UpBookHot upBookHot=new UpBookHot();
                                        upBookHot.UpHot(bookList.get(position).getB_Name());
                                        //添加书籍到阅读记录
                                        UpReadHistory upReadHistory=new UpReadHistory();
                                        upReadHistory.UpHistory(agrs1,bookList.get(position).getB_Name());

                                        Intent intent=new Intent(getActivity(), ReadActivity.class);
                                        intent.putExtra("book_id",bookList.get(position).getB_ID());
                                        intent.putExtra("book_name", bookList.get(position).getB_Name());
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
                book_list.setAdapter(loadallAdapter);
            }
        },1000);
    }
}