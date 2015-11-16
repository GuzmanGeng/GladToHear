package com.mb.mmdepartment.adapter.catlogs;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.helpcheck.brandsel.Type;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15 0015.
 */
public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.MyViewHolder> {
    private List<String> list;
    private List<Type> types;
    private OnItemClickListener listener;
    private OnHotItemClickListener itemClickListener;
    private boolean isTrue;
    public GoodsListAdapter(List<String> list,OnItemClickListener listener) {
        this.list = list;
        this.listener=listener;
    }
    public GoodsListAdapter(List<Type> types,OnHotItemClickListener itemClickListener,boolean isTrue) {
        this.types = types;
        this.itemClickListener=itemClickListener;
        this.isTrue=isTrue;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_list_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (isTrue) {
            holder.tv.setText(types.get(position).getTitle());
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onHotItemClick(types.get(position).getTitle(),types.get(position).getCategory_id());
                }
            });
        }else {
            holder.tv.setText(list.get(position));
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        if (isTrue) {
            return types.size();
        }
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv=(TextView)itemView.findViewById(R.id.goods_sel_item_tv);
        }
    }
    public interface OnItemClickListener{
        void onClick(View view);
    }
    public  interface OnHotItemClickListener{
        void onHotItemClick(String title,String keyword);
    }
}
