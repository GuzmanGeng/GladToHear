package com.mb.mmdepartment.adapter.main_search;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.SearchActivity;
import com.mb.mmdepartment.activities.SearchListActivity;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.biz.main_search.MainAddSearchKeyword;

import java.util.Date;
import java.util.List;
/**
 * Created by joyone2one on 2015/11/13.
 */
public class HotDataAdapter extends RecyclerView.Adapter<HotDataAdapter.MyViewHolder> {
    private List<String> list;
    private Context context;
    public HotDataAdapter(Context context,List<String> list){
        this.list=list;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_search_item,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_show_name.setText(list.get(position));
        holder.tv_show_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SearchActivity)context).LuPing(list.get(position),"other","next",new Date());
                Intent intent = new Intent(v.getContext(), SearchListActivity.class);
                intent.putExtra("keyword", list.get(position));
                v.getContext().startActivity(intent);
                MainAddSearchKeyword biz=new MainAddSearchKeyword();
                if (TApplication.user_id == null) {
                    biz.addHistory("1", list.get(position));
                } else {
                    biz.addHistory(TApplication.user_id, list.get(position));
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_show_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_show_name=(TextView)itemView.findViewById(R.id.goods_sel_item_tv);
        }
    }
}
