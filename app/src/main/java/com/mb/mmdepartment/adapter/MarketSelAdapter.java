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
import com.mb.mmdepartment.listener.OnRecycItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2015/8/19.
 */
public class MarketSelAdapter extends RecyclerView.Adapter<MarketSelAdapter.ViewHolder>{
    private List<Description> list;
    private OnRecycItemClickListener onItemClickListener;
    private boolean[] isSel;
    public MarketSelAdapter(List<Description> list,OnRecycItemClickListener onItemClickListener,boolean[] isSel) {
        this.list = list;
        this.onItemClickListener=onItemClickListener;
        this.isSel=isSel;
    }
    @Override
    public MarketSelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v;
        if (viewType==1){
            v=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.market_item_left, parent, false);
        }else {
            v=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.market_item, parent, false);
        }
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String url= BaseConsts.BASE_IMAGE_URL+list.get(position).getShop_logo();
        final String name=list.get(position).getShop_name();
        ImageLoader.getInstance().displayImage(url, holder.mImageView_icon, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                ((ImageView) view).setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                ((ImageView) view).setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (!"".equals(url)) {
//                    ImageLoader.getInstance().displayImage(url, (ImageView) view);
                            ((ImageView) view).setImageBitmap(bitmap);
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
                    }else if ("全家".equals(name)) {
                        ((ImageView) view).setImageResource(R.mipmap.yimaide_chao);
                    }
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        holder.mTextView.setText(name);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });
        holder.mImageView_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });
        if (isSel[position]){
            holder.mImageView_sel.setImageResource(R.mipmap.marcket_sel);
        }else {
            holder.mImageView_sel.setImageResource(R.mipmap.market_unsel);
        }
        holder.mImageView_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,position);
            }
        });
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView_sel;
        public ImageView mImageView_icon;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_market_left);
            mImageView_sel = (ImageView) v.findViewById(R.id.iv_market_sel);
            mImageView_icon = (ImageView) v.findViewById(R.id.iv_market_icon);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position%2==0?1:2;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
