package com.mb.mmdepartment.bean.helpcheck.shopcart;

import java.util.List;

/**
 * Created by Administrator on 2015/10/8 0008.
 */
public class ShopDetail {
    private String shop_name;

    private String shop_id;

    private String t_price;

    private List<ShopData> data ;

    public void setShop_name(String shop_name){
        this.shop_name = shop_name;
    }
    public String getShop_name(){
        return this.shop_name;
    }
    public void setShop_id(String shop_id){
        this.shop_id = shop_id;
    }
    public String getShop_id(){
        return this.shop_id;
    }
    public void setT_price(String t_price){
        this.t_price = t_price;
    }
    public String getT_price(){
        return this.t_price;
    }
    public void setData(List<ShopData> data){
        this.data = data;
    }
    public List<ShopData> getData(){
        return this.data;
    }
}
