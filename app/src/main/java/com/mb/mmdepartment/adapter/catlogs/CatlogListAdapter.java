package com.mb.mmdepartment.adapter.catlogs;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.commodity.Type;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15 0015.
 */
public class CatlogListAdapter extends RecyclerView.Adapter<CatlogListAdapter.MyViewHolder>{
    private List<Type> list;
    private OnItemClickListener listener;
    private int firstPosition;
    private SpannableStringBuilder style;
    public CatlogListAdapter(List<Type> list,OnItemClickListener listener) {
        this.list = list;
        this.listener=listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.catlog_sel_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);

        return holder;
    }
//    public void getFirstViewPosition(int position){
//        firstPosition=position;
//        String title=list.get(position).getTitle();
//        style=new SpannableStringBuilder(title);
//        style.setSpan(new ForegroundColorSpan(Color.RED), 0, title.length() - 1, 0);
//        notifyItemChanged(position);
//    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String title=list.get(position).getTitle();
        holder.tv.setText(title);
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position);
                Log.i("test", "test" + position);
                if(holder.choosen == 0) {
                    holder.tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    holder.choosen = 1;
                }
//                else{
//                    holder.tv.setBackgroundColor(Color.parseColor("#CFDDDDDD"));
//                    holder.choosen = 0;
//                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        int choosen  = 0;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv=(TextView)itemView.findViewById(R.id.calog_sel_item_tv);
        }
    }
    public interface OnItemClickListener{
        void onClick(int position);
    }
}
