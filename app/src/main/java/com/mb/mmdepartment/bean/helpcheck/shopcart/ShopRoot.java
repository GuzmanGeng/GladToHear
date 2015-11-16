package com.mb.mmdepartment.bean.helpcheck.shopcart;

import java.util.List;

/**
 * Created by Administrator on 2015/10/8 0008.
 */
public class ShopRoot {
    private String user_id;

    private String t_price;

    private String s_price;

    private String device_no;

    private List<ShopDetail> shop ;

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }
    public String getUser_id(){
        return this.user_id;
    }
    public void setT_price(String t_price){
        this.t_price = t_price;
    }
    public String getT_price(){
        return this.t_price;
    }
    public void setS_price(String s_price){
        this.s_price = s_price;
    }
    public String getS_price(){
        return this.s_price;
    }
    public void setDevice_no(String device_no){
        this.device_no = device_no;
    }
    public String getDevice_no(){
        return this.device_no;
    }
    public void setShop(List<ShopDetail> shop){
        this.shop = shop;
    }
    public List<ShopDetail> getShop(){
        return this.shop;
    }
}
