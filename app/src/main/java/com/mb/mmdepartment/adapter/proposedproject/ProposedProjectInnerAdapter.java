package com.mb.mmdepartment.adapter.proposedproject;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.constans.BaseConsts;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by joyone2one on 2015/11/17.
 */
public class ProposedProjectInnerAdapter extends RecyclerView.Adapter<ProposedProjectInnerAdapter.MyViewHolder> {
    private List<Lists> lists;
    private int which;
    private int count;
    private ImageView title_sel;
    private LinearLayout check_all;
    private String[] ids;
    private List<String> sel_ids;
    private boolean sel;
    private AllSel listener;
    public ProposedProjectInnerAdapter(List<Lists> lists,int which,ImageView title_sel,LinearLayout check_all,AllSel listener){
        this.lists=lists;
        this.which=which;
        this.title_sel=title_sel;
        this.check_all=check_all;
        this.listener=listener;
        sel_ids = new ArrayList<>();
        ids = new String[lists.size()];
        Log.e("size====", lists.size() + "");
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.proposed_project_recycle_item_tem, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        title_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=0;
                if (sel) {
                    for (String id:ids) {
                        TApplication.ids.remove(id);
                    }
                    sel_ids.clear();

                    Log.e("a===sel_ids", "size=" + sel_ids.size() + "," + TApplication.ids.size());
                    notifyDataSetChanged();
                    title_sel.setImageResource(R.mipmap.market_unsel);
                    sel=false;
                } else {
                    for (int i=0;i<lists.size();i++) {
                        String id=ids[i];
                        if (!TApplication.ids.contains(id)) {
                            TApplication.ids.add(id);
                            notifyItemChanged(i);
                        }
                    }
                    Log.e("b===sel_ids", "size=" + sel_ids.size()+","+TApplication.ids.size());
                    title_sel.setImageResource(R.mipmap.marcket_sel);
                    sel=true;
                }

            }
        });
//    check_all.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            for (String id : ids) {
//                TApplication.ids.add(id);
//            }
//        }
//    });
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.e("cishu", position+"");
        final String id = lists.get(position).getId();
        ids[position] = id;
        String name = lists.get(position).getName();
        String activity = lists.get(position).getActivity();
        String standard = lists.get(position).getStandard();
        String f_price = lists.get(position).getF_price();
        String o_price = lists.get(position).getO_price();
        String once_shop = lists.get(position).getOne_shop();
        String item = lists.get(position).getItem();
        holder.marcket_sel_detail_item_count_tv.setText(item);
        holder.marcket_sel_detail_item_title_tv.setText(name);
        holder.marcket_sel_detail_item_from_tv.setText(activity);
        if ("".equals(standard)) {
            holder.marcket_sel_detail_item_standard_tv.setText("规格待定");
        } else {
            holder.marcket_sel_detail_item_standard_tv.setText(standard);
        }
        holder.marcket_sel_detail_item_new_price_tv.setText(f_price);
        holder.marcket_sel_detail_item_del_price_tv.setText(o_price);
        holder.marcket_sel_detail_item_del_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.marcket_sel_detail_item_example_tv.setText(once_shop);
        if (which == 0) {
            holder.marcket_sel_detail_item_marcket_tv.setVisibility(View.GONE);
        } else {
            holder.marcket_sel_detail_item_marcket_tv.setVisibility(View.VISIBLE);
        }

        final String url = BaseConsts.BASE_IMAGE_URL + lists.get(position).getPic();
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
        /**
         * 根据id是否被添加到Tapplication里面判断是否选中
         */
        if (TApplication.ids.contains(id)) {
            holder.check_single.setImageResource(R.mipmap.marcket_sel);
            ++count;
            if (count <=lists.size()) {
                sel_ids.add(id);
            }
            if (sel_ids.size() == lists.size()) {
                listener.selAll(true);
                title_sel.setImageResource(R.mipmap.marcket_sel);
                sel = true;
            }
        } else {
            listener.selAll(false);
            holder.check_single.setImageResource(R.mipmap.market_unsel);
        }
        holder.check_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TApplication.ids.contains(id)) {
                    TApplication.ids.remove(id);
                    if (sel_ids.contains(id)) {
                        sel_ids.remove(id);
                    }
                    Log.e("c===sel_ids", "size=" + sel_ids.size() + "," + TApplication.ids.size());
                    holder.check_single.setImageResource(R.mipmap.market_unsel);
                    if (sel) {
                        title_sel.setImageResource(R.mipmap.market_unsel);
                        sel = false;
                    }
                } else {
                    TApplication.ids.add(id);
                    sel_ids.add(id);
                    Log.e("d===sel_ids", "size=" + sel_ids.size() + "," + TApplication.ids.size());
                    holder.check_single.setImageResource(R.mipmap.marcket_sel);
                    if (sel_ids.size() == lists.size()) {
                        title_sel.setImageResource(R.mipmap.marcket_sel);
                        listener.selAll(true);
                        sel = true;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
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
        private TextView marcket_sel_detail_item_marcket_tv;
        public LinearLayout marcket_sel_detail_liner;
        public ImageView check_single;
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
            marcket_sel_detail_item_marcket_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_marcket_tv);
            check_single = (ImageView) itemView.findViewById(R.id.check_single);
        }
    }
    public interface AllSel{
        void selAll(boolean sel_all);
    }
}
