package com.mb.mmdepartment.bean.helpcheck.shopcart;

/**
 * Created by Administrator on 2015/10/8 0008.
 */
public class ShopData {
    private String c_number;

    private String quantity;

    private String t_price;

    public void setC_number(String c_number){
        this.c_number = c_number;
    }
    public String getC_number(){
        return this.c_number;
    }
    public void setQuantity(String quantity){
        this.quantity = quantity;
    }
    public String getQuantity(){
        return this.quantity;
    }
    public void setT_price(String t_price){
        this.t_price = t_price;
    }
    public String getT_price(){
        return this.t_price;
    }
}
