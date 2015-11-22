package com.mb.mmdepartment.adapter.helpcalculate;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.market_sel.AreaMarketSelData;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2015/11/22.
 */
public class AreasMarketListAdapter extends RecyclerView.Adapter<AreasMarketListAdapter.MyViewHolder>{
    private List<AreaMarketSelData> list;
    public AreasMarketListAdapter(List<AreaMarketSelData> list) {
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.areas_market_sel_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position).getShop_name2());
        holder.tv_address.setText(list.get(position).getAddres());
        String distance=list.get(position).getDistance();
        if (!TextUtils.isEmpty(distance)) {
            double dis = Double.parseDouble(distance);
            dis=Math.floor(dis*10d)/10;
            holder.tv_distance.setText(String.valueOf(dis));
        } else {
            holder.tv_distance.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_address,tv_distance;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name=(TextView)itemView.findViewById(R.id.areas_market_item_name);
            tv_address=(TextView)itemView.findViewById(R.id.areas_market_item_address);
            tv_distance=(TextView)itemView.findViewById(R.id.distance);
        }
    }
}
