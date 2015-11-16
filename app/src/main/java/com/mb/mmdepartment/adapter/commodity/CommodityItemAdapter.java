package com.mb.mmdepartment.adapter.commodity;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.commodity.Category;
import com.mb.mmdepartment.listener.CommodityRightItemClickListener;
import java.util.List;

/**
 * Created by Administrator on 2015/9/28 0028.
 */
public class CommodityItemAdapter extends RecyclerView.Adapter<CommodityItemAdapter.MyViewHolder> {
    private List<Category> categories;
    private CommodityRightItemClickListener listener;
    private int index;
    private int selWhich;
    public CommodityItemAdapter(List<Category> categories,CommodityRightItemClickListener listener,int index,int selWhich) {
        this.categories = categories;
        this.listener=listener;
        this.index=index;
        this.selWhich=selWhich;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String title=categories.get(position).getTitle();
        final int category_id = Integer.parseInt(categories.get(position).getCategory_id());
        holder.tv_title.setText(title);
        final String hid=categories.get(position).getHid().substring(0,6);
        Log.e("hid", hid);
        if ("0".equals(categories.get(position).getSelect())) {
            holder.tv_title.setBackgroundResource(R.drawable.cicle_back_goods_list);
            holder.tv_title.setTextColor(holder.tv_title.getResources().getColor(R.color.text_half_red));
        }else if("1".equals(categories.get(position).getSelect())){
            holder.tv_title.setBackgroundResource(R.drawable.cicle_back_login_tv);
            holder.tv_title.setTextColor(holder.tv_title.getResources().getColor(R.color.color_white));
        }
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCommodityRightItemClickListener(title,category_id,hid,position,index,selWhich);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title=(TextView)itemView.findViewById(R.id.goods_sel_item_tv);
        }
    }
}
