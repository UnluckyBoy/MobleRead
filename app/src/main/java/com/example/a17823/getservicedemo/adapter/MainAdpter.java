package com.example.a17823.getservicedemo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a17823.getservicedemo.R;
import com.example.a17823.getservicedemo.entities.BOOK;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 清风明月 on 2018/6/8.
 */

public class MainAdpter extends RecyclerView.Adapter<MainAdpter.ViewHolder>{

    private List<BOOK> bookBean;
    //private List<String> bookBean;
    private Activity mActivity;

    public MainAdpter(Activity mActivity,List<BOOK> list){
        this.bookBean=list;
        this.mActivity=mActivity;

    }
    @Override
    public MainAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MainAdpter.ViewHolder holder, int position) {

        if(bookBean.get(position)!=null){
            //SetPic(holder,"http://192.168.16.111:8080/getImage"+bookBean.get(position).getB_Pic());
            SetPic(holder,"http://1oz9819419.iask.in:44216/getImage"+bookBean.get(position).getPic());
        }

        holder.book_title.setText(bookBean.get(position).getName());
        holder.book_hot.setText(bookBean.get(position).getHot());
        holder.writer.setText(bookBean.get(position).getWriter());
        holder.type.setText(bookBean.get(position).getType());

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

    private void SetPic(MainAdpter.ViewHolder viewHolder,String path){
        if(path!=null){
            Picasso.with(mActivity)
                    .load(path)
                    .placeholder(R.drawable.warning)
                    .error(R.drawable.warning)
                    .into(viewHolder.book_pic);
        }
    }

    @Override
    public int getItemCount() {
        return bookBean.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView book_pic;
        private TextView book_hot;
        private TextView book_title;
        private TextView writer;
        private TextView type;
        public ViewHolder(View itemView) {
            super(itemView);
            book_pic=itemView.findViewById(R.id.book_image);
            book_hot=itemView.findViewById(R.id.hot);
            book_title=itemView.findViewById(R.id.book_title);
            writer=itemView.findViewById(R.id.b_writer);
            type=itemView.findViewById(R.id.b_type);
        }
    }

    public interface OnItemClickListener{
        void  onItemClick(View view, int position);
        void  onItemLongClick(View view, int position);
    }
    private MainAdpter.OnItemClickListener OnItemClickListener;
    public void SetOnItemClickListener(MainAdpter.OnItemClickListener onItemClickListener){
        OnItemClickListener =onItemClickListener;
    }
}