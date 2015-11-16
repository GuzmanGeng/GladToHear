package com.mb.mmdepartment.adapter.buyplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.tools.log.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by krisi on 2015/10/23.
 */
public class ShoppingCartAdapter extends BaseExpandableListAdapter {
    int a;
    private static class GroupHolder{
        TextView groupname;
    }
    private static class ChildHolder {
        LinearLayout shoppin_ll;
        ImageView pic;
        TextView weight;
        TextView name;
        TextView count;
        TextView activity;
        TextView f_price;
        TextView o_price;
        TextView once_price;
    }

    private Map<String, Lists> shopping_list;
    private List<DataList> shopping_lists;
    private LayoutInflater inflater;

    public ShoppingCartAdapter(Context context, List<DataList> shopping_lists) {
        this.shopping_lists = shopping_lists;
        inflater = LayoutInflater.from(context);
        shopping_list = TApplication.shop_lists;
    }

    @Override
    public int getGroupCount() {
        return shopping_lists.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return shopping_lists.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return shopping_lists.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupHolder holder;
        final DataList item = shopping_lists.get(groupPosition);
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = inflater.inflate(R.layout.item_shopping_group,parent, false);
            holder.groupname = (TextView) convertView.findViewById(R.id.item_shopping_cart_group_name);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.groupname.setText(item.getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder holder;
        final Lists item = shopping_lists.get(groupPosition).getList().get(childPosition);
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = inflater.inflate(R.layout.item_shopping_cart_goods_detail, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_name);
            holder.count = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_count);
            holder.o_price = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_o_price);
            holder.f_price = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_f_price);
            holder.once_price = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_o_price);
            holder.activity = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_activity);
            holder.weight = (TextView) convertView.findViewById(R.id.wave);
            holder.pic = (ImageView) convertView.findViewById(R.id.item_shopping_cart_child_pic);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        Log.i("tag", item.getShop_id());
        holder.name.setText(item.getName());
        holder.o_price.setText("¥" + item.getO_price());
        holder.once_price.setText("¥" + item.getOne_shop());
        holder.f_price.setText("¥" + item.getF_price());
        holder.count.setText(item.getItem() + "件");
        holder.activity.setText(item.getActivity());
        ImageLoader.getInstance().displayImage(BaseConsts.BASE_IMAGE_URL + item.getPic(), holder.pic);
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        final Holder holder;
//        final Lists item = shopping_lists.get(position);
//        if (convertView == null) {
//            holder = new Holder();
//            convertView = inflater.inflate(R.layout.item_shopping_cart_goods_detail, parent, false);
//            holder.groupname = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_group_name);
//            holder.shoppin_ll = (LinearLayout) convertView.findViewById(R.id.shopping_ll);
//            holder.name = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_name);
//            holder.count = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_count);
//            holder.o_price = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_o_price);
//            holder.f_price = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_f_price);
//            holder.once_price = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_o_price);
//            holder.activity = (TextView) convertView.findViewById(R.id.item_shopping_cart_child_activity);
//            holder.weight = (TextView) convertView.findViewById(R.id.wave);
//            holder.pic = (ImageView) convertView.findViewById(R.id.item_shopping_cart_child_pic);
//            convertView.setTag(holder);
//        } else {
//            holder = (Holder) convertView.getTag();
//        }
//        Log.i("tag",item.getShop_id());
//        holder.groupname.setText(item.getShop_name());
//        holder.name.setText(item.getName());
//        holder.o_price.setText("¥" + item.getO_price());
//        holder.once_price.setText("¥" + item.getOne_shop());
//        holder.f_price.setText("¥" + item.getF_price());
//        holder.count.setText(item.getItem() + "件");
//        holder.activity.setText(item.getActivity());
//        ImageLoader.getInstance().displayImage(BaseConsts.BASE_IMAGE_URL + item.getPic(), holder.pic);
//        if (position != 0) {
//                if (item.getShop_id().equals(shopping_lists.get(position - 1).getShop_id())) {
//                    holder.shoppin_ll.setVisibility(View.GONE);
//            }
//        }
//        if (position == 0){
//            holder.shoppin_ll.setVisibility(View.VISIBLE);
//        }
//        return convertView;
//    }

}
