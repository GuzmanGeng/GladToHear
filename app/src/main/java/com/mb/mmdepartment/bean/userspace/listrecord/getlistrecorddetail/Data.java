package com.mb.mmdepartment.bean.userspace.listrecord.getlistrecorddetail;

import java.util.List;

/**
 * Created by krisi on 2015/10/20.
 */
public class Data {
    private String onumber;
    private String o_date;
    private String t_price;
    private String s_price;
    private List<Shop> shop;

    public String getOnumber() {
        return onumber;
    }

    public void setOnumber(String onumber) {
        this.onumber = onumber;
    }

    public String getO_date() {
        return o_date;
    }

    public void setO_date(String o_date) {
        this.o_date = o_date;
    }

    public String getT_price() {
        return t_price;
    }

    public void setT_price(String t_price) {
        this.t_price = t_price;
    }

    public String getS_price() {
        return s_price;
    }

    public void setS_price(String s_price) {
        this.s_price = s_price;
    }

    public List<Shop> getShop() {
        return shop;
    }

    public void setShop(List<Shop> shop) {
        this.shop = shop;
    }
}
