package com.mb.mmdepartment.adapter.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.getcities.Description;
import com.mb.mmdepartment.bean.userspace.listrecord.getlistrecorddetail.Shop;

import java.util.List;

/**
 * Created by krisi on 2015/10/21.
 */
public class HotCityAdapter extends BaseAdapter {
    class Holder{
        TextView cityname;
    }
    private LayoutInflater inflater;
    private List<Description> cities;

    public HotCityAdapter(Context context,List<Description> cities){
        inflater=LayoutInflater.from(context);
        this.cities=cities;
    }
    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder=new Holder();
            convertView = inflater.inflate(R.layout.item_cities, parent, false);
            holder.cityname=(TextView)convertView.findViewById(R.id.city_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.cityname.setText(cities.get(position).getCity_name());
        return convertView;
    }
}
