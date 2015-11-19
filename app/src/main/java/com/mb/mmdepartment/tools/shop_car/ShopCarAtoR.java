package com.mb.mmdepartment.tools.shop_car;

import android.app.Activity;
import android.util.Log;

import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joyone2one on 2015/11/19.
 */
public class ShopCarAtoR {
    private Activity activity;
    public ShopCarAtoR(Activity activity) {
        this.activity = activity;
    }
    /**
     * 移除购物车数据
     * @param id
     */
    public void remove_cars_index(String id) {
        if (TApplication.ids.contains(id)) {
            TApplication.ids.remove(id);
        } else {
            ((BaseActivity)activity).showToast("商品不存在");
        }
        if (TApplication.shop_lists.containsKey(id)) {
            TApplication.shop_lists.remove(id);
        } else {
            ((BaseActivity)activity).showToast("商品不存在");
        }
        for (int i=0;i<TApplication.shop_list_to_pick.size();i++) {
            DataList list = TApplication.shop_list_to_pick.get(i);
            for (int j = 0; j < list.getList().size();j++) {
                Lists lists=list.getList().get(j);
                String id_get=lists.getId().trim();
                try {
                    id_get= URLEncoder.encode(id_get, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (id.equals(id_get)) {
                    list.getList().remove(lists);
                    if (list.getList().size() == 0) {
                        TApplication.shop_list_to_pick.remove(list);
                    }
                    return;
                }
            }
        }
    }
    /**
     * 向购物车中追加数据
     * @param id
     * @param shop_name
     * @param list
     */
    public void add_cars_index(String id,String shop_name,Lists list){
        if (TApplication.ids.size()==0||!TApplication.ids.contains(id)) {
            TApplication.ids.add(id);
        } else {
            ((BaseActivity)activity).showToast("商品已存在");
        }
        if (TApplication.shop_lists.size()==0||TApplication.shop_lists.get(id) == null) {
            TApplication.shop_lists.put(id, list);
        } else {
            ((BaseActivity)activity).showToast("商品已存在");
        }
        if (TApplication.shop_list_to_pick.size() == 0) {
            DataList datalist = new DataList();
            List<Lists> new_list = new ArrayList<>();
            datalist.setName(shop_name);
            new_list.add(list);
            datalist.setList(new_list);
            TApplication.shop_list_to_pick.add(datalist);
            return;
        }
        for (int i = 0; i < TApplication.ids.size(); i++) {
            DataList data = TApplication.shop_list_to_pick.get(i);
            String get_shop_name = data.getName().trim();
            try {
                get_shop_name=URLEncoder.encode(get_shop_name, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (shop_name.equals(get_shop_name)) {
                data.getList().add(list);
                return;
            }
        }
        DataList datalist = new DataList();
        List<Lists> new_list = new ArrayList<>();
        datalist.setName(shop_name);
        new_list.add(list);
        datalist.setList(new_list);
        TApplication.shop_list_to_pick.add(datalist);
    }
}
