package com.mb.mmdepartment.adapter.buyplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.tools.log.Log;

import java.util.List;

/**
 * Created by krisi on 2015/10/27.
 */
public class BuyListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<DataList> list;
    private LayoutInflater inflater;
    private double alllpay = 0d;

    class GroupHolder{
        TextView market_name;
    }
    class ChildHolder{
        TextView item_buy_list_name_tv;
        TextView item_buy_list_goods_price_tv;
        TextView item_buy_list_goods_num_tv;
        TextView item_buy_list_goods_coast_tv;
        TextView item_buy_list_allpay_tv;
        LinearLayout item_buy_list_allpay_ll;
    }

    public BuyListAdapter(Context context,List<DataList> list){
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if(convertView == null){
            holder = new GroupHolder();
            convertView = inflater.inflate(R.layout.item_market,parent,false);
            holder.market_name = (TextView)convertView.findViewById(R.id.item_market_name_tv);
            convertView.setTag(holder);
        }else{
            holder = (GroupHolder)convertView.getTag();
        }
        holder.market_name.setText(list.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        Lists item = list.get(groupPosition).getList().get(childPosition);
        if(convertView == null){
            holder = new ChildHolder();
            convertView = inflater.inflate(R.layout.item_buy_list,null,false);
            holder.item_buy_list_allpay_ll = (LinearLayout)convertView.findViewById(R.id.item_buy_list_allpay_ll);
            holder.item_buy_list_allpay_tv = (TextView)convertView.findViewById(R.id.item_buy_list_allpay_tv);
            holder.item_buy_list_name_tv = (TextView)convertView.findViewById(R.id.item_buy_list_name_tv);
            holder.item_buy_list_goods_num_tv = (TextView)convertView.findViewById(R.id.item_buy_list_goods_num_tv);
            holder.item_buy_list_goods_coast_tv = (TextView)convertView.findViewById(R.id.item_buy_list_goods_coast_tv);
            holder.item_buy_list_goods_price_tv = (TextView)convertView.findViewById(R.id.item_buy_list_goods_price_tv);
            convertView.setTag(holder);
        }
        holder = (ChildHolder
                )convertView.getTag();
        holder.item_buy_list_name_tv.setText(item.getName());
        holder.item_buy_list_goods_num_tv.setText(item.getItem()+"件");
        holder.item_buy_list_goods_price_tv.setText("¥"+item.getO_price()+"(折前)\n"+"¥" +item.getF_price()+"(折后)");
//        double cost = Integer.parseInt(item.getPid())*Double.parseDouble(item.getF_price());
        holder.item_buy_list_goods_coast_tv.setText("¥"+item.getF_price());
        if(childPosition==0){
            alllpay = 0;
        }
        alllpay = alllpay + Double.parseDouble(item.getF_price());
        if(isLastChild) {
            holder.item_buy_list_allpay_ll.setVisibility(View.VISIBLE);
            String pay = alllpay + "";
            int num = pay.indexOf(".");
            holder.item_buy_list_allpay_tv.setText("总计：¥" + pay.substring(0, num+2));
        }else {
            holder.item_buy_list_allpay_ll.setVisibility(View.GONE);
        }

        return convertView;
    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Holder holder;
//        alllpay = 0;
//        Lists item = list.get(position);
//        if(convertView == null){
//            holder = new Holder();
//            convertView = inflater.inflate(R.layout.item_buy_list,null,false);
//            holder.item_buy_list_allpay_ll = (LinearLayout)convertView.findViewById(R.id.item_buy_list_allpay_ll);
//            holder.item_buy_list_allpay_tv = (TextView)convertView.findViewById(R.id.item_buy_list_allpay_tv);
//            holder.market_name = (TextView)convertView.findViewById(R.id.item_buy_list_market_name_tv);
//            holder.item_buy_list_name_tv = (TextView)convertView.findViewById(R.id.item_buy_list_name_tv);
//            holder.item_buy_list_goods_num_tv = (TextView)convertView.findViewById(R.id.item_buy_list_goods_num_tv);
//            holder.item_buy_list_goods_coast_tv = (TextView)convertView.findViewById(R.id.item_buy_list_goods_coast_tv);
//            holder.item_buy_list_goods_price_tv = (TextView)convertView.findViewById(R.id.item_buy_list_goods_price_tv);
//            convertView.setTag(holder);
//        }
//        holder = (Holder)convertView.getTag();
//        holder.market_name.setText(item.getShop_id());
//        holder.item_buy_list_name_tv.setText(item.getName());
//        holder.item_buy_list_goods_num_tv.setText(item.getPid()+"件");
//        holder.item_buy_list_goods_coast_tv.setText("¥"+item.getF_price()+"(折前)"+"¥"+item.getO_price()+"(折后)");
//        double cost = Integer.parseInt(item.getPid())*Double.parseDouble(item.getO_price());
//        holder.item_buy_list_goods_price_tv.setText("¥"+cost);
//        alllpay = alllpay + cost;
//        String pay = alllpay + "";
//        int num = pay.indexOf(".");
//        if(position!=0) {
//            if (list.get(position).getShop_id().equals(list.get(position - 1).getShop_id())) {
//                holder.market_name.setVisibility(View.GONE);
//            }
//        }else{
//            holder.market_name.setVisibility(View.VISIBLE);
//        }
//        if(position+1<list.size()){
//            if(list.get(position).getShop_id().equals(list.get(position +1).getShop_id())){
//                holder.item_buy_list_allpay_ll.setVisibility(View.GONE);
//            }
//        }
//        if(position == 0) {
//            holder.item_buy_list_allpay_ll.setVisibility(View.GONE);
//        }
//        holder.item_buy_list_allpay_tv.setText("¥"+pay.substring(0, num+2));
//        return convertView;
//    }
}
