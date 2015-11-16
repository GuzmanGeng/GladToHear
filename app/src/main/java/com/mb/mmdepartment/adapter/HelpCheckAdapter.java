package com.mb.mmdepartment.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.mmdepartment.constans.BaseConsts;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.market_address.Description;
import com.mb.mmdepartment.listener.OnMarcketSelCallback;

import java.util.List;

/**
 * Created by Administrator on 2015/9/8.
 */
public class HelpCheckAdapter extends RecyclerView.Adapter<HelpCheckAdapter.MyViewHolder>{
    private List<Description> list;
    private OnMarcketSelCallback calback;
    public HelpCheckAdapter(List<Description> list,OnMarcketSelCallback calback) {
        this.list=list;
        this.calback=calback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.helpcheck_recyc_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String url= BaseConsts.BASE_IMAGE_URL+list.get(position).getShop_logo();
        final String name=list.get(position).getShop_name();
        ImageLoader.getInstance().displayImage(url, holder.icon, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                holder.icon.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                holder.icon.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (!"".equals(url)) {
                    holder.icon.setImageBitmap(bitmap);
                } else {
                    if ("万宁".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.wanning_chao);
                    } else if ("迪亚天天".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.diya_chao);
                    } else if ("吉买盛".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.jimaisheng_chao);
                    } else if ("华联超市".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.lianhua_chao);
                    } else if ("屈臣氏".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.quchengshi_chao);
                    } else if ("易买得".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.yimaide_chao);
                    } else if ("欧尚".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.oushang_chao);
                    } else if ("乐天玛特".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.letianmate_chao);
                    } else if ("麦德龙".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.maidelong_chao);
                    } else if ("卜蜂莲花".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.lianhua_chao);
                    } else if ("沃尔玛".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.woerma_chao);
                    } else if ("农工商".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.nonggongshang_chao);
                    } else if ("大润发".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.runfa_chao);
                    } else if ("乐购".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.happy_buy_chao);
                    } else if ("家乐福".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.jialefu_chao);
                    }
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        holder.tv_name.setText(name);
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calback.marcketSelCallBack(list.get(position).getShop_id(),name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView tv_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            icon= (ImageView) itemView.findViewById(R.id.help_check_market_icon);
            tv_name=(TextView)itemView.findViewById(R.id.help_check_market_name);
        }
    }
    public interface OnItemClickListener {
        void onClick(View view);
    }
}
