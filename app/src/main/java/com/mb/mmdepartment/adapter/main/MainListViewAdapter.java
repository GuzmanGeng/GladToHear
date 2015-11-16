package com.mb.mmdepartment.adapter.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.main_brand.News;
import com.mb.mmdepartment.constans.BaseConsts;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by krisi on 2015/11/4.
 */
public class MainListViewAdapter extends BaseAdapter {
    private List<News> datas;
    private Context context;
    private LayoutInflater inflater;

    public class MyViewHolder  {
        public ImageView imageView;
    }

    public MainListViewAdapter(List<News> datas,Context context){
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder ;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.main_brand_item,null);
            holder = new MyViewHolder();
            holder.imageView=(ImageView)convertView.findViewById(R.id.main_brand_iv);
            convertView.setTag(holder);
        }
        holder = (MyViewHolder)convertView.getTag();
        ImageLoader.getInstance().displayImage(datas.get(position).getImage(),holder.imageView);
        return convertView;
    }
}
