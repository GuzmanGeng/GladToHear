package com.mb.mmdepartment.bean.userspace.listrecord.getexchangeprizerecord;

import java.io.Serializable;

/**
 * Created by jack on 2015/10/19.
 */
public class Exchange implements Serializable{
    private String order_number;
    private String address;
    private String exchange_integral;
    private String content_id;
    private String ctime;
    private String goods_name;

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExchange_integral() {
        return exchange_integral;
    }

    public void setExchange_integral(String exchange_integral) {
        this.exchange_integral = exchange_integral;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
}
