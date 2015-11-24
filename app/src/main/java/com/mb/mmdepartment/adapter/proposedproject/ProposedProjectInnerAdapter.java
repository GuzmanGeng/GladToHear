package com.mb.mmdepartment.adapter.proposedproject;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.WaresDetailPageActivity;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.tools.shop_car.ShopCarAtoR;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import java.util.ArrayList;
import java.util.Date;
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
    private SizeCallBack callBack;
    private Activity activity;
    public ProposedProjectInnerAdapter(List<Lists> lists,int which,ImageView title_sel,LinearLayout check_all,SizeCallBack callBack,Activity activity){
        this.lists=lists;
        this.which=which;
        this.title_sel=title_sel;
        this.check_all=check_all;
        this.callBack=callBack;
        this.activity=activity;
        sel_ids = new ArrayList<>();
        ids = new String[lists.size()];
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.proposed_project_recycle_item_tem, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        if (title_sel != null) {
            title_sel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShopCarAtoR shop_car = new ShopCarAtoR(activity);
                    count=0;
                    if (sel) {
                        for (int i=0;i<lists.size();i++) {
                            String id = ids[i];
                            shop_car.remove_cars_index(id);
                            ((BaseActivity)activity).LuPingWithSelectId(String.valueOf(id),"car","unSelected","help_Accu_Commodity_List",lists.get(i).getShop_id(),new Date());
                        }
                        sel_ids.clear();
                        notifyDataSetChanged();
                        title_sel.setImageResource(R.mipmap.market_unsel);
                        sel=false;
                    } else {
                        for (int i=0;i<lists.size();i++) {
                            String id=ids[i];
                            if (!TApplication.ids.contains(id)) {
                                ((BaseActivity)activity).LuPingWithSelectId(String.valueOf(id),"car","selected","help_Accu_Commodity_List",lists.get(i).getShop_id(),new Date());
                                shop_car.add_cars_index(id,lists.get(i).getSelect_shop_name(),lists.get(i));
                                notifyItemChanged(i);
                            }
                        }
                        title_sel.setImageResource(R.mipmap.marcket_sel);
                        sel=true;
                    }
                    callBack.getSize();
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String id = lists.get(position).getId();
        ids[position] = id;
        String name = lists.get(position).getName();
        final String activity_info = lists.get(position).getActivity();
        String standard = lists.get(position).getStandard();
        String f_price = lists.get(position).getF_price();
        String o_price = lists.get(position).getO_price();
        String once_shop = lists.get(position).getOne_shop();
        String item = lists.get(position).getItem();
        //超市名称  此处是留给购物车里显示用的
        String title = lists.get(position).getSelect_shop_name();
        if (TextUtils.isEmpty(title)) {
            title = lists.get(position).getShop_name();
        }
        holder.marcket_sel_detail_item_count_tv.setText(item+"件");
        holder.marcket_sel_detail_item_title_tv.setText(name);
        holder.marcket_sel_detail_item_from_tv.setText(activity_info);
        if ("".equals(standard)) {
            holder.marcket_sel_detail_item_standard_tv.setText("规格待定");
        } else {
            holder.marcket_sel_detail_item_standard_tv.setText(standard);
        }
        holder.marcket_sel_detail_item_new_price_tv.setText(f_price);
        holder.marcket_sel_detail_item_del_price_tv.setText(o_price);
        holder.marcket_sel_detail_item_del_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.marcket_sel_detail_item_example_tv.setText(once_shop);
        holder.marcket_sel_detail_item_marcket_tv.setText(title);
        if (which == 0) {
            holder.marcket_sel_detail_item_marcket_tv.setVisibility(View.INVISIBLE);
            holder.check_single.setVisibility(View.GONE);

            if (position == 0) {
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setText(title);
            } else {
                String shop_name=lists.get(position).getSelect_shop_name();
                if (TextUtils.isEmpty(shop_name)) {
                    shop_name = lists.get(position).getShop_name().trim();
                }
                String pre_shop_name=lists.get(position-1).getSelect_shop_name();
                if (TextUtils.isEmpty(pre_shop_name)) {
                    pre_shop_name=lists.get(position-1).getShop_name();
                }
                if (pre_shop_name.equals(shop_name)) {
                    holder.title.setVisibility(View.GONE);
                } else {
                    holder.title.setVisibility(View.VISIBLE);
                    holder.title.setText(shop_name);
                }
            }
        } else if (which==1){
            holder.marcket_sel_detail_item_marcket_tv.setVisibility(View.VISIBLE);
        }else if (which == 2) {
            holder.marcket_sel_detail_item_marcket_tv.setVisibility(View.INVISIBLE);
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
        if (title_sel != null) {
            if (TApplication.ids.contains(id)) {
                holder.check_single.setImageResource(R.mipmap.marcket_sel);
                ++count;
                if (count <=lists.size()) {
                    sel_ids.add(id);
                }
                if (sel_ids.size() == lists.size()) {
                    title_sel.setImageResource(R.mipmap.marcket_sel);
                    sel = true;
                }
            } else {
                holder.check_single.setImageResource(R.mipmap.market_unsel);
            }
            holder.check_single.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShopCarAtoR shop = new ShopCarAtoR(activity);
                    if (TApplication.ids.contains(id)) {
                        shop.remove_cars_index(id);
                        ((BaseActivity)activity).LuPingWithSelectId(String.valueOf(id), "car", "unSelected", "help_Accu_Commodity_List", lists.get(position).getShop_id(), new Date());
                        if (sel_ids.contains(id)) {
                            sel_ids.remove(id);
                        }
                        holder.check_single.setImageResource(R.mipmap.market_unsel);
                        if (sel) {
                            title_sel.setImageResource(R.mipmap.market_unsel);
                            sel = false;
                        }
                    } else {
                        shop.add_cars_index(id,lists.get(position).getSelect_shop_name(),lists.get(position));
                        ((BaseActivity)activity).LuPingWithSelectId(String.valueOf(id), "car", "selected", "help_Accu_Commodity_List", lists.get(position).getShop_id(), new Date());
                        sel_ids.add(id);
                        holder.check_single.setImageResource(R.mipmap.marcket_sel);
                        if (sel_ids.size() == lists.size()) {
                            title_sel.setImageResource(R.mipmap.marcket_sel);
                            sel = true;
                        }
                    }
                    callBack.getSize();
                }
            });
        }
        holder.marcket_sel_detail_liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title_sel != null) {
                    ((BaseActivity) activity).LuPingWithSelectId(lists.get(position).getCategory_id(), "article", "helpYouCount", "help_Accu_Commodity_List", lists.get(position).getShop_id(), new Date());
                } else {
                    ((BaseActivity) activity).LuPingWithSelectId(lists.get(position).getCategory_id(), "article", "car", "shopping_Car", lists.get(position).getShop_id(), new Date());
                }
                Intent intent = new Intent(view.getContext(), WaresDetailPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("lists", lists.get(position));
                intent.putExtra("bundle", bundle);
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
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
            title = (TextView) itemView.findViewById(R.id.title);
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
    public interface SizeCallBack{
        void getSize();
    }
}
