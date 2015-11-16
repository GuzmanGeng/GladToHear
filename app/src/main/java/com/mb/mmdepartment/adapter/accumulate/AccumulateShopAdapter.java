package com.mb.mmdepartment.adapter.accumulate;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.assist.imageaware.WeakImageViewAware;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.accumulate.Product;
import com.mb.mmdepartment.listener.AccumulateShopItemClickListener;
import com.mb.mmdepartment.listener.RequestListener;

import java.util.List;

/**
 * Accumulate(积分商城)adapter
 */
public class AccumulateShopAdapter extends RecyclerView.Adapter<AccumulateShopAdapter.MyViewHolder>{
    private List<Product> list;
    private DisplayImageOptions options;
    private AccumulateShopItemClickListener listener;
    public AccumulateShopAdapter(List<Product> list,AccumulateShopItemClickListener listener) {
        this.list=list;
        this.listener=listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.accumulate_shop_item,parent,false);
        MyViewHolder hollder=new MyViewHolder(view);
        return hollder;
    }

    @Override
    public void onBindViewHolder(final AccumulateShopAdapter.MyViewHolder holder, final int position) {
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.loading)
                .showImageOnFail(R.mipmap.loading)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.NONE)
                .bitmapConfig(Bitmap.Config.RGB_565)//设置为RGB565比起默认的ARGB_8888要节省大量的内存
                .delayBeforeLoading(100)//载入图片前稍做延时可以提高整体滑动的流畅度
                .build();
        String url=list.get(position).getImage();
        ImageLoader.getInstance().displayImage(url, holder.imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                ((ImageView)view).setImageResource(R.mipmap.loading);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                ((ImageView)view).setImageResource(R.mipmap.loading);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                ((ImageView)view).setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        holder.tv_titile.setText(list.get(position).getTitle());
        holder.tv_count.setText(list.get(position).getExchange_integral());
        holder.accumulate_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(null,list.get(position).getContent_id());
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_titile;
        public TextView tv_count;
        public CardView accumulate_cardview;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.accumulate_item_shopping_iv);
            tv_titile=(TextView)itemView.findViewById(R.id.accumulate_item_shopping_introduce_tv);
            tv_count=(TextView)itemView.findViewById(R.id.accumulate_item_shopping_count_tv);
            accumulate_cardview = (CardView) itemView.findViewById(R.id.accumulate_cardview);
        }
    }
}
