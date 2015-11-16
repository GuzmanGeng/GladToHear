package com.mb.mmdepartment.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.mb.mmdepartment.R;
/**
 * Created by Administrator on 2015/8/26.
 */
public class ShopingCarAdapter extends RecyclerView.Adapter<ShopingCarAdapter.MyHolder>{
    private LayoutInflater inflater;
    private Context context;
    public ShopingCarAdapter(Context context){
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public ShopingCarAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType==1){
            v=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.marketsel_content_item, parent, false);
        }else {
            v=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.marketsel_name_item, parent, false);
        }
        MyHolder vh = new MyHolder(v,viewType);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?0:1;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (getItemViewType(position)==1) {
            holder.market_content_item_old_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
            BadgeView badgeView = new BadgeView(context);
            badgeView.setTargetView(holder.tv_sheng);
            badgeView.setBadgeGravity(Gravity.TOP | Gravity.LEFT);
//            badgeView.setTypeface(Typeface.create(Typeface.SANS_SERIF,
//                    Typeface.ITALIC));
            badgeView.setText("省");
        }
    }
    @Override
    public int getItemCount() {
        return 10;
    }
    class MyHolder extends RecyclerView.ViewHolder{
        private TextView tv_sheng;
        private TextView market_content_item_old_price_tv;
        public MyHolder(View itemView,int viewType) {
            super(itemView);
            if (viewType==1) {
                tv_sheng = (TextView) itemView.findViewById(R.id.shopping_sheng_tv);
                market_content_item_old_price_tv = (TextView) itemView.findViewById(R.id.market_content_item_old_price_tv);
            }
        }
    }
}
