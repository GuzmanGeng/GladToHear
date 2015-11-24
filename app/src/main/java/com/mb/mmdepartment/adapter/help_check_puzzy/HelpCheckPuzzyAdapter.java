package com.mb.mmdepartment.adapter.help_check_puzzy;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.helpcheck.puzzy.PuzzyModel;

import java.util.List;

/**
 * Created by joyone2one on 2015/11/23.
 */
public class HelpCheckPuzzyAdapter extends BaseAdapter {
    private List<PuzzyModel> models;
    private int which;
    public HelpCheckPuzzyAdapter(List<PuzzyModel> models,int which) {
        this.models = models;
        this.which=which;
    }
    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
        return models.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public void setWhich(int which){
        this.which=which;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.puzzy_item, viewGroup, false);
        }
        Log.e("shisha", "===" + which);
        TextView tv= (TextView) view.findViewById(R.id.puzzy_item);
        if (which == 2) {
            Log.e("adapter", "====="+models.get(i).getSearch_name());
            tv.setText(models.get(i).getSearch_name());
        }else if (which == 3) {
            tv.setText(models.get(i).getShop_name());
        } else {
            tv.setText(models.get(i).getKeyword());
        }
        Log.e("tostring", "==model=="+models.get(i).toString());
        return view;
    }
}
