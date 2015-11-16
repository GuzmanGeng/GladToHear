package com.mb.mmdepartment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.FunctionItem;

import java.util.List;

/**
 * Created by Administrator on 2015/8/27.
 */
public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.MyViewHolder>{
    private List<FunctionItem> functionItems;
    private OnItemClickListener onItemClickListener;
    public FunctionAdapter(List<FunctionItem> functionItems,OnItemClickListener onItemClickListener){
        this.functionItems=functionItems;
        this.onItemClickListener=onItemClickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_function_item,parent,false);
        holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String url = ImageDownloader.Scheme.DRAWABLE.wrap(functionItems.get(position).getUrl());
        String title=functionItems.get(position).getTitle();
        ImageLoader.getInstance().displayImage(url,holder.iv_function);
        holder.tv_title.setText(title);
        holder.iv_function.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(holder.iv_function,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return functionItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_function;
        private TextView tv_title;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_function= (ImageView) itemView.findViewById(R.id.main_function_iv);
            tv_title= (TextView) itemView.findViewById(R.id.main_function_tv);
        }
    }
    public interface OnItemClickListener{
        public void onClick(View view,int position);
    }
}
