package com.mb.mmdepartment.bean.userspace.listrecord;

/**
 * Created by Administrator on 2015/9/24 0024.
 */
public class Orders {
    private String onumber;

    private String o_date;

    private String user_id;

    private String t_price;

    private String s_price;

    private String device_no;

    public void setOnumber(String onumber){
        this.onumber = onumber;
    }
    public String getOnumber(){
        return this.onumber;
    }
    public void setO_date(String o_date){
        this.o_date = o_date;
    }
    public String getO_date(){
        return this.o_date;
    }
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
}
