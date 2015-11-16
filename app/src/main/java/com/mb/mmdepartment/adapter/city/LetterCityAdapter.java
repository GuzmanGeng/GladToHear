package com.mb.mmdepartment.adapter.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.ExpandableAdapter;
import com.mb.mmdepartment.bean.getcities.Description;
import com.mb.mmdepartment.bean.getcities.getlettercities.Data;
import com.mb.mmdepartment.tools.log.Log;

import java.util.List;
import java.util.Objects;

/**
 * Created by krisi on 2015/10/21.
 */
public class LetterCityAdapter extends BaseExpandableListAdapter {
    private static class ChildHolder {
        TextView city_name;

    }
    private static class GroupHolder {
        TextView city_letter;
    }
    private LayoutInflater inflater;
    private List<List<Description>> groups;
    private ExpandableAdapter.CallBack callBack;
    public LetterCityAdapter(Context context, List<List<Description>> groups) {
        this.groups = groups;
        inflater = LayoutInflater.from(context);
        this.callBack=callBack;
    }

    @Override
    public int getGroupCount() {
        Log.i("lettercity","getGroupCount"+groups.size());
        return groups.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.i("lettercity","getChild");
        return groups.get(groupPosition).get(childPosition);

    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        Log.i("lettercity","lettercity");
        List<Description> item = groups.get(groupPosition);
        if (convertView == null) {
            holder=new GroupHolder();
            convertView = inflater.inflate(R.layout.item_city, parent, false);
            holder.city_letter=(TextView)convertView.findViewById(R.id.city_letter);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        if(groupPosition == 0){
            holder.city_letter.setText("热门城市");
        }else {
            holder.city_letter.setText(item.get(0).getInitial());
        }
        convertView.setClickable(false);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        final ChildHolder holder;
        Log.i("lettercity","getChildView");
        final Description item=groups.get(groupPosition).get(childPosition);
        if (view == null) {
            holder = new ChildHolder();
            view = inflater.inflate(R.layout.item_cities, parent, false);
            holder.city_name=(TextView)view.findViewById(R.id.city_name);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        holder.city_name.setText(item.getCity_name());
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.i("lettercity","getChildrenCount"+groups.get(groupPosition).size());
        return groups.get(groupPosition).size();
    }

    @Override
    public List<Description> getGroup(int groupPosition) {
        Log.i("lettercity", "getGroup");
        return groups.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        Log.i("lettercity",""+groupPosition);
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
