package com.mb.mmdepartment.adapter.helpcalculate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.market_sel.AreaSelData;

import java.util.List;

/**
 * Created by Administrator on 2015/11/22.
 */
public class AreasAdapter extends BaseAdapter {
    private List<AreaSelData> list;
    public AreasAdapter(List<AreaSelData> list) {
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.areas_sel_item, parent, false);
        }
        TextView tv = (TextView) v.findViewById(R.id.accumulate_recycle);
        tv.setText(list.get(position).getDistrict());
        return v;
    }
}
