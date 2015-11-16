package com.mb.mmdepartment.adapter.commodity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.commodity.Category;
import com.mb.mmdepartment.bean.commodity.SelRecord;
import com.mb.mmdepartment.listener.CommodityRightItemClickListener;
import com.mb.mmdepartment.listener.CommoditySelectedListener;
import com.mb.mmdepartment.listener.OnRecycItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2015/9/28 0028.
 */
public class CommoditySelectedAdapter extends RecyclerView.Adapter<CommoditySelectedAdapter.MyViewHolder> {
    private List<SelRecord> records;
    private CommoditySelectedListener listener;
    private int index;
    public CommoditySelectedAdapter(List<SelRecord> records, CommoditySelectedListener listener) {
        this.records = records;
        this.listener=listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String title=records.get(position).getTitle();
        holder.tv_title.setText(title);
        holder.tv_title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClick(records.get(position));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title=(TextView)itemView.findViewById(R.id.goods_sel_item_tv);
        }
    }
}
