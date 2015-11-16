package com.mb.mmdepartment.adapter.userspace;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.userspace.listrecord.getexchangeprizerecord.Exchange;
import com.mb.mmdepartment.listener.ExchangePrizeRecordItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public class ExchangePrizeRecordAdapter extends RecyclerView.Adapter<ExchangePrizeRecordAdapter.MyViewHolder> {
    private List<Exchange> exchanges;
    private ExchangePrizeRecordItemClickListener listener;
    public ExchangePrizeRecordAdapter(List<Exchange> exchanges, ExchangePrizeRecordItemClickListener listener){
        this.exchanges=exchanges;
        this.listener=listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_space_fragment_list_ittem, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Exchange exchange=exchanges.get(position);
        holder.listrecord_list_tv.setText(""+(position+1));
        holder.listrecord_number_tv.setText(exchange.getOrder_number());
        String data=exchange.getCtime().substring(2,9);
//        int index=data.indexOf("-", 1);
//        data=data.substring(index+1, data.length()-1);
        holder.listrecord_time_tv.setText(data);
        holder.listrecord_money_tv.setText(exchange.getGoods_name().substring(0,4)+"……");
        holder.listrecord_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(null,exchange);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exchanges.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView listrecord_list_tv,listrecord_number_tv,listrecord_time_tv,listrecord_money_tv;
        public CardView listrecord_cardview;
        public MyViewHolder(View itemView) {
            super(itemView);
            listrecord_list_tv=(TextView)itemView.findViewById(R.id.listrecord_list_tv);
            listrecord_time_tv=(TextView)itemView.findViewById(R.id.listrecord_time_tv);
            listrecord_number_tv=(TextView)itemView.findViewById(R.id.listrecord_number_tv);
            listrecord_money_tv=(TextView)itemView.findViewById(R.id.listrecord_money_tv);
            listrecord_cardview = (CardView) itemView.findViewById(R.id.listrecord_cardview);
        }
    }
}
