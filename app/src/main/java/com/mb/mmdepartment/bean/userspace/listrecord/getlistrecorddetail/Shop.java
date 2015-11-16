package com.mb.mmdepartment.bean.userspace.listrecord.getlistrecorddetail;

import com.mb.mmdepartment.adapter.catlogs.GroupItem;
import com.mb.mmdepartment.bean.userspace.listrecord.getlistrecorddetail.shop.Description;

import java.util.List;

/**
 * Created by krisi on 2015/10/20.
 */
public class Shop {
    private String pocategory_id;
    private String shop_name;
    private String shop_id;
    private String onumber;
    private String t_price;
    private List<Description> data;

    public String getPocategory_id() {
        return pocategory_id;
    }

    public void setPocategory_id(String pocategory_id) {
        this.pocategory_id = pocategory_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getOnumber() {
        return onumber;
    }

    public void setOnumber(String onumber) {
        this.onumber = onumber;
    }

    public String getT_price() {
        return t_price;
    }

    public void setT_price(String t_price) {
        this.t_price = t_price;
    }

    public List<Description> getData() {
        return data;
    }

    public void setData(List<Description> data) {
        this.data = data;
    }
}
