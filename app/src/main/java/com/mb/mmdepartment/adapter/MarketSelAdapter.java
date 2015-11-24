package com.mb.mmdepartment.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        if (position < list.size()) {
            final String url = BaseConsts.BASE_IMAGE_URL + list.get(position).getShop_logo();
            final String name = list.get(position).getShop_name();
            ImageLoader.getInstance().displayImage(url, holder.mImageView_icon, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    ((ImageView) view).setImageResource(R.mipmap.ic_launcher);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    ((ImageView) view).setImageBitmap(bitmap);
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
            if (isSel[position]) {
                holder.mImageView_sel.setImageResource(R.mipmap.marcket_sel);
            } else {
                holder.mImageView_sel.setImageResource(R.mipmap.market_unsel);
            }
            holder.mImageView_sel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, position);
                }
            });
            if (getItemCount() > list.size()) {
                if (position == list.size() - 1) {
                    holder.line.setVisibility(View.GONE);
                } else {
                    if (holder.line != null) {
                        if (holder.line.getVisibility() != View.VISIBLE) {
                            holder.line.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        } else {
            holder.content.setVisibility(View.INVISIBLE);
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView_sel;
        public ImageView mImageView_icon;
        public TextView line;
        public LinearLayout content;
        public ViewHolder(View v) {
            super(v);
            content = (LinearLayout) v.findViewById(R.id.content);
            line = (TextView) v.findViewById(R.id.line);
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
        return list.size() % 2 == 0 ? list.size() : list.size() + 1;
    }
}
