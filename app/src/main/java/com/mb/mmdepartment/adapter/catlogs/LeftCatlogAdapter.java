package com.mb.mmdepartment.adapter.catlogs;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.commodity.Type;
import com.mb.mmdepartment.bean.informationcollaction.detail.Content;

import java.util.List;

/**
 * Created by joyone2one on 2015/11/16.
 */
public class LeftCatlogAdapter extends BaseAdapter {
    private List<Type> list;
    private Context context;
    private int defItem;
    public LeftCatlogAdapter(Context context,List<Type> list) {
        this.list = list;
        this.context=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public void setDefSelction(int position){
        this.defItem=position;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.catlog_sel_item, viewGroup, false);
        }
        TextView tv_catlog=(TextView)view.findViewById(R.id.calog_sel_item_tv);
        tv_catlog.setText(list.get(position).getTitle());
        if (defItem == position) {
            tv_catlog.setPressed(true);
        } else {
            tv_catlog.setPressed(false);
        }
        return view;
    }
}
