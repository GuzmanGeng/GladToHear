package com.mb.mmdepartment.adapter.marcket;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.MarcketSelDetailCallback;

import java.util.List;

/**
 * Created by Administrator on 2015/9/29 0029.
 */
public class MarcketSelDetailAdapter extends RecyclerView.Adapter<MarcketSelDetailAdapter.MyViewHolder> {
    private List<Lists> listses;
    private MarcketSelDetailCallback callback;
    private int which;
    public MarcketSelDetailAdapter(List<Lists> listses,MarcketSelDetailCallback callback,int which) {
        this.listses = listses;
        this.callback=callback;
        this.which=which;
    }

    @Override
    public MarcketSelDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marcket_sel_detail_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MarcketSelDetailAdapter.MyViewHolder holder, final int position) {
        String name=listses.get(position).getName();
        String activity = listses.get(position).getInfo();
        String standard=listses.get(position).getStandard();
        String f_price=listses.get(position).getF_price();
        String o_price=listses.get(position).getO_price();

        String once_shop = listses.get(position).getOne_shop();
        if (which==0) {
            String marcket = listses.get(position).getShop_name();
            holder.marcket_sel_detail_item_marcket_tv.setText(marcket);
            String item=listses.get(position).getItem();
            holder.marcket_sel_detail_item_count_tv.setText(item);
        }else if (which == 1) {
            String marcket = listses.get(position).getShop_name();
            holder.marcket_sel_detail_item_marcket_tv.setText(marcket);
        }
        final String url= BaseConsts.BASE_IMAGE_URL+listses.get(position).getPic();

        ImageLoader.getInstance().displayImage(url, holder.marcket_sel_detail_item_iv, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                ((ImageView) view).setImageResource(R.mipmap.loading_a);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                ((ImageView) view).setImageResource(R.mipmap.loading_a);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (url == null) {
                    ((ImageView) view).setImageResource(R.mipmap.loading_a);
                } else {
                    ((ImageView) view).setImageBitmap(bitmap);
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });

        holder.marcket_sel_detail_item_title_tv.setText(name);
        if ("".equals(standard)) {
            holder.marcket_sel_detail_item_standard_tv.setText("规格待定");
        } else {
            holder.marcket_sel_detail_item_standard_tv.setText(standard);
        }
        holder.marcket_sel_detail_item_new_price_tv.setText("￥:" + f_price);
        holder.marcket_sel_detail_item_del_price_tv.setText("￥:" + o_price);
        holder.marcket_sel_detail_item_del_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.marcket_sel_detail_item_from_tv.setText(activity);
        holder.marcket_sel_detail_item_example_tv.setText(once_shop);

        holder.marcket_sel_detail_liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.callBack(listses.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView marcket_sel_detail_item_standard_tv;
        public TextView marcket_sel_detail_item_title_tv;
        public TextView marcket_sel_detail_item_from_tv;
        public TextView marcket_sel_detail_item_new_price_tv;
        public TextView marcket_sel_detail_item_del_price_tv;
        public TextView marcket_sel_detail_item_example_tv;
        public TextView marcket_sel_detail_item_count_tv;
        public ImageView marcket_sel_detail_item_iv;
        public LinearLayout marcket_sel_detail_liner;
        public TextView marcket_sel_detail_item_marcket_tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            marcket_sel_detail_item_standard_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_standard_tv);
            marcket_sel_detail_item_title_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_title_tv);
            marcket_sel_detail_item_from_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_from_tv);
            marcket_sel_detail_item_new_price_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_new_price_tv);
            marcket_sel_detail_item_del_price_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_del_price_tv);
            marcket_sel_detail_item_example_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_example_tv);
            marcket_sel_detail_item_count_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_count_tv);
            marcket_sel_detail_item_iv=(ImageView)itemView.findViewById(R.id.marcket_sel_detail_item_iv);
            marcket_sel_detail_liner = (LinearLayout) itemView.findViewById(R.id.marcket_sel_detail_liner);
            marcket_sel_detail_item_marcket_tv = (TextView) itemView.findViewById(R.id.marcket_sel_detail_item_marcket_tv);
            if (which==2) {
                marcket_sel_detail_item_marcket_tv.setVisibility(View.INVISIBLE);
                marcket_sel_detail_item_count_tv.setVisibility(View.GONE);
            }else if (which == 1) {
                marcket_sel_detail_item_count_tv.setVisibility(View.GONE);
            }
        }
    }
}
