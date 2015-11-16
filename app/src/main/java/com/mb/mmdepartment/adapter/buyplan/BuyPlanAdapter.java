package com.mb.mmdepartment.adapter.buyplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mb.mmdepartment.activities.WaresDetailPageActivity;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.activities.CalculateShowWaresInfoActivity;
import com.mb.mmdepartment.adapter.ExpandableAdapter;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.tools.log.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by krisi on 2015/10/23.
 */
public class BuyPlanAdapter extends BaseExpandableListAdapter {
    private static class ChildHolder {
        RelativeLayout choice_ll;
        ImageView choose;
        ImageView choice;
        ImageView pic;
        TextView weight;
        TextView name;
        TextView count;
        TextView activity;
        TextView f_price;
        TextView o_price;
        TextView once_price;
        TextView shop_name;
        int choosed = 0;
    }
    private static class GroupHolder {
        int a ;
        RelativeLayout choice_ll;
        TextView groupname;
        ImageView choose;
        ImageView choice;
    }

    private int sort_group;
    private List<Lists> lists;
    private List<DataList> groups;
    private LayoutInflater inflater;
    private ExpandableAdapter.CallBack callBack;
    public BuyPlanAdapter(Context context, List<DataList> groups, ExpandableAdapter.CallBack callBack) {
        this.groups = groups;
        inflater = LayoutInflater.from(context);
        this.callBack=callBack;
        lists = new ArrayList<>();
    }

    public void setSort_group(int sort_group) {
        this.sort_group = sort_group;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public Lists getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       final GroupHolder holder;
        DataList item = groups.get(groupPosition);
        if (convertView == null) {
            holder=new GroupHolder();
            convertView = inflater.inflate(R.layout.item_plan_group, parent, false);
            holder.groupname=(TextView)convertView.findViewById(R.id.item_plan_group_name);
            holder.choice = (ImageView)convertView.findViewById(R.id.item_plan_group_choice);
            holder.choose = (ImageView)convertView.findViewById(R.id.item_plan_group_choose);
            holder.choice_ll=(RelativeLayout)convertView.findViewById(R.id.item_plan_group_choice_ll);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.groupname.setText(item.getName());
        for(int i = 0;i<groups.size();i++){
            for(int j = 0;j<groups.get(i).getList().size();j++) {
                holder.a=0;
                if (TApplication.shop_lists.containsKey(groups.get(i).getList().get(j).getId())) {
                    holder.a++;
                }
            }
        }
        final View v = convertView;
       if(groups.get(groupPosition).getList().size() == holder.a){
           holder.choose.setVisibility(View.VISIBLE);
           holder.choice.setVisibility(View.GONE);
           notifyDataSetChanged();
       }else{
           holder.choose.setVisibility(View.GONE);
           holder.choice.setVisibility(View.VISIBLE);
           notifyDataSetChanged();
       }
        holder.choice_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groups.size() == holder.a){
                    Log.i("contain","1");
                    holder.choose.setVisibility(View.GONE);
                    holder.choice.setVisibility(View.VISIBLE);
                    for(int i = 0;i<groups.size();i++){
                        for(int j = 0;j<groups.get(i).getList().size();j++) {
                            TApplication.shop_lists.remove(groups.get(groupPosition).getList().get(j).
                                    getId());
                        }
                        CalculateShowWaresInfoActivity.setbadge();
                    }
                    notifyDataSetChanged();
                    holder.a = 0;
                }else{
                    Log.i("contain", "2");
                    holder.choose.setVisibility(View.VISIBLE);
                    holder.choice.setVisibility(View.GONE);
                    for(int i = 0;i<groups.size();i++){
                        for(int j = 0;j<groups.get(i).getList().size();j++) {
                            TApplication.shop_lists.put(groups.get(groupPosition).getList().get(j).
                                    getId(), groups.get(groupPosition).getList().get(j));
                            holder.a = groups.get(i).getList().size();
                        }
                        CalculateShowWaresInfoActivity.setbadge();
                    }
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,View view, ViewGroup parent) {
        final ChildHolder holder;
        final Lists item=groups.get(groupPosition).getList().get(childPosition);
        if (view == null) {
            holder = new ChildHolder();
            view = inflater.inflate(R.layout.item_plan_goods_detail, parent, false);
            holder.name=(TextView)view.findViewById(R.id.item_plan_child_name);
            holder.count=(TextView)view.findViewById(R.id.item_plan_child_count);
            holder.o_price=(TextView)view.findViewById(R.id.item_plan_child_o_price);
            holder.f_price=(TextView)view.findViewById(R.id.item_plan_child_f_price);
            holder.once_price=(TextView)view.findViewById(R.id.item_plan_child_o_price);
            holder.activity=(TextView)view.findViewById(R.id.item_plan_child_activity);
            holder.weight=(TextView)view.findViewById(R.id.wave);
            holder.choice=(ImageView)view.findViewById(R.id.item_plan_child_choice);
            holder.choose=(ImageView)view.findViewById(R.id.item_plan_child_choose);
            holder.pic=(ImageView)view.findViewById(R.id.item_plan_child_pic);
            holder.choice_ll=(RelativeLayout)view.findViewById(R.id.item_plan_child_choice_ll);
            holder.shop_name=(TextView)view.findViewById(R.id.buy_plan_shop_name_tv);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        holder.name.setText(item.getName());
        holder.o_price.setText("¥"+item.getO_price());
        holder.once_price.setText("¥"+ item.getOne_shop());
        holder.f_price.setText("¥" + item.getF_price());
        holder.count.setText(item.getItem() + "件");
        holder.activity.setText(item.getActivity());
        String shop_name = TApplication.market.get(item.getShop_id());
        holder.shop_name.setText(shop_name);
        ImageLoader.getInstance().displayImage(BaseConsts.BASE_IMAGE_URL + item.getPic(), holder.pic);
//        for(int i = 0;i<TApplication.shop_list_to_pick.size();i++) {
//            for(int j = 0;j<TApplication.shop_list_to_pick.get(i).getList().size();j++){
            if (TApplication.shop_lists.containsKey(item.getId())) {
                holder.choose.setVisibility(View.VISIBLE);
                holder.choice.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
            else {
                holder.choose.setVisibility(View.GONE);
                holder.choice.setVisibility(View.VISIBLE);
            notifyDataSetChanged();
//            }
//            }
        }
        final View v = view;
        holder.choice_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                LuPinModel luPinModel = new LuPinModel();
                luPinModel.setName(item.getId());
                luPinModel.setType("car");
                luPinModel.setOperationtime(sdf.format(new Date()));
                if(!TApplication.shop_lists.containsKey(item.getId())){
                    holder.choose.setVisibility(View.VISIBLE);
                    holder.choice.setVisibility(View.GONE);
//                    holder.choosed = 1;
                    luPinModel.setState("selected");
                    TApplication.shop_lists.put(item.getId(), item);
                    WaresDetailPageActivity.add_goods(item);
                    callBack.getView(v, groupPosition, childPosition);
                }else{
                    holder.choose.setVisibility(View.GONE);
                    holder.choice.setVisibility(View.VISIBLE);
//                    holder.choosed = 0;
                    luPinModel.setState("unselected");
                    TApplication.shop_lists.remove(item.getId());
                    WaresDetailPageActivity.remove_goods(item);
                    callBack.getView(v, groupPosition, childPosition);
                }
                TApplication.luPinModels.add(luPinModel);
//                notifyDataSetChanged();
            }
        });
        if(sort_group == 1){
            holder.shop_name.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getList().size();
    }



    @Override
    public DataList getGroup(int groupPosition) {
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
