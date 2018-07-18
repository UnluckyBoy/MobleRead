package com.example.a17823.getservicedemo.ui.Fragment;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a17823.getservicedemo.Api.UpHeadApi;
import com.example.a17823.getservicedemo.Api.UpNameApi;
import com.example.a17823.getservicedemo.Api.UserIfoApi;
import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.Service.UpHeadService;
import com.example.a17823.getservicedemo.Service.UpnameService;
import com.example.a17823.getservicedemo.Service.UserIfoService;
import com.example.a17823.getservicedemo.entities.IsTrueBean;
import com.example.a17823.getservicedemo.entities.UserBean;
import com.example.a17823.getservicedemo.ui.Fragment.Activity.LoginActivity;
import com.example.a17823.getservicedemo.ui.Fragment.Activity.UserAboutActivity;
import com.example.a17823.getservicedemo.ui.Fragment.Activity.UserHelpActivity;
import com.example.a17823.getservicedemo.ui.Fragment.Activity.UserReadActivity;
import com.example.a17823.getservicedemo.ui.Fragment.Activity.UserWriterActivity;
import com.example.a17823.getservicedemo.ui.Fragment.view.CirecleImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 17823 on 2017/12/23.
 */

public class MyFragment extends Fragment{
    private CirecleImageView imageButton=null;
    private List<UserBean> userBeans=new ArrayList<>();
    private Button editBt;
    private TextView userName;
    private EditText A_Name;
    private View view;
    AlertDialog alertDialog;
    String agrs1;

    public static MyFragment newInstance(String param1) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myfragment, container, false);
        alertDialog=new AlertDialog.Builder(getActivity()).create();
        Bundle bundle = getArguments();
        agrs1 = bundle.getString("agrs1");
        /*if(view!=null){
            ViewGroup parent=(ViewGroup) view.getParent();
            if(parent!=null){
                parent.removeView(view);
            }
        }else{
            view = inflater.inflate(R.layout.mainfragment, container, false);
            Bundle bundle = getArguments();
            agrs1 = bundle.getString("agrs1");

            Userpage(view);
            UserRun(view);
        }*/
        if(agrs1!=null){
            initData(view,agrs1);
        }

        Userpage(view);
        UserRun(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initData(View view,String agrs) {
        userName=view.findViewById(R.id.userAccount);
        if(agrs!=null){
            UserIfoApi userIfoApi=new UserIfoApi();
            UserIfoService userIfoService=userIfoApi.getService();
            Call<UserBean> beanCall=userIfoService.getState(agrs);
            beanCall.enqueue(new Callback<UserBean>() {
                @Override
                public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                    if(response.body()!=null){
                        userBeans.add(response.body().getUser().get(0));
                        SetHead(response.body().getUser().get(0).getHead());
                        userName.setText(response.body().getUser().get(0).getName());
                    }
                }

                @Override
                public void onFailure(Call<UserBean> call, Throwable t) {

                }
            });
        }
    }

    private void SetHead(String headUrl) {
        Picasso.with(getActivity())
                .load("http://1oz9819419.iask.in:44216/getImage"+headUrl)
                .error(R.mipmap.nohead)
                .into(imageButton);
    }

    private void UserRun(View view) {
        Button uesr_Writer,user_Read,user_Help,user_About,user_book;
        uesr_Writer=(Button)view.findViewById(R.id.writer);
        user_Read=(Button)view.findViewById(R.id.read);
        user_Help=(Button)view.findViewById(R.id.help);
        user_About=(Button)view.findViewById(R.id.about);
        user_book=view.findViewById(R.id.userbook);
        uesr_Writer.setOnClickListener(new UserClickListener());
        user_Read.setOnClickListener(new UserClickListener());
        user_Help.setOnClickListener(new UserClickListener());
        user_About.setOnClickListener(new UserClickListener());
        user_book.setOnClickListener(new UserClickListener());
    }

    public void Userpage(View view){
        imageButton=view.findViewById(R.id.user);
        userName=view.findViewById(R.id.userName);
        editBt=view.findViewById(R.id.userEdit);
        A_Name=new EditText(getActivity());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agrs1==null){
                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }else{
                    Log.i(agrs1,"已登录！");
                    //修改头像
                    /*Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                    albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(albumIntent,2);*/
                    startActivityForResult(BGAPhotoPickerActivity.newIntent(getActivity(),
                            null, 1, null,
                            false), 55);
                    //upHead();
                }
            }
        });
        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("修改昵称提示");
                alertDialog.setView(A_Name);
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "放弃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //final String name=A_Name.getText().toString();
                        UpNameApi upNameApi=new UpNameApi();
                        UpnameService upnameService=upNameApi.getService();
                        Call<IsTrueBean> trueBeanCall=upnameService.getState(A_Name.getText().toString(),agrs1);
                        trueBeanCall.enqueue(new Callback<IsTrueBean>() {
                            @Override
                            public void onResponse(Call<IsTrueBean> call, Response<IsTrueBean> response) {
                                if(response.body().getResult().equals("1")){
                                    userName.setText(A_Name.getText());
                                }else{
                                    Log.i("修改用户姓名","失败!");
                                }
                            }

                            @Override
                            public void onFailure(Call<IsTrueBean> call, Throwable t) {

                            }
                        });
                        //userName.setText(A_Name.getText());
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == 55) {
            final String localPicturePath = BGAPhotoPickerActivity.getSelectedImages(data).get(0);
            Log.i("相册",localPicturePath);
            upHead(localPicturePath);
        }
    }

    public void upHead(String imagepath){
        UpHeadApi upHeadApi=new UpHeadApi();
        UpHeadService upHeadService=upHeadApi.getService();
        File file=new File(imagepath);
        RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body=MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        //final RequestBody account=RequestBody.create(null,agrs1);
        Call<IsTrueBean> call=upHeadService.getState(agrs1,body);
        call.enqueue(new Callback<IsTrueBean>() {
            @Override
            public void onResponse(Call<IsTrueBean> call, Response<IsTrueBean> response) {
                if(response.body().getResult().equals("1")){
                    Log.i("Change head seccess",agrs1);
                }else{
                    Log.i("failed在else里面",agrs1);
                }
            }

            @Override
            public void onFailure(Call<IsTrueBean> call, Throwable t) {
                Log.i("Change head failed",agrs1);
            }
        });
    }
    private class UserClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final int button_id=view.getId();
            Intent intent_writer,intent_read,intent_help,intent_about;
            switch(button_id){
                case R.id.writer:
                    intent_writer=new Intent(getActivity(), UserWriterActivity.class);
                    startActivity(intent_writer);
                    break;
                case R.id.read:
                    intent_read=new Intent(getActivity(), UserReadActivity.class);
                    startActivity(intent_read);
                    break;
                case R.id.help:
                    intent_help=new Intent(getActivity(), UserHelpActivity.class);
                    startActivity(intent_help);
                    break;
                case R.id.about:
                    intent_about=new Intent(getActivity(), UserAboutActivity.class);
                    startActivity(intent_about);
                    break;
                case R.id.userbook:
                    break;
            }
        }
    }
}