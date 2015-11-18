package com.mb.mmdepartment.adapter.userspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.ExpandableAdapter;
import com.mb.mmdepartment.adapter.catlogs.ChildItem;
import com.mb.mmdepartment.adapter.catlogs.GroupItem;
import com.mb.mmdepartment.bean.userspace.listrecord.getlistrecorddetail.Shop;
import com.mb.mmdepartment.bean.userspace.listrecord.getlistrecorddetail.shop.Description;
import com.mb.mmdepartment.tools.log.Log;

import java.util.List;
import java.util.Objects;

/**
 * Created by krisi on 2015/10/21.
 */
public class ListRecordDetailAdapter extends BaseExpandableListAdapter {

    private static class ChildHolder {
        TextView goods_name;
        TextView goods_price;
        TextView goods_num;
        TextView goods_coast;
        TextView allpay;
        LinearLayout item_goods_detail_goods_allpay_ll;

    }
    private static class GroupHolder {
        TextView market_name;
    }
    private List<Shop> groups;
    private LayoutInflater inflater;
    private ExpandableAdapter.CallBack callBack;
    private double alllpay = 0d;
    public ListRecordDetailAdapter(Context context, List<Shop> groups, ExpandableAdapter.CallBack callBack) {
        this.groups = groups;
        inflater = LayoutInflater.from(context);
        this.callBack=callBack;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getData().get(childPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        alllpay = 0;
        Shop item = groups.get(groupPosition);
        if (convertView == null) {
            holder=new GroupHolder();
            convertView = inflater.inflate(R.layout.item_market, parent, false);
            holder.market_name=(TextView)convertView.findViewById(R.id.item_market_name_tv);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.market_name.setText(item.getShop_name());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        final ChildHolder holder;
        final Description item=groups.get(groupPosition).getData().get(childPosition);
        if (view == null) {
            holder = new ChildHolder();
            view = inflater.inflate(R.layout.item_goods_detail, parent, false);
            holder.goods_name=(TextView)view.findViewById(R.id.item_goods_detail_goods_name_tv);
            holder.goods_price=(TextView)view.findViewById(R.id.item_goods_detail_goods_price_tv);
            holder.goods_num=(TextView)view.findViewById(R.id.item_goods_detail_goods_num_tv);
            holder.goods_coast=(TextView)view.findViewById(R.id.item_goods_detail_goods_coast_tv);
            holder.allpay=(TextView)view.findViewById(R.id.item_goods_detail_goods_allpay_tv);
            holder.item_goods_detail_goods_allpay_ll=(LinearLayout)view.findViewById(R.id.item_goods_detail_goods_allpay_ll);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        holder.goods_name.setText(item.getName());
        holder.goods_price.setText("¥"+item.getF_price()+"(折前)\n"+"¥"+item.getO_price()+"(折后)");
        holder.goods_num.setText(item.getQuantity());
        double cost = Integer.parseInt(item.getQuantity())*Double.parseDouble(item.getO_price());
        Log.i("cost",cost+"");
        holder.goods_coast.setText(cost+"");
        alllpay = alllpay + cost;
        if(isLastChild) {
            Log.i("tag", "isLastChild");
            holder.item_goods_detail_goods_allpay_ll.setVisibility(View.VISIBLE);
            String pay = alllpay + "";
            int num = pay.indexOf(".");
            holder.allpay.setText("总计：¥" + pay.substring(0, num+2));
        }
        if(childPosition==0){
            holder.item_goods_detail_goods_allpay_ll.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getData().size();
    }

    @Override
    public Shop getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return (groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
