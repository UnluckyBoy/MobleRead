package com.example.a17823.getservicedemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.entities.LoadallBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 清风明月 on 2018/6/5.
 */

public class LoadallAdapter extends RecyclerView.Adapter<LoadallAdapter.ViewHolder>{

    private List<LoadallBean> bookBean;
    private Activity mActivity;

    public LoadallAdapter(Activity mActivity, List<LoadallBean> bookBean){
        this.mActivity =mActivity;
        this. bookBean=bookBean;
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(final LoadallAdapter.ViewHolder holder, int position) {
        //String title=bookBean.get(position).
        if(bookBean.get(position)!=null){
            //SetPic(holder,"http://192.168.16.111:8080/getImage"+bookBean.get(position).getB_Pic());
            SetPic(holder,"http://1oz9819419.iask.in:44216/getImage"+bookBean.get(position).getB_Pic());
        }

        holder.book_title.setText(bookBean.get(position).getB_Name());
        holder.writer.setText(bookBean.get(position).getB_Writer());
        holder.type.setText(bookBean.get(position).getB_Type());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getLayoutPosition();
                OnItemClickListener.onItemClick(holder.itemView,position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = holder.getLayoutPosition();
                OnItemClickListener.onItemLongClick(holder.itemView,position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookBean.size();
        //return 0;
    }

    private void SetPic(LoadallAdapter.ViewHolder viewHolder,String path){
        if(path!=null){
            Picasso.with(mActivity)
                    .load(path)
                    .placeholder(R.drawable.warning)
                    .error(R.drawable.warning)
                    .into(viewHolder.book_firstpage);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView book_firstpage;
        private TextView book_title;
        private TextView writer;
        private TextView type;
        //public ImageView book_pic;

        public ViewHolder(View itemView) {
            super(itemView);
            book_firstpage=itemView.findViewById(R.id.book_image);
            book_title=itemView.findViewById(R.id.book_title);
            writer=itemView.findViewById(R.id.b_writer);
            type=itemView.findViewById(R.id.b_type);
        }
    }
    public interface OnItemClickListener{
        void  onItemClick(View view, int position);
        void  onItemLongClick(View view, int position);
    }
    private OnItemClickListener OnItemClickListener;
    public void SetOnItemClickListener(OnItemClickListener onItemClickListener){
        OnItemClickListener =onItemClickListener;
    }
}