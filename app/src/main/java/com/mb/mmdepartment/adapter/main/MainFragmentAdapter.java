package com.mb.mmdepartment.adapter.main;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.main_brand.News;
import com.mb.mmdepartment.listener.AccumulateShopItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2015/8/27.
 */
public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.MyViewHolder> {
    private AccumulateShopItemClickListener listener;
    private List<News> list;
    public MainFragmentAdapter(List<News> list,AccumulateShopItemClickListener listener) {
        this.list=list;
        this.listener = listener;
        Log.e("main_size",list.size()+"");
    }

    @Override
    public MainFragmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_brand_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MainFragmentAdapter.MyViewHolder holder, final int position) {
        ImageLoader.getInstance().displayImage(list.get(position).getImage(), holder.imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                holder.imageView.setImageResource(R.mipmap.loading);
            }
            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                holder.imageView.setImageResource(R.mipmap.loading);
//                Toast.makeText(view.getContext(),s,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (!"".equals(s)){
                    holder.imageView.setImageBitmap(bitmap);
                }else {
                    holder.imageView.setImageResource(R.mipmap.loading);
                }
            }
            @Override
            public void onLoadingCancelled(String s, View view) {
//                Toast.makeText(view.getContext(),s,Toast.LENGTH_SHORT).show();
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(null,list.get(position).getContent_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.main_brand_iv);
        }
    }
}
